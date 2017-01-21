package com.milo.amz.review.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milo.amz.review.domain.PurchaseOrder;
import com.milo.amz.review.repository.PurchaseOrderRepository;
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.mapper.PurchaseOrderMapper;

/**
 * Service Implementation for managing PurchaseOrder.
 */
@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);
    
    @Inject
    private PurchaseOrderRepository purchaseOrderRepository;

    @Inject
    private PurchaseOrderMapper purchaseOrderMapper;

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrderDTO the entity to save
     * @return the persisted entity
     */
    public PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO) {
        log.debug("Request to save PurchaseOrder : {}", purchaseOrderDTO);
        PurchaseOrder purchaseOrder = purchaseOrderMapper.purchaseOrderDTOToPurchaseOrder(purchaseOrderDTO);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        PurchaseOrderDTO result = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder);
        return result;
    }

    /**
     *  Get all the purchaseOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PurchaseOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrders");
        Page<PurchaseOrder> result = purchaseOrderRepository.findAll(pageable);
        return result.map(purchaseOrder -> purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder));
    }

    /**
     *  Get one purchaseOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PurchaseOrderDTO findOne(Long id) {
        log.debug("Request to get PurchaseOrder : {}", id);
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(id);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder);
        return purchaseOrderDTO;
    }

    /**
     *  Delete the  purchaseOrder by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrder : {}", id);
        purchaseOrderRepository.delete(id);
    }
    
    public List<PurchaseOrderDTO> findOrdersWithIcompleteItems()
    {
    	return purchaseOrderMapper.purchaseOrdersToPurchaseOrderDTOs(purchaseOrderRepository.findPurchaseOrdersWithIncompleteItems());
    }

	@Override
	public PurchaseOrderDTO findBySellerOrderId(String sellerOrderId) {
		return purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrderRepository.findBysellerOrderId(sellerOrderId));
	}

	@Override
	public Page<PurchaseOrderDTO> findByBuyerId(String buyerId,Pageable pageable) {
		  log.debug("Request to get all PurchaseOrders");
	      Page<PurchaseOrder> result = purchaseOrderRepository.findByBuyerId(buyerId,pageable);
	      return result.map(purchaseOrder -> purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder));
	}
}
