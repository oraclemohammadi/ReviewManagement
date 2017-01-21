package com.milo.amz.review.repository;

import com.milo.amz.review.domain.PurchaseOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.lang.String;

/**
 * Spring Data JPA repository for the PurchaseOrder entity.
 */
@SuppressWarnings("unused")
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {
	@Query("SELECT  purchaseOrder FROM  PurchaseOrder purchaseOrder where  purchaseOrder.numberOfItemsUnshipped+numberOfItemsShipped>(SELECT count(orderItem.id) from PurchaseOrderItem orderItem where orderItem.purchaseOrder.id=purchaseOrder.id)")
	List<PurchaseOrder> findPurchaseOrdersWithIncompleteItems();

	PurchaseOrder findBysellerOrderId(String sellerOrderId);
	
	Page<PurchaseOrder> findByBuyerId(String buyerid,Pageable pageable);
	

	
}
