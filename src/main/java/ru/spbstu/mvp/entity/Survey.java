package ru.spbstu.mvp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.interview.ApartmentType;
import ru.spbstu.mvp.entity.enums.interview.City;
import ru.spbstu.mvp.entity.enums.interview.Term;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id", nullable = false, updatable = false)
    public Integer id;

    @Nullable
    @Enumerated(EnumType.STRING)
    private Term term;

    @Nullable
    @Enumerated(EnumType.STRING)
    private ApartmentType apartmentType;

    @Nullable
    @Enumerated(EnumType.STRING)
    private City city;

    private Integer minArea;
    private Integer maxArea;
    private Integer minBudget;
    private Integer maxBudget;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
