package com.milo.amz.review.service;

import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing PurchaseOrderItem.
 */
public interface PurchaseOrderItemService {

    /**
     * Save a purchaseOrderItem.
     *
     * @param purchaseOrderItemDTO the entity to save
     * @return the persisted entity
     */
    PurchaseOrderItemDTO save(PurchaseOrderItemDTO purchaseOrderItemDTO);

    /**
     *  Get all the purchaseOrderItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PurchaseOrderItemDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" purchaseOrderItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PurchaseOrderItemDTO findOne(Long id);

    /**
     *  Delete the "id" purchaseOrderItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
