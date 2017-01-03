package com.milo.amz.review.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.InvoiceDataService;
import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.service.dto.InvoiceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing InvoiceData.
 */
@RestController
@RequestMapping("/api")
public class InvoiceDataResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceDataResource.class);
        
    @Inject
    private InvoiceDataService invoiceDataService;

    /**
     * POST  /invoice-data : Create a new invoiceData.
     *
     * @param invoiceDataDTO the invoiceDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceDataDTO, or with status 400 (Bad Request) if the invoiceData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-data")
    @Timed
    public ResponseEntity<InvoiceDataDTO> createInvoiceData(@RequestBody InvoiceDataDTO invoiceDataDTO) throws URISyntaxException {
        log.debug("REST request to save InvoiceData : {}", invoiceDataDTO);
        if (invoiceDataDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("invoiceData", "idexists", "A new invoiceData cannot already have an ID")).body(null);
        }
        InvoiceDataDTO result = invoiceDataService.save(invoiceDataDTO);
        return ResponseEntity.created(new URI("/api/invoice-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("invoiceData", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-data : Updates an existing invoiceData.
     *
     * @param invoiceDataDTO the invoiceDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceDataDTO,
     * or with status 400 (Bad Request) if the invoiceDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoiceDataDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-data")
    @Timed
    public ResponseEntity<InvoiceDataDTO> updateInvoiceData(@RequestBody InvoiceDataDTO invoiceDataDTO) throws URISyntaxException {
        log.debug("REST request to update InvoiceData : {}", invoiceDataDTO);
        if (invoiceDataDTO.getId() == null) {
            return createInvoiceData(invoiceDataDTO);
        }
        InvoiceDataDTO result = invoiceDataService.save(invoiceDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("invoiceData", invoiceDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-data : get all the invoiceData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceData in body
     */
    @GetMapping("/invoice-data")
    @Timed
    public List<InvoiceDataDTO> getAllInvoiceData() {
        log.debug("REST request to get all InvoiceData");
        return invoiceDataService.findAll();
    }

    /**
     * GET  /invoice-data/:id : get the "id" invoiceData.
     *
     * @param id the id of the invoiceDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-data/{id}")
    @Timed
    public ResponseEntity<InvoiceDataDTO> getInvoiceData(@PathVariable Long id) {
        log.debug("REST request to get InvoiceData : {}", id);
        InvoiceDataDTO invoiceDataDTO = invoiceDataService.findOne(id);
        return Optional.ofNullable(invoiceDataDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoice-data/:id : delete the "id" invoiceData.
     *
     * @param id the id of the invoiceDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoiceData(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceData : {}", id);
        invoiceDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("invoiceData", id.toString())).build();
    }

}
