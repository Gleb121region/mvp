package ru.spbstu.mvp.response.flat;

import lombok.Builder;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.interview.ApartmentType;

import java.util.Set;

@Builder
public record AnnouncementWithDescriptionResponse(Integer id,
                                                  Integer floor,
                                                  Integer floorsCount,
                                                  Double totalMeters,
                                                  ApartmentType apartmentType,
                                                  Double pricePerMonth,
                                                  String address,
                                                  @Nullable
                                                  String underground,
                                                  Set<String> photoUrls,
                                                  String description) {
}
