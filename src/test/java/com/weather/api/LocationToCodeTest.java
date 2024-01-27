package com.weather.api;

import com.weather.api.service.LocationToCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LocationToCodeTest {
    @InjectMocks
    private LocationToCode locationToCode;

    @Test
    public void testSearchTotalLocation() {
        String code1 = locationToCode.searchTotalLocation("서울");
        String code2 = locationToCode.searchTotalLocation("강원도");
        String code3 = locationToCode.searchTotalLocation("전국");

        assertEquals("109", code1);
        assertEquals("105", code2);
        assertEquals("108", code3);
    }

    @Test
    public void testSearchRainLocation() {
        String code1 = locationToCode.searchRainLocation("서울");
        String code2 = locationToCode.searchRainLocation("강원도영서");
        String code3 = locationToCode.searchRainLocation("전라북도");

        assertEquals("11B00000", code1);
        assertEquals("11D10000", code2);
        assertEquals("11F10000", code3);
    }

    @Test
    public void testSearchTempLocation() {
        String code1 = locationToCode.searchTempLocation("서울");
        String code2 = locationToCode.searchTempLocation("제주");
        String code3 = locationToCode.searchTempLocation("부산");

        assertEquals("11B10101", code1);
        assertEquals("11G00201", code2);
        assertEquals("11H20201", code3);
    }
}
