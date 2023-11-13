package ru.spbstu.mvp.response.flat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlatWithDescriptionResponse {
    private Integer id;
    private Integer floor;
    private Integer floorsCount;
    private Double totalMeters;
    private Integer roomsCount;
    private Double pricePerMonth;
    private String address;
    private String underground;
    private Set<String> photoUrls;
    private String description;
}
