package com.milo.amz.review.service;

import com.milo.amz.review.service.dto.ShippingAddressDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ShippingAddress.
 */
public interface ShippingAddressService {

    /**
     * Save a shippingAddress.
     *
     * @param shippingAddressDTO the entity to save
     * @return the persisted entity
     */
    ShippingAddressDTO save(ShippingAddressDTO shippingAddressDTO);

    /**
     *  Get all the shippingAddresses.
     *  
     *  @return the list of entities
     */
    List<ShippingAddressDTO> findAll();

    /**
     *  Get the "id" shippingAddress.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShippingAddressDTO findOne(Long id);

    /**
     *  Delete the "id" shippingAddress.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
