package com.milo.amz.review.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.ShippingAddressService;
import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.service.dto.ShippingAddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ShippingAddress.
 */
@RestController
@RequestMapping("/api")
public class ShippingAddressResource {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressResource.class);
        
    @Inject
    private ShippingAddressService shippingAddressService;

    /**
     * POST  /shipping-addresses : Create a new shippingAddress.
     *
     * @param shippingAddressDTO the shippingAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shippingAddressDTO, or with status 400 (Bad Request) if the shippingAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipping-addresses")
    @Timed
    public ResponseEntity<ShippingAddressDTO> createShippingAddress(@Valid @RequestBody ShippingAddressDTO shippingAddressDTO) throws URISyntaxException {
        log.debug("REST request to save ShippingAddress : {}", shippingAddressDTO);
        if (shippingAddressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("shippingAddress", "idexists", "A new shippingAddress cannot already have an ID")).body(null);
        }
        ShippingAddressDTO result = shippingAddressService.save(shippingAddressDTO);
        return ResponseEntity.created(new URI("/api/shipping-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("shippingAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipping-addresses : Updates an existing shippingAddress.
     *
     * @param shippingAddressDTO the shippingAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shippingAddressDTO,
     * or with status 400 (Bad Request) if the shippingAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the shippingAddressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipping-addresses")
    @Timed
    public ResponseEntity<ShippingAddressDTO> updateShippingAddress(@Valid @RequestBody ShippingAddressDTO shippingAddressDTO) throws URISyntaxException {
        log.debug("REST request to update ShippingAddress : {}", shippingAddressDTO);
        if (shippingAddressDTO.getId() == null) {
            return createShippingAddress(shippingAddressDTO);
        }
        ShippingAddressDTO result = shippingAddressService.save(shippingAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("shippingAddress", shippingAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipping-addresses : get all the shippingAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shippingAddresses in body
     */
    @GetMapping("/shipping-addresses")
    @Timed
    public List<ShippingAddressDTO> getAllShippingAddresses() {
        log.debug("REST request to get all ShippingAddresses");
        return shippingAddressService.findAll();
    }

    /**
     * GET  /shipping-addresses/:id : get the "id" shippingAddress.
     *
     * @param id the id of the shippingAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shippingAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipping-addresses/{id}")
    @Timed
    public ResponseEntity<ShippingAddressDTO> getShippingAddress(@PathVariable Long id) {
        log.debug("REST request to get ShippingAddress : {}", id);
        ShippingAddressDTO shippingAddressDTO = shippingAddressService.findOne(id);
        return Optional.ofNullable(shippingAddressDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shipping-addresses/:id : delete the "id" shippingAddress.
     *
     * @param id the id of the shippingAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipping-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Long id) {
        log.debug("REST request to delete ShippingAddress : {}", id);
        shippingAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("shippingAddress", id.toString())).build();
    }

}
