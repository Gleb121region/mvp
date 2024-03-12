package ru.spbstu.mvp.request.survey;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.spbstu.mvp.entity.enums.interview.ApartmentType;
import ru.spbstu.mvp.entity.enums.interview.City;
import ru.spbstu.mvp.entity.enums.interview.Term;

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
        Integer maxBudget
) {
}
