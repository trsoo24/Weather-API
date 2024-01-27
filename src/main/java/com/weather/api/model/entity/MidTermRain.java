package com.weather.api.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidTermRain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String location;
    private String rnSt3Am; // 오전 강수 확률
    private String rnSt3Pm; // 오후 강수 확률
    private String rnSt4Am; // 오전 강수 확률
    private String rnSt4Pm; // 오후 강수 확률
    private String rnSt5Am; // 오전 강수 확률
    private String rnSt5Pm; // 오후 강수 확률
    private String rnSt6Am; // 오전 강수 확률
    private String rnSt6Pm; // 오후 강수 확률
    private String rnSt7Am; // 오전 강수 확률
    private String rnSt7Pm; // 오후 강수 확률
    private String rn8St; // 강수 확률
    private String rn9St; // 강수 확률
    private String rn10St; // 강수 확률
    private String wf3Am; // 오전 날씨 예보
    private String wf3Pm; // 오후 날씨 예보
    private String wf4Am; // 오전 날씨 예보
    private String wf4Pm; // 오후 날씨 예보
    private String wf5Am; // 오전 날씨 예보
    private String wf5Pm; // 오후 날씨 예보
    private String wf6Am; // 오전 날씨 예보
    private String wf6Pm; // 오후 날씨 예보
    private String wf7Am; // 오전 날씨 예보
    private String wf7Pm; // 오후 날씨 예보
    private String wf8; // 날씨 예보
    private String wf9; // 날씨 예보
    private String wf10; // 날씨 예보
}
