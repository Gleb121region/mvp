package ru.spbstu.mvp.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.spbstu.mvp.entity.enums.ApartmentType;
import ru.spbstu.mvp.entity.enums.City;
import ru.spbstu.mvp.entity.enums.Term;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "survey_answers")
public class SurveyAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_answer_id", nullable = false, updatable = false)
    public Integer id;

    @Enumerated(EnumType.STRING)
    private Term term;

    @Enumerated(EnumType.STRING)
    private Set<ApartmentType> apartmentType;

    @Enumerated(EnumType.STRING)
    private City city;

    private Integer minArea;
    private Integer maxArea;

    private Integer minBudget;
    private Integer maxBudget;

    private Boolean isRefrigerator;
    private Boolean isWashingMachine;
    private Boolean isTV;
    private Boolean isShowerCubicle;
    private Boolean isBathtub;
    private Boolean isFurnitureRoom;
    private Boolean isFurnitureKitchen;
    private Boolean isDishwasher;
    private Boolean isAirConditioning;
    private Boolean isInternet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
