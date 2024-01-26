package com.weather.api.controller;

import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermRain;
import com.weather.api.model.entity.MidTermTotal;
import com.weather.api.service.MidRainService;
import com.weather.api.service.MidTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mid")
public class MidTermController {
    private final MidTermService midTermService;
    private final MidRainService midRainService;

    @PostMapping("/total")
    public ResponseEntity<MidTermTotal> searchTotal(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midTermService.searchTotal(midLocationCode));
    }

    @PostMapping("/rain")
    public ResponseEntity<MidTermRain> searchRain(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midRainService.searchMidRain(midLocationCode));
    }
}
