package com.milo.amz.review.service;

import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing PurchaseOrder.
 */
public interface PurchaseOrderService {

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrderDTO the entity to save
     * @return the persisted entity
     */
    PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO);

    /**
     *  Get all the purchaseOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PurchaseOrderDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" purchaseOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PurchaseOrderDTO findOne(Long id);

    /**
     *  Delete the "id" purchaseOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    List<PurchaseOrderDTO> findOrdersWithIcompleteItems();
}
