package com.weather.api.service;

import com.weather.api.model.entity.UltShortTerm;
import com.weather.api.repository.UltShortTermRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UltShortTermTest {
    @InjectMocks
    private UltShortTermService ultShortTermService;
    @Mock
    private UltShortTermRepository ultShortTermRepository;
    @Mock
    private AddressToPoint addressToPoint;
    @Mock
    private TransCoordinate transCoordinate;

    @Test
    void ultShortTermTest() {

    }


    @Test
    void findInDB() {
        String testDateNum = "202401260600";
        String testDateVal = "2024년01월26일 06시";

        UltShortTerm ultShortTerm = UltShortTerm.builder()
                .id(115L)
                .dateTime(testDateVal)
                .build();

        when(ultShortTermRepository.findByDateTime(testDateVal)).thenReturn(Optional.of(ultShortTerm));

        UltShortTerm result = ultShortTermService.searchInDB(testDateNum);

        assertEquals("날짜 값 일치 여부", testDateVal, result.getDateTime());
    }
}
