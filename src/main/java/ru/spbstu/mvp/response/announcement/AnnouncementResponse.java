package ru.spbstu.mvp.response.announcement;

import lombok.Builder;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.ApartmentType;

import java.util.Set;

@Builder
public record AnnouncementResponse(
        Integer id,
        Integer floor,
        Integer floorsCount,
        Double totalMeters,
        ApartmentType apartmentType,
        Double pricePerMonth,
        String address,
        @Nullable
        String underground,
        Set<String> photoUrls) {
}
