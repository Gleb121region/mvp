package ru.spbstu.mvp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.request.flat.FlatRequest;

//todo: нужно решить проблему с N+1, можно использовать https://www.baeldung.com/jpa-entity-graph
// N+1 решает проблему, но есть самая главная проблема с получением данных из таблицы Photo поля photo_url

// todo: нужно переписать под Criteria API

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer> {
    @Query(value = """
                SELECT DISTINCT f FROM Flat f
                WHERE (:#{#request.city} is null or f.city = :#{#request.city})
                AND (:#{#request.underground} is null or f.underground = :#{#request.underground})
                AND (:#{#request.district} is null or f.district = :#{#request.district})
                
                AND (:#{#request.roomsCounts} is null or f.roomsCount in :#{#request.roomsCounts})
                
                AND (:#{#request.maxPricePerMonth} is null or f.pricePerMonth <= :#{#request.maxPricePerMonth})
                AND (:#{#request.minPricePerMonth} is null or f.pricePerMonth >= :#{#request.minPricePerMonth})
                   
                AND (:#{#request.isRefrigerator} is null or f.isRefrigerator = :#{#request.isRefrigerator})
                AND (:#{#request.isWashingMachine} is null or f.isWashingMachine = :#{#request.isWashingMachine})
                AND (:#{#request.isTV} is null or f.isTV = :#{#request.isTV})
                AND (:#{#request.isShowerCubicle} is null or f.isShowerCubicle = :#{#request.isShowerCubicle})
                AND (:#{#request.isBathtub} is null or f.isBathtub = :#{#request.isBathtub})
                AND (:#{#request.isFurnitureRoom} is null or f.isFurnitureRoom = :#{#request.isFurnitureRoom})
                AND (:#{#request.isFurnitureKitchen} is null or f.isFurnitureKitchen = :#{#request.isFurnitureKitchen})
                AND (:#{#request.isDishwasher} is null or f.isDishwasher = :#{#request.isDishwasher})
                AND (:#{#request.isAirConditioning} is null or f.isAirConditioning = :#{#request.isAirConditioning})
                AND (:#{#request.isInternet} is null or f.isInternet = :#{#request.isInternet})
                ORDER BY f.id
            """)
    Page<Flat> findFlatByParams(@Param("request") FlatRequest request, Pageable pageable);
}

