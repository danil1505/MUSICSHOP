package com.guitarstore.guitar_store_api.repository;

import com.guitarstore.guitar_store_api.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {}
