package com.milo.amz.review.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.PaymentExecutionService;
import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.service.dto.PaymentExecutionDTO;
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
 * REST controller for managing PaymentExecution.
 */
@RestController
@RequestMapping("/api")
public class PaymentExecutionResource {

    private final Logger log = LoggerFactory.getLogger(PaymentExecutionResource.class);
        
    @Inject
    private PaymentExecutionService paymentExecutionService;

    /**
     * POST  /payment-executions : Create a new paymentExecution.
     *
     * @param paymentExecutionDTO the paymentExecutionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentExecutionDTO, or with status 400 (Bad Request) if the paymentExecution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-executions")
    @Timed
    public ResponseEntity<PaymentExecutionDTO> createPaymentExecution(@RequestBody PaymentExecutionDTO paymentExecutionDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentExecution : {}", paymentExecutionDTO);
        if (paymentExecutionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("paymentExecution", "idexists", "A new paymentExecution cannot already have an ID")).body(null);
        }
        PaymentExecutionDTO result = paymentExecutionService.save(paymentExecutionDTO);
        return ResponseEntity.created(new URI("/api/payment-executions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("paymentExecution", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-executions : Updates an existing paymentExecution.
     *
     * @param paymentExecutionDTO the paymentExecutionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentExecutionDTO,
     * or with status 400 (Bad Request) if the paymentExecutionDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentExecutionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-executions")
    @Timed
    public ResponseEntity<PaymentExecutionDTO> updatePaymentExecution(@RequestBody PaymentExecutionDTO paymentExecutionDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentExecution : {}", paymentExecutionDTO);
        if (paymentExecutionDTO.getId() == null) {
            return createPaymentExecution(paymentExecutionDTO);
        }
        PaymentExecutionDTO result = paymentExecutionService.save(paymentExecutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("paymentExecution", paymentExecutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-executions : get all the paymentExecutions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentExecutions in body
     */
    @GetMapping("/payment-executions")
    @Timed
    public List<PaymentExecutionDTO> getAllPaymentExecutions() {
        log.debug("REST request to get all PaymentExecutions");
        return paymentExecutionService.findAll();
    }

    /**
     * GET  /payment-executions/:id : get the "id" paymentExecution.
     *
     * @param id the id of the paymentExecutionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentExecutionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-executions/{id}")
    @Timed
    public ResponseEntity<PaymentExecutionDTO> getPaymentExecution(@PathVariable Long id) {
        log.debug("REST request to get PaymentExecution : {}", id);
        PaymentExecutionDTO paymentExecutionDTO = paymentExecutionService.findOne(id);
        return Optional.ofNullable(paymentExecutionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payment-executions/:id : delete the "id" paymentExecution.
     *
     * @param id the id of the paymentExecutionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-executions/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentExecution(@PathVariable Long id) {
        log.debug("REST request to delete PaymentExecution : {}", id);
        paymentExecutionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("paymentExecution", id.toString())).build();
    }

}
