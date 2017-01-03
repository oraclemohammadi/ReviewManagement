package com.milo.amz.review.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.PurchaseOrderItemService;
import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.web.rest.util.PaginationUtil;
import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;
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
 * REST controller for managing PurchaseOrderItem.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderItemResource.class);
        
    @Inject
    private PurchaseOrderItemService purchaseOrderItemService;

    /**
     * POST  /purchase-order-items : Create a new purchaseOrderItem.
     *
     * @param purchaseOrderItemDTO the purchaseOrderItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseOrderItemDTO, or with status 400 (Bad Request) if the purchaseOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-order-items")
    @Timed
    public ResponseEntity<PurchaseOrderItemDTO> createPurchaseOrderItem(@RequestBody PurchaseOrderItemDTO purchaseOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrderItem : {}", purchaseOrderItemDTO);
        if (purchaseOrderItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("purchaseOrderItem", "idexists", "A new purchaseOrderItem cannot already have an ID")).body(null);
        }
        PurchaseOrderItemDTO result = purchaseOrderItemService.save(purchaseOrderItemDTO);
        return ResponseEntity.created(new URI("/api/purchase-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("purchaseOrderItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-order-items : Updates an existing purchaseOrderItem.
     *
     * @param purchaseOrderItemDTO the purchaseOrderItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseOrderItemDTO,
     * or with status 400 (Bad Request) if the purchaseOrderItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseOrderItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-order-items")
    @Timed
    public ResponseEntity<PurchaseOrderItemDTO> updatePurchaseOrderItem(@RequestBody PurchaseOrderItemDTO purchaseOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrderItem : {}", purchaseOrderItemDTO);
        if (purchaseOrderItemDTO.getId() == null) {
            return createPurchaseOrderItem(purchaseOrderItemDTO);
        }
        PurchaseOrderItemDTO result = purchaseOrderItemService.save(purchaseOrderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("purchaseOrderItem", purchaseOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-order-items : get all the purchaseOrderItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseOrderItems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/purchase-order-items")
    @Timed
    public ResponseEntity<List<PurchaseOrderItemDTO>> getAllPurchaseOrderItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PurchaseOrderItems");
        Page<PurchaseOrderItemDTO> page = purchaseOrderItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-order-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-order-items/:id : get the "id" purchaseOrderItem.
     *
     * @param id the id of the purchaseOrderItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseOrderItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-order-items/{id}")
    @Timed
    public ResponseEntity<PurchaseOrderItemDTO> getPurchaseOrderItem(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrderItem : {}", id);
        PurchaseOrderItemDTO purchaseOrderItemDTO = purchaseOrderItemService.findOne(id);
        return Optional.ofNullable(purchaseOrderItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /purchase-order-items/:id : delete the "id" purchaseOrderItem.
     *
     * @param id the id of the purchaseOrderItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrderItem : {}", id);
        purchaseOrderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("purchaseOrderItem", id.toString())).build();
    }

}
