package com.weather.api.service;

import com.weather.api.model.dto.DateInfo;
import com.weather.api.model.dto.Grid;
import com.weather.api.model.dto.Point;
import com.weather.api.model.entity.ShortTerm;
import com.weather.api.repository.ShortTermRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ShortTermTest {
    @Mock
    private ShortTermRepository shortTermRepository;

    @Mock
    private AddressToPoint addressToPoint;

    @Mock
    private TransCoordinate transCoordinate;

    @InjectMocks
    private ShortTermService shortTermService;


    @Test
    void saveShortTerm() {
        DateInfo dateInfo = DateInfo.builder()
                .date("20240125")
                .address("서울특별시 강남구 테헤란로 131")
                .build();
        Point testPoint = Point.builder()
                .latitude(127.032926912)
                .longitude(37.500066200)
                .build();
        Grid grid = Grid.builder()
                .x(125)
                .y(61)
                .build();

        when(addressToPoint.getMapString(anyString())).thenReturn(testPoint);
        when(transCoordinate.toGrid(any(Point.class))).thenReturn(grid);
    }

    @Test
    void findInDB() {
        String testDateNum = "202401260600";
        String testDateVal = "2024년01월26일 06시";

        ShortTerm shortTerm = ShortTerm.builder()
                .id(115L)
                .dateTime(testDateVal)
                .build();

        when(shortTermRepository.findByDateTime(testDateVal)).thenReturn(Optional.of(shortTerm));

        ShortTerm result = shortTermService.searchInDB(testDateNum);

        assertEquals("날짜 값 일치 여부", testDateVal, result.getDateTime());
    }
}
