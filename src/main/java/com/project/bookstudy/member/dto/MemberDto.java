package com.project.bookstudy.member.dto;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.domain.MemberStatus;
import com.project.bookstudy.member.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {

    private Long id;
    private String email;
    private String name;
    private String phone;

    private String career;
    private Long point = 0L; //나중에 Point class 따로 생성?
    private Role role;
    private MemberStatus status;
    private String password;

    @Builder
    private MemberDto(Long id, String email, String name, String phone, String career, Long point, Role role, MemberStatus status, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.career = career;
        this.point = point;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .career(member.getCareer())
                .point(member.getPoint())
                .status(member.getStatus())
                .role(member.getRole())
                .build();
    }


}
