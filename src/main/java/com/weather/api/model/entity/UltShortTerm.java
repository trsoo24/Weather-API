package com.weather.api.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UltShortTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String dateTime; // yyyy년 MM월 dd일 HH시
    private String address;
    private String t1h; // 기온
    private String rn1; // 1시간 강수량
    private String sky; // 하늘 상태
    private String uuu; // 동서바람 성분
    private String vvv; // 남북바람 성분
    private String reh; // 습도
    private String pty; // 강수 형태
    private String lgt; // 낙뢰
    private String vec; // 풍향
    private String wsd; // 풍속
}
