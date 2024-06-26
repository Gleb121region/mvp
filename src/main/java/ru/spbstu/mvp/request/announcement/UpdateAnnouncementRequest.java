package ru.spbstu.mvp.request.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import ru.spbstu.mvp.entity.enums.ApartmentType;
import ru.spbstu.mvp.entity.enums.City;

@Builder
public record UpdateAnnouncementRequest(
        @NotBlank
        Integer announcementId,
        @NotBlank
        City city,
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
        Integer totalMeters,
        @NotNull
        ApartmentType apartmentType,
        @NotNull
        Integer pricePerMonth,
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
