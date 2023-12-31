package com.project.bookstudy.security.filter.handler;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.exception.MemberNotFoundException;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2User memberInfo = (OAuth2User) authentication.getPrincipal();
        String email = (String) ((Map) memberInfo.getAttribute("kakao_account")).get("email");
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException());
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        loginSuccess(response, accessToken, refreshToken, member);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{ ☆ 로그인 성공 ☆ }");

        log.info("로그인 성공");
    }

    private void loginSuccess(HttpServletResponse response, String accessToken, String refreshToken, Member member){
        response.addHeader(jwtTokenProvider.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtTokenProvider.getRefreshHeader(), "Bearer " + refreshToken);

        jwtTokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtTokenProvider.updateRefreshToken(member.getEmail(), refreshToken);
    }


}
