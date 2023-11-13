package ru.spbstu.mvp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.mvp.entity.Flat;
import ru.spbstu.mvp.request.flat.FlatRequest;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer> {
    @Query(value = """
                SELECT DISTINCT f FROM Flat f
                WHERE (:#{#request.city} is null or f.city = :#{#request.city})
                AND (:#{#request.underground} is null or f.underground = :#{#request.underground})
                AND (:#{#request.maxPricePerMonth} is null or f.pricePerMonth <= :#{#request.maxPricePerMonth})
                AND (:#{#request.minPricePerMonth} is null or f.pricePerMonth >= :#{#request.minPricePerMonth})
                AND (:#{#request.roomsCounts} is null or f.roomsCount in :#{#request.roomsCounts})
            """)
    Page<Flat> findFlatByParams(@Param("request") FlatRequest request, Pageable pageable);
}

