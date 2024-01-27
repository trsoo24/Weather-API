package com.weather.api.controller;

import com.weather.api.model.dto.DateInfo;
import com.weather.api.model.entity.ShortTerm;
import com.weather.api.service.ShortTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/short")
public class ShortTermController {
    private final ShortTermService shortTermService;

    @PostMapping("/fcst")
    public ResponseEntity<List<ShortTerm>> useShortTermApi(@RequestBody @Valid DateInfo dateInfo) {
        return ResponseEntity.ok(shortTermService.searchShortTerm(dateInfo));
    }
}
