package com.milo.amz.review.service;

import com.milo.amz.review.service.dto.MarketPlaceDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing MarketPlace.
 */
public interface MarketPlaceService {

    /**
     * Save a marketPlace.
     *
     * @param marketPlaceDTO the entity to save
     * @return the persisted entity
     */
    MarketPlaceDTO save(MarketPlaceDTO marketPlaceDTO);

    /**
     *  Get all the marketPlaces.
     *  
     *  @return the list of entities
     */
    List<MarketPlaceDTO> findAll();

    /**
     *  Get the "id" marketPlace.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MarketPlaceDTO findOne(Long id);

    /**
     *  Delete the "id" marketPlace.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
