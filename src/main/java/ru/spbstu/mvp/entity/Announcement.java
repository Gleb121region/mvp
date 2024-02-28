package ru.spbstu.mvp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id", nullable = false, updatable = false)
    private Integer id;

    @NotBlank
    private String city;

    @NotBlank
    private String underground;

    @NotBlank
    private String district;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @NotNull
    private Integer floor;

    @NotNull
    private Integer floorsCount;

    @NotNull
    private Double totalMeters;

    @NotNull
    private Integer roomsCount;

    @NotNull
    private Double pricePerMonth;

    @NotBlank
    @Size(max = 10_000)
    private String description;

    @NotNull
    private Boolean isRefrigerator;

    @NotNull
    private Boolean isWashingMachine;

    @Column(name = "is_tv", nullable = false, updatable = false)
    private Boolean isTV;

    @NotNull
    private Boolean isShowerCubicle;

    @NotNull
    private Boolean isBathtub;

    @NotNull
    private Boolean isFurnitureRoom;

    @NotNull
    private Boolean isFurnitureKitchen;

    @NotNull
    private Boolean isDishwasher;

    @NotNull
    private Boolean isAirConditioning;

    @NotNull
    private Boolean isInternet;

    @NotNull
    @Builder.Default
    private Boolean isHide = false;

    @Column
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column
    @Builder.Default
    private OffsetDateTime updatedAt = null;

    @OneToMany(mappedBy = "announcement")
    private Set<Photo> photos;

    @OneToMany(mappedBy = "announcement")
    private Set<Feedback> feedbacks;
}