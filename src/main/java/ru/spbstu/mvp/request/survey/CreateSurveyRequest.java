package ru.spbstu.mvp.request.survey;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.spbstu.mvp.entity.enums.ApartmentType;
import ru.spbstu.mvp.entity.enums.City;
import ru.spbstu.mvp.entity.enums.Term;

import java.util.Set;

@Builder
public record CreateSurveyRequest(
        @NotNull
        Term term,
        @NotNull
        Set<ApartmentType> apartmentTypes,
        @NotNull
        City city,
        @NotNull
        @Min(0)
        Integer minArea,
        @NotNull
        @Min(1_000_000)
        Integer maxArea,
        @NotNull
        @Min(0)
        Integer minBudget,
        @NotNull
        @Min(100_000_000)
        Integer maxBudget,
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
