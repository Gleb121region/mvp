package ru.spbstu.mvp.response.announcement;

import lombok.Builder;
import org.springframework.lang.Nullable;
import ru.spbstu.mvp.entity.enums.ApartmentType;
import ru.spbstu.mvp.entity.enums.City;

import java.util.Set;

@Builder
public record AnnouncementWithDescriptionResponse(Integer id,
                                                  City city, 
                                                  Integer floor,
                                                  Integer floorsCount,
                                                  Integer totalMeters,
                                                  ApartmentType apartmentType,
                                                  Integer pricePerMonth,
                                                  String address,
                                                  @Nullable String underground,
                                                  Set<String> photoUrls,
                                                  String description,
                                                  Boolean isRefrigerator,
                                                  Boolean isWashingMachine,
                                                  Boolean isTV,
                                                  Boolean isShowerCubicle,
                                                  Boolean isBathtub,
                                                  Boolean isFurnitureRoom,
                                                  Boolean isFurnitureKitchen,
                                                  Boolean isDishwasher,
                                                  Boolean isAirConditioning,
                                                  Boolean isInternet,
                                                  Boolean isHide,
                                                  Boolean isLikedByUser
) {
}
