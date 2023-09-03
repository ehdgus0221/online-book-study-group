package com.project.bookstudy.security.filter;

import com.project.bookstudy.util.PasswordUtil;
import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.project.bookstudy.common.dto.ErrorCode.GET_WRITER_ERROR;
import static com.project.bookstudy.common.dto.ErrorCode.NOT_FOUND_TOKEN;

/**
 * [Jwt 인증 필터]
 *
 * 기본적으로 사용자는 요청 헤더에 AccessToken만 담아서 요청
 * AccessToken 만료 시에만 RefreshToken을 요청 헤더에 담아서 요청
 *
 * 1. RefreshToken이 헤더에 없고, AccessToken이 유효한 경우 -> 인증 성공 처리, RefreshToken을 재발급하지는 않는다.
 * 2. RefreshToken이 헤더에 없고, AccessToken이 없거나 유효하지 않은 경우 -> 인증 실패 처리 (토큰 관련 예외는 ExceptionHandlerFilter에서 처리)
 * 3. RefreshToken이 헤더에 있는 경우 -> DB의 RefreshToken과 비교하여 일치하면 AccessToken, RefreshToken 재발급(RTR 방식)
 * 인증 성공 처리는 하지 않고 실패 처리
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String refreshToken = jwtTokenProvider.extractRefreshToken(request)
                .filter(jwtTokenProvider::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessAndRefreshToken(response, refreshToken);
            return; // RefreshToken을 보낸 경우에는 AccessToken & RefreshToken을 재발급 해주는 메서드 호출, 인증 처리는 하지 않게 하기위해 바로 return으로 필터 진행 막기
        }

        checkAccessTokenAndAuthentication(request);
        filterChain.doFilter(request, response);
    }

    /**
     * [RefreshToken 토큰으로 유저 정보 찾기 & 액세스 토큰/리프레시 토큰 재발급]
     */
    public void checkRefreshTokenAndReIssueAccessAndRefreshToken(HttpServletResponse response, String refreshToken) {
            Member member = memberRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

                        try {
                            response.getWriter().write(String.valueOf(NOT_FOUND_TOKEN)); // 에러 메시지를 응답 본문에 포함
                            response.getWriter().flush();
                            response.getWriter().close();
                        } catch (IOException e) {
                            throw new RuntimeException(String.valueOf(GET_WRITER_ERROR));
                        }
                        return new RuntimeException(String.valueOf(NOT_FOUND_TOKEN));
                    });

        String reIssuedRefreshToken = reIssueRefreshToken(member);
        jwtTokenProvider.sendAccessAndRefreshToken(response, jwtTokenProvider.createAccessToken(member.getId()),
                reIssuedRefreshToken);

}

    /**
     * [RefreshToken 토큰 재발급 & DB에 업데이트]
     */
    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtTokenProvider.createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }

    /**
     * [액세스 토큰 체크 & 인증 처리]
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtTokenProvider.extractAccessToken(request)
                .filter(jwtTokenProvider::isTokenValid)
                .ifPresent(accessToken -> jwtTokenProvider.extractId(accessToken)
                        .ifPresent(id -> memberRepository.findById(id)
                                .ifPresent(this::saveAuthentication)));
    }

    /**
     * [인증 허가]
     */
    public void saveAuthentication(Member member) {
        String password = member.getPassword();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 인증 되도록 설정
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(member.getEmail())
                .password(password)
                .roles(member.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}