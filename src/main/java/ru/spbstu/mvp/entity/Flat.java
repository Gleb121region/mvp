package ru.spbstu.mvp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flat_id", nullable = false, updatable = false)
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

    @Column
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flat", fetch = FetchType.EAGER)
    private Set<Photo> photos;
}