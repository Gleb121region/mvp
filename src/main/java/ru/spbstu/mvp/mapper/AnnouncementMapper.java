package ru.spbstu.mvp.mapper;

import ru.spbstu.mvp.entity.Announcement;
import ru.spbstu.mvp.request.announcement.CreateAnnouncementRequest;

public class AnnouncementMapper {
    public static Announcement mapCreateAnnouncementRequestToAnnouncement(CreateAnnouncementRequest request) {
        return Announcement.builder()
                .city(request.city())
                .underground(request.underground())
                .district(request.district())
                .street(request.street())
                .houseNumber(request.houseNumber())
                .floor(request.floor())
                .floorsCount(request.floorsCount())
                .totalMeters(request.totalMeters())
                .apartmentType(request.apartmentType())
                .pricePerMonth(request.pricePerMonth())
                .description(request.description())
                .isRefrigerator(request.isRefrigerator())
                .isWashingMachine(request.isWashingMachine())
                .isTV(request.isTV())
                .isShowerCubicle(request.isShowerCubicle())
                .isBathtub(request.isBathtub())
                .isFurnitureRoom(request.isFurnitureRoom())
                .isFurnitureKitchen(request.isFurnitureKitchen())
                .isDishwasher(request.isDishwasher())
                .isAirConditioning(request.isAirConditioning())
                .isInternet(request.isInternet())
                .build();
    }
}
