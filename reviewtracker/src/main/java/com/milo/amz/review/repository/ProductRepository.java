package com.milo.amz.review.repository;

import com.milo.amz.review.domain.Product;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
public interface ProductRepository extends JpaRepository<Product,Long> {
  List<Product> findAll();
}
