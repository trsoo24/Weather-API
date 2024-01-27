package com.weather.api;

import com.weather.api.model.dto.Grid;
import com.weather.api.model.dto.Point;
import com.weather.api.service.AddressToPoint;
import com.weather.api.service.TransCoordinate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(MockitoExtension.class)
public class TransValueTest {
    @InjectMocks
    private AddressToPoint addressToPoint;

    @InjectMocks
    private TransCoordinate transCoordinate;

    @Test
    void testAddressToPoint() {
        String address = "서울특별시 강남구 테헤란로 131";
        double latitude = 127.032926912;
        double longitude = 37.500066200;

        Point point = addressToPoint.getMapString(address);

        assertEquals("위도 값", point.getLatitude(), latitude);
        assertEquals("경도 값", point.getLongitude(), longitude);
    }

    @Test
    void testTransXY () {
        Point point = Point.builder()
                .longitude(37.500066200)
                .latitude(127.032926912)
                .build();

        Grid grid = transCoordinate.toGrid(point);

        assertEquals("X 좌표 값", grid.getX(), 125);
        assertEquals("Y 좌표 값", grid.getY(), 61);
    }
}
