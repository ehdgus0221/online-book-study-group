package com.project.bookstudy.studygroup.domain;

import com.project.bookstudy.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private Long id;

    private LocalDateTime paymentDt;
    private PaymentStatus status;
    private int price;
    private int discountPrice;
    private int paymentPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member memberId;


}
