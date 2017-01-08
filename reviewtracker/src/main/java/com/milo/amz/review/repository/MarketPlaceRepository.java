package com.milo.amz.review.repository;

import com.milo.amz.review.domain.MarketPlace;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MarketPlace entity.
 */
@SuppressWarnings("unused")
public interface MarketPlaceRepository extends JpaRepository<MarketPlace,Long> {

}
