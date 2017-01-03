package com.milo.amz.review.repository;

import com.milo.amz.review.domain.Promotions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Promotions entity.
 */
@SuppressWarnings("unused")
public interface PromotionsRepository extends JpaRepository<Promotions,Long> {

}
