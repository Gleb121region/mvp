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
        Integer totalMeters,
        ApartmentType apartmentType,
        Integer pricePerMonth,
        String address,
        @Nullable
        String underground,
        Set<String> photoUrls,
        Boolean isLikedByUser
) {
}
