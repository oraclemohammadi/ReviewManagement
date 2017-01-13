package com.milo.amz.review.repository;

import com.milo.amz.review.domain.PurchaseOrderItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PurchaseOrderItem entity.
 */
@SuppressWarnings("unused")
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem,Long> {
	  @Query("select (purchaseOrderItem.asin) from PurchaseOrderItem purchaseOrderItem GROUP BY asin")
	  List<String> findProductAsinForRequestItems();
}
