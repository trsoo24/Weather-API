package com.weather.api.service;

import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermTemp;
import com.weather.api.model.entity.MidTermTotal;
import com.weather.api.repository.MidTermTempRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MidTempTest {
    @InjectMocks
    private MidTempService midTempService;
    @Mock
    private MidTermTempRepository midTermTempRepository;
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
        MidTermTemp midTermTemp = MidTermTemp.builder()
                .id(115L)
                .date(testDateVal)
                .location(location)
                .build();

        when(midTermTempRepository.findByDateAndLocation(testDateVal, location)).thenReturn(Optional.of(midTermTemp));

        MidTermTemp result = midTempService.findInDB(midLocationCode);

        assertEquals("날짜 값 일치 여부", testDateVal, result.getDate());
    }
}
