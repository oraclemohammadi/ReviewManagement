package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.PurchaseOrderItemService;
import com.milo.amz.review.domain.PurchaseOrderItem;
import com.milo.amz.review.repository.PurchaseOrderItemRepository;
import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;
import com.milo.amz.review.service.mapper.PurchaseOrderItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PurchaseOrderItem.
 */
@Service
@Transactional
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService{

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderItemServiceImpl.class);
    
    @Inject
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Inject
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    /**
     * Save a purchaseOrderItem.
     *
     * @param purchaseOrderItemDTO the entity to save
     * @return the persisted entity
     */
    public PurchaseOrderItemDTO save(PurchaseOrderItemDTO purchaseOrderItemDTO) {
        log.debug("Request to save PurchaseOrderItem : {}", purchaseOrderItemDTO);
        PurchaseOrderItem purchaseOrderItem = purchaseOrderItemMapper.purchaseOrderItemDTOToPurchaseOrderItem(purchaseOrderItemDTO);
        purchaseOrderItem = purchaseOrderItemRepository.save(purchaseOrderItem);
        PurchaseOrderItemDTO result = purchaseOrderItemMapper.purchaseOrderItemToPurchaseOrderItemDTO(purchaseOrderItem);
        return result;
    }

    /**
     *  Get all the purchaseOrderItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PurchaseOrderItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrderItems");
        Page<PurchaseOrderItem> result = purchaseOrderItemRepository.findAll(pageable);
        return result.map(purchaseOrderItem -> purchaseOrderItemMapper.purchaseOrderItemToPurchaseOrderItemDTO(purchaseOrderItem));
    }

    /**
     *  Get one purchaseOrderItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PurchaseOrderItemDTO findOne(Long id) {
        log.debug("Request to get PurchaseOrderItem : {}", id);
        PurchaseOrderItem purchaseOrderItem = purchaseOrderItemRepository.findOne(id);
        PurchaseOrderItemDTO purchaseOrderItemDTO = purchaseOrderItemMapper.purchaseOrderItemToPurchaseOrderItemDTO(purchaseOrderItem);
        return purchaseOrderItemDTO;
    }

    /**
     *  Delete the  purchaseOrderItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrderItem : {}", id);
        purchaseOrderItemRepository.delete(id);
    }

	@Override
	public List<String> findProductsForRequestItem() {
		// TODO Auto-generated method stub
		return purchaseOrderItemRepository.findProductAsinForRequestItems();
	}
}
