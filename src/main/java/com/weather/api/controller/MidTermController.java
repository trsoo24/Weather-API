package com.weather.api.controller;

import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermRain;
import com.weather.api.model.entity.MidTermTemp;
import com.weather.api.model.entity.MidTermTotal;
import com.weather.api.service.MidRainService;
import com.weather.api.service.MidTempService;
import com.weather.api.service.MidTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mid")
public class MidTermController {
    private final MidTermService midTermService;
    private final MidRainService midRainService;
    private final MidTempService midTempService;

    @PostMapping("/total")
    public ResponseEntity<MidTermTotal> searchTotal(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midTermService.searchTotal(midLocationCode));
    }
    @PostMapping("/rain")
    public ResponseEntity<MidTermRain> searchRain(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midRainService.searchMidRain(midLocationCode));
    }
    @PostMapping("/temp")
    public ResponseEntity<MidTermTemp> searchTemp(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midTempService.searchTemp(midLocationCode));
    }
    @GetMapping("/total")
    public ResponseEntity<MidTermTotal> searchTotalInDB(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midTermService.findInDB(midLocationCode));
    }
    @GetMapping("/rain")
    public ResponseEntity<MidTermRain> searchRainInDB(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midRainService.findInDB(midLocationCode));
    }
    @GetMapping("/temp")
    public ResponseEntity<MidTermTemp> searchTempInDB(@RequestBody @Valid MidLocationCode midLocationCode) {
        return ResponseEntity.ok(midTempService.findInDB(midLocationCode));
    }
}
