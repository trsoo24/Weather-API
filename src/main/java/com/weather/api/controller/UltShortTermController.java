package com.weather.api.controller;

import com.weather.api.model.dto.DateInfo;
import com.weather.api.model.entity.UltShortTerm;
import com.weather.api.service.UltShortTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ult")
@RequiredArgsConstructor
public class UltShortTermController {
    private final UltShortTermService ultShortTermService;

    @PostMapping("/fcst")
    public ResponseEntity<List<UltShortTerm>> useUltShortTermApi (@RequestBody @Valid DateInfo dateInfo) {
        return ResponseEntity.ok(ultShortTermService.searchUltShortTerm(dateInfo));
    }

    @GetMapping("/fcst")
    public ResponseEntity<UltShortTerm> findInDB(@RequestParam @Valid String dateTime) {
        return ResponseEntity.ok(ultShortTermService.searchInDB(dateTime));
    }
}
