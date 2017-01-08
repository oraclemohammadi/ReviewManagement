package com.milo.amz.review.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.PurchaseOrderService;
import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.web.rest.util.PaginationUtil;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PurchaseOrder.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderResource.class);
        
    @Inject
    private PurchaseOrderService purchaseOrderService;

    /**
     * POST  /purchase-orders : Create a new purchaseOrder.
     *
     * @param purchaseOrderDTO the purchaseOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseOrderDTO, or with status 400 (Bad Request) if the purchaseOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-orders")
    @Timed
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrder : {}", purchaseOrderDTO);
        if (purchaseOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("purchaseOrder", "idexists", "A new purchaseOrder cannot already have an ID")).body(null);
        }
        PurchaseOrderDTO result = purchaseOrderService.save(purchaseOrderDTO);
        return ResponseEntity.created(new URI("/api/purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("purchaseOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-orders : Updates an existing purchaseOrder.
     *
     * @param purchaseOrderDTO the purchaseOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseOrderDTO,
     * or with status 400 (Bad Request) if the purchaseOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-orders")
    @Timed
    public ResponseEntity<PurchaseOrderDTO> updatePurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrder : {}", purchaseOrderDTO);
        if (purchaseOrderDTO.getId() == null) {
            return createPurchaseOrder(purchaseOrderDTO);
        }
        PurchaseOrderDTO result = purchaseOrderService.save(purchaseOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("purchaseOrder", purchaseOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-orders : get all the purchaseOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseOrders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/purchase-orders")
    @Timed
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPurchaseOrders(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PurchaseOrders");
        Page<PurchaseOrderDTO> page = purchaseOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-orders/:id : get the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-orders/{id}")
    @Timed
    public ResponseEntity<PurchaseOrderDTO> getPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrder : {}", id);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.findOne(id);
        return Optional.ofNullable(purchaseOrderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /purchase-orders/:id : delete the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-orders/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrder : {}", id);
        purchaseOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("purchaseOrder", id.toString())).build();
    }
    
    @GetMapping("/incomplete-purchase-orders")
    @Timed
    public ResponseEntity<List<PurchaseOrderDTO>> getPurchaseOrderWithIncompleteItems() {
    	 	return new ResponseEntity<>(purchaseOrderService.findOrdersWithIcompleteItems(), null, HttpStatus.OK);
       }
}
