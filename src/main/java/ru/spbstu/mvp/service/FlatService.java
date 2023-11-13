package ru.spbstu.mvp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.entity.Photo;
import ru.spbstu.mvp.repository.FlatRepository;
import ru.spbstu.mvp.request.flat.FlatRequest;
import ru.spbstu.mvp.response.flat.FlatResponse;
import ru.spbstu.mvp.response.flat.FlatWithDescriptionResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlatService {

    private final FlatRepository repository;

    public Set<FlatResponse> getFlatsInfo(FlatRequest request, Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Flat> flats = repository.findFlatByParams(request, pageable);
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
                        .photoUrls(flat.getPhotoLinks().stream().map(Photo::getPhotoUrl).collect(Collectors.toSet()))
                        .build()
        ).stream().collect(Collectors.toSet());
    }


    public FlatWithDescriptionResponse getFlatInfo(int flatId) {
        return repository.findById(flatId).map(
                it ->
                {
                    System.out.println(it.getPhotoLinks());
                    return FlatWithDescriptionResponse.builder()
                            .id(it.getId())
                            .floor(it.getFloor())
                            .floorsCount(it.getFloorsCount())
                            .totalMeters(it.getTotalMeters())
                            .roomsCount(it.getRoomsCount())
                            .pricePerMonth(it.getPricePerMonth())
                            .address(it.getDistrict() + " " + it.getStreet() + " " + it.getHouseNumber())
                            .underground(it.getUnderground())
                            .photoUrls(it.getPhotoLinks().stream().map(Photo::getPhotoUrl).collect(Collectors.toSet()))
                            .description(it.getDescription())
                            .build();
                }

        ).orElse(null);
    }
}
