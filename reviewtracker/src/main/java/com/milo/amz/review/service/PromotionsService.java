package com.milo.amz.review.service;

import com.milo.amz.review.service.dto.PromotionsDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Promotions.
 */
public interface PromotionsService {

    /**
     * Save a promotions.
     *
     * @param promotionsDTO the entity to save
     * @return the persisted entity
     */
    PromotionsDTO save(PromotionsDTO promotionsDTO);

    /**
     *  Get all the promotions.
     *  
     *  @return the list of entities
     */
    List<PromotionsDTO> findAll();

    /**
     *  Get the "id" promotions.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PromotionsDTO findOne(Long id);

    /**
     *  Delete the "id" promotions.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
