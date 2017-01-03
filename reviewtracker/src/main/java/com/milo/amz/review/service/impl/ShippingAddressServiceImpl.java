package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.ShippingAddressService;
import com.milo.amz.review.domain.ShippingAddress;
import com.milo.amz.review.repository.ShippingAddressRepository;
import com.milo.amz.review.service.dto.ShippingAddressDTO;
import com.milo.amz.review.service.mapper.ShippingAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ShippingAddress.
 */
@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService{

    private final Logger log = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);
    
    @Inject
    private ShippingAddressRepository shippingAddressRepository;

    @Inject
    private ShippingAddressMapper shippingAddressMapper;

    /**
     * Save a shippingAddress.
     *
     * @param shippingAddressDTO the entity to save
     * @return the persisted entity
     */
    public ShippingAddressDTO save(ShippingAddressDTO shippingAddressDTO) {
        log.debug("Request to save ShippingAddress : {}", shippingAddressDTO);
        ShippingAddress shippingAddress = shippingAddressMapper.shippingAddressDTOToShippingAddress(shippingAddressDTO);
        shippingAddress = shippingAddressRepository.save(shippingAddress);
        ShippingAddressDTO result = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);
        return result;
    }

    /**
     *  Get all the shippingAddresses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ShippingAddressDTO> findAll() {
        log.debug("Request to get all ShippingAddresses");
        List<ShippingAddressDTO> result = shippingAddressRepository.findAll().stream()
            .map(shippingAddressMapper::shippingAddressToShippingAddressDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one shippingAddress by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ShippingAddressDTO findOne(Long id) {
        log.debug("Request to get ShippingAddress : {}", id);
        ShippingAddress shippingAddress = shippingAddressRepository.findOne(id);
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.shippingAddressToShippingAddressDTO(shippingAddress);
        return shippingAddressDTO;
    }

    /**
     *  Delete the  shippingAddress by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ShippingAddress : {}", id);
        shippingAddressRepository.delete(id);
    }
}
