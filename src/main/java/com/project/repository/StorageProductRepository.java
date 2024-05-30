package com.project.repository;

import com.project.entity.StorageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageProductRepository extends JpaRepository<StorageProduct, Long> {

}
