package ru.spbstu.mvp.request.flat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Set;

@Getter
@Setter
@Builder
public class FlatRequest {
    private String city;
    @Nullable
    private String underground;
    @Nullable
    private Set<Integer> roomsCounts;
    @Nullable
    private Double maxPricePerMonth;
    @Nullable
    private Double minPricePerMonth;
}
