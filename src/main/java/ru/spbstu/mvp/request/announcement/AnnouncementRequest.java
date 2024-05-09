package ru.spbstu.mvp.request.announcement;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.ApartmentType;
import ru.spbstu.mvp.entity.enums.City;

import java.util.Set;

@Builder
public record AnnouncementRequest(
        City city,
        @Nullable
        String underground,
        @Nullable
        String district,
        @Nullable
        Set<ApartmentType> apartmentTypes,
        @Nullable
        @Max(100_000_000)
        Integer maxPricePerMonth,
        @Nullable
        @Min(0)
        Integer minPricePerMonth,
        @Nullable
        @Max(100_000_000)
        Integer maxArea,
        @Nullable
        @Min(0)
        Integer minArea,
        @Nullable
        Boolean isRefrigerator,
        @Nullable
        Boolean isWashingMachine,
        @Nullable
        Boolean isTV,
        @Nullable
        Boolean isShowerCubicle,
        @Nullable
        Boolean isBathtub,
        @Nullable
        Boolean isFurnitureRoom,
        @Nullable
        Boolean isFurnitureKitchen,
        @Nullable
        Boolean isDishwasher,
        @Nullable
        Boolean isAirConditioning,
        @Nullable
        Boolean isInternet
) {
}
