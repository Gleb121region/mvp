package ru.spbstu.mvp.request.announcement;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.interview.ApartmentType;
import ru.spbstu.mvp.entity.enums.interview.City;

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
        Double maxPricePerMonth,
        @Nullable
        @Min(0)
        Double minPricePerMonth,
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
