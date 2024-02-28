package ru.spbstu.mvp.response.flat;

import lombok.Builder;

import java.util.Set;

@Builder
public record AnnouncementResponse(
        Integer id,
        Integer floor,
        Integer floorsCount,
        Double totalMeters,
        Integer roomsCount,
        Double pricePerMonth,
        String address,
        String underground,
        Set<String> photoUrls) {
}
