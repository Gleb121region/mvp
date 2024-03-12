package ru.spbstu.mvp.response.survey;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import ru.spbstu.mvp.entity.enums.interview.ApartmentType;
import ru.spbstu.mvp.entity.enums.interview.City;
import ru.spbstu.mvp.entity.enums.interview.Term;

import java.util.Set;

@Builder
public record GetSurveyResponse(
        @NotBlank
        Term term,
        @NotBlank
        Set<ApartmentType> apartmentTypes,
        @NotBlank
        City city,
        @NotBlank
        @Min(0)
        Integer minArea,
        @NotBlank
        @Min(1_000_000)
        Integer maxArea,
        @NotBlank
        @Min(0)
        Integer minBudget,
        @NotBlank
        @Min(100_000_000)
        Integer maxBudget
) {
}
