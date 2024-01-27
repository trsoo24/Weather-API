package com.weather.api.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String dateTime; // yyyy년 MM월 dd일 HH시 형식
    private String address; // 지역 (도로명 주소)
    private String pop; // 강수 확률
    private String pty; // 강수 형태
    private String pcp; // 1시간 강수량
    private String reh; // 습도
    private String sno; // 1시간 신적설
    private String sky; // 하늘 상태
    private String tmp; // 1시간 기온
    private String tmn; // 일 최저 기온
    private String tmx; // 일 최고 기온
    private String uuu; // 풍속 (동서성분)
    private String vvv; // 풍속 (남북성분)
    private String wav; // 파고
    private String vec; // 풍향
    private String wsd; // 풍속
}
