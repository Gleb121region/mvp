package ru.spbstu.mvp.request.flat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.Set;

@Builder
public record FlatRequest(
        String city,
        @Nullable
        String underground,
        @Nullable
        String district,
        @Nullable
        Set<Integer> roomsCounts,
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
