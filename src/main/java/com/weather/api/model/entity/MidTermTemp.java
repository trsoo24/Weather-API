package com.weather.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidTermTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String location;
    private String taMin3; // 3일 후 예상 최저 기온
    private String taMax3; // 3일 후 예상 최고 기온
    private String taMin4; // 4일 후 예상 최저 기온
    private String taMax4; // 4일 후 예상 최고 기온
    private String taMin5; // 5일 후 예상 최저 기온
    private String taMax5; // 5일 후 예상 최고 기온
    private String taMin6; // 6일 후 예상 최저 기온
    private String taMax6; // 6일 후 예상 최고 기온
    private String taMin7; // 7일 후 예상 최저 기온
    private String taMax7; // 7일 후 예상 최고 기온
    private String taMin8; // 8일 후 예상 최저 기온
    private String taMax8; // 8일 후 예상 최고 기온
    private String taMin9; // 9일 후 예상 최저 기온
    private String taMax9; // 9일 후 예상 최고 기온
    private String taMin10; // 10일 후 예상 최저 기온
    private String taMax10; // 10일 후 예상 최고 기온
}
