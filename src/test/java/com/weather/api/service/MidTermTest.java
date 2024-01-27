package com.weather.api.service;

import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermTotal;
import com.weather.api.repository.MidTermTotalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MidTermTest {
    @Mock
    private MidTermTotalRepository midTermTotalRepository;
    @Mock
    private LocationToCode locationToCode;
    @InjectMocks
    private MidTermService midTermService;

    @Test
    void testMidTermApi() {
        String dateTime = "202401270600";
        String location = "서울";
        MidLocationCode m = MidLocationCode.builder()
                .dateTime(dateTime)
                .location(location)
                .build();

        when(locationToCode.searchTotalLocation(anyString())).thenReturn("109");
        String date = "2024년01월27일";
        MidTermTotal midTermTest = MidTermTotal.builder()
                .id(1L)
                .date(date)
                .location(location)
                .description("테스트 설명")
                .build();
        when(midTermService.searchTotal(m)).thenReturn(midTermTest);

        MidTermTotal midTermTotal = midTermService.searchTotal(m);

        assertEquals("저장 값 일치", midTermTest.getDate(), midTermTotal.getDate());
        assertEquals("저장 값 일치", midTermTest.getLocation(), midTermTotal.getDate());
    }

    @Test
    void findInDB() {
        String testDateNum = "202401260600";
        String testDateVal = "2024년01월26일";
        String location = "서울 강동구";

        MidLocationCode midLocationCode = MidLocationCode.builder()
                .dateTime(testDateNum)
                .location(location)
                .build();
        MidTermTotal midTermTotal = MidTermTotal.builder()
                .id(115L)
                .date(testDateVal)
                .location(location)
                .build();

        when(midTermTotalRepository.findByDateAndLocation(testDateVal, location)).thenReturn(Optional.of(midTermTotal));

        MidTermTotal result = midTermService.findInDB(midLocationCode);

        assertEquals("날짜 값 일치 여부", testDateVal, result.getDate());
    }
}
