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
    private String rnSt3Am; // 3일 후 오전 강수 확률
    private String rnSt3Pm; // 3일 후 오후 강수 확률
    private String rnSt4Am; // 4일 후 오전 강수 확률
    private String rnSt4Pm; // 4일 후 오후 강수 확률
    private String rnSt5Am; // 5일 후 오전 강수 확률
    private String rnSt5Pm; // 5일 후 오후 강수 확률
    private String rnSt6Am; // 6일 후 오전 강수 확률
    private String rnSt6Pm; // 6일 후 오후 강수 확률
    private String rnSt7Am; // 7일 후 오전 강수 확률
    private String rnSt7Pm; // 7일 후 오후 강수 확률
    private String rn8St; // 8일 후 강수 확률
    private String rn9St; // 9일 후 강수 확률
    private String rn10St; // 10일 후 강수 확률
    private String wf3Am; // 3일 후 오전 날씨 예보
    private String wf3Pm; // 3일 후 오후 날씨 예보
    private String wf4Am; // 4일 후 오전 날씨 예보
    private String wf4Pm; // 4일 후 오후 날씨 예보
    private String wf5Am; // 5일 후 오전 날씨 예보
    private String wf5Pm; // 5일 후 오후 날씨 예보
    private String wf6Am; // 6일 후 오전 날씨 예보
    private String wf6Pm; // 6일 후 오후 날씨 예보
    private String wf7Am; // 7일 후 오전 날씨 예보
    private String wf7Pm; // 7일 후 오후 날씨 예보
    private String wf8; // 8일 후 날씨 예보
    private String wf9; // 9일 후 날씨 예보
    private String wf10; // 10일 후 날씨 예보
}
