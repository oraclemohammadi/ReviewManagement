package com.milo.amz.review.repository;

import com.milo.amz.review.domain.ShippingAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShippingAddress entity.
 */
@SuppressWarnings("unused")
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {

}
