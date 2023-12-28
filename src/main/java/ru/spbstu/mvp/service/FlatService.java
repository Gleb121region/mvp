package ru.spbstu.mvp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.Photo;
import ru.spbstu.mvp.repository.FlatRepository;
import ru.spbstu.mvp.repository.PhotoRepository;
import ru.spbstu.mvp.request.flat.CreateFlatRequest;
import ru.spbstu.mvp.request.flat.FlatRequest;
import ru.spbstu.mvp.response.flat.FlatResponse;
import ru.spbstu.mvp.response.flat.FlatWithDescriptionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlatService {

    private final FlatRepository flatRepository;
    private final PhotoRepository photoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Page<Flat> findFlatByParams(FlatRequest request, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flat> criteriaQuery = criteriaBuilder.createQuery(Flat.class);
        Root<Flat> flat = criteriaQuery.from(Flat.class);
        List<Predicate> predicates = new ArrayList<>();
        if (request.city() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("city"), request.city()));
        }
        if (request.underground() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("underground"), request.underground()));
        }
        if (request.district() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("district"), request.district()));
        }
        if (request.roomsCounts() != null) {
            predicates.add(flat.get("roomsCount").in(request.roomsCounts()));
        }
        if (request.maxPricePerMonth() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(flat.get("pricePerMonth"), request.maxPricePerMonth()));
        }
        if (request.minPricePerMonth() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(flat.get("pricePerMonth"), request.minPricePerMonth()));
        }
        if (request.isRefrigerator() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isRefrigerator"), request.isRefrigerator()));
        }
        if (request.isWashingMachine() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isWashingMachine"), request.isWashingMachine()));
        }
        if (request.isTV() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isTV"), request.isTV()));
        }
        if (request.isShowerCubicle() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isShowerCubicle"), request.isShowerCubicle()));
        }
        if (request.isBathtub() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isBathtub"), request.isBathtub()));
        }
        if (request.isFurnitureRoom() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isFurnitureRoom"), request.isFurnitureRoom()));
        }
        if (request.isFurnitureKitchen() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isFurnitureKitchen"), request.isFurnitureKitchen()));
        }
        if (request.isDishwasher() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isDishwasher"), request.isDishwasher()));
        }
        if (request.isAirConditioning() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isAirConditioning"), request.isAirConditioning()));
        }
        if (request.isInternet() != null) {
            predicates.add(criteriaBuilder.equal(flat.get("isInternet"), request.isInternet()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Flat> query = entityManager.createQuery(criteriaQuery);
        return new PageImpl<>(query.getResultList(), pageable, query.getResultList().size());
    }


    public Set<FlatResponse> getFlatsInfo(FlatRequest request, Integer limit, Integer offset) {
        Page<Flat> flats = findFlatByParams(request, PageRequest.of(offset, limit));
        return flats.map(
                flat -> FlatResponse.builder()
                        .id(flat.getId())
                        .floor(flat.getFloor())
                        .floorsCount(flat.getFloorsCount())
                        .totalMeters(flat.getTotalMeters())
                        .roomsCount(flat.getRoomsCount())
                        .pricePerMonth(flat.getPricePerMonth())
                        .address(flat.getDistrict() + " " + flat.getStreet() + " " + flat.getHouseNumber())
                        .underground(flat.getUnderground())
                        .photoUrls(photoRepository.findPhotosByFlat(flat).stream().map(Photo::getPhotoUrl).collect(Collectors.toSet()))
                        .build()
        ).stream().collect(Collectors.toSet());
    }


    public FlatWithDescriptionResponse getFlatInfo(int flatId) {
        return flatRepository.findById(flatId).map(
                flat ->
                        FlatWithDescriptionResponse.builder()
                                .id(flat.getId())
                                .floor(flat.getFloor())
                                .floorsCount(flat.getFloorsCount())
                                .totalMeters(flat.getTotalMeters())
                                .roomsCount(flat.getRoomsCount())
                                .pricePerMonth(flat.getPricePerMonth())
                                .address(flat.getDistrict() + " " + flat.getStreet() + " " + flat.getHouseNumber())
                                .underground(flat.getUnderground())
                                .photoUrls(photoRepository.findPhotosByFlat(flat).stream().map(Photo::getPhotoUrl).collect(Collectors.toSet()))
                                .description(flat.getDescription())
                                .build()
        ).orElse(null);
    }

    // todo: добавить endpoint который бы создавал объявление
    public void createFlat(CreateFlatRequest request) {
        return;
    }

    // todo: добавить шадулер который через месяц удалял бы объявление

}
