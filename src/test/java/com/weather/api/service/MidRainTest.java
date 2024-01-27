package com.weather.api.service;

import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermRain;
import com.weather.api.model.entity.MidTermTotal;
import com.weather.api.repository.MidTermRainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MidRainTest {
    @InjectMocks
    private MidRainService midRainService;
    @Mock
    private MidTermRainRepository midTermRainRepository;
    @Mock
    private LocationToCode locationToCode;

    @Test
    void findInDB() {
        String testDateNum = "202401260600";
        String testDateVal = "2024년01월26일";
        String location = "서울 강동구";

        MidLocationCode midLocationCode = MidLocationCode.builder()
                .dateTime(testDateNum)
                .location(location)
                .build();
        MidTermRain midTermRain = MidTermRain.builder()
                .id(115L)
                .date(testDateVal)
                .location(location)
                .build();

        when(midTermRainRepository.findByDateAndLocation(testDateVal, location)).thenReturn(Optional.of(midTermRain));

        MidTermRain result = midRainService.findInDB(midLocationCode);

        assertEquals("날짜 값 일치 여부", testDateVal, result.getDate());
    }
}
