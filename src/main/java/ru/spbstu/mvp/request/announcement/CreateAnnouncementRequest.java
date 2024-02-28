package ru.spbstu.mvp.request.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateAnnouncementRequest(
        @NotBlank
        String city,
        @NotBlank
        String underground,
        @NotBlank
        String district,
        @NotBlank
        String street,
        @NotBlank
        String houseNumber,
        @NotNull
        Integer floor,
        @NotNull
        Integer floorsCount,
        @NotNull
        Double totalMeters,
        @NotNull
        Integer roomsCount,
        @NotNull
        Double pricePerMonth,
        @NotBlank
        @Size(max = 10_000)
        String description,
        @NotNull
        Boolean isRefrigerator,
        @NotNull
        Boolean isWashingMachine,
        @NotNull
        Boolean isTV,
        @NotNull
        Boolean isShowerCubicle,
        @NotNull
        Boolean isBathtub,
        @NotNull
        Boolean isFurnitureRoom,
        @NotNull
        Boolean isFurnitureKitchen,
        @NotNull
        Boolean isDishwasher,
        @NotNull
        Boolean isAirConditioning,
        @NotNull
        Boolean isInternet
) {
}
