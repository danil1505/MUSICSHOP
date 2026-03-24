package com.guitarstore.guitar_store_api.repository;

import com.guitarstore.guitar_store_api.model.Guitar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, UUID> {

    @Query(value =
        "SELECT * FROM guitars " +
        "WHERE " +
        "    (:search IS NULL OR search_vector @@ plainto_tsquery('russian', :search)) " +
        "AND (:brandId IS NULL OR brand_id = :brandId) " +
        "AND (:catId IS NULL OR category_id = :catId) " +
        "AND (:minPrice IS NULL OR price >= :minPrice) " +
        "AND (:maxPrice IS NULL OR price <= :maxPrice) " +
        "ORDER BY created_at DESC",
        countQuery =
        "SELECT count(*) FROM guitars " +
        "WHERE " +
        "    (:search IS NULL OR search_vector @@ plainto_tsquery('russian', :search)) " +
        "AND (:brandId IS NULL OR brand_id = :brandId) " +
        "AND (:catId IS NULL OR category_id = :catId) " +
        "AND (:minPrice IS NULL OR price >= :minPrice) " +
        "AND (:maxPrice IS NULL OR price <= :maxPrice)",
        nativeQuery = true)
    Page<Guitar> findWithFilters(
            @Param("search")   String search,
            @Param("brandId")  Integer brandId,
            @Param("catId")    Integer catId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );
}
