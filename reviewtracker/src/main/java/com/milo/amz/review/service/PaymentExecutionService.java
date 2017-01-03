package com.milo.amz.review.service;

import com.milo.amz.review.service.dto.PaymentExecutionDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing PaymentExecution.
 */
public interface PaymentExecutionService {

    /**
     * Save a paymentExecution.
     *
     * @param paymentExecutionDTO the entity to save
     * @return the persisted entity
     */
    PaymentExecutionDTO save(PaymentExecutionDTO paymentExecutionDTO);

    /**
     *  Get all the paymentExecutions.
     *  
     *  @return the list of entities
     */
    List<PaymentExecutionDTO> findAll();

    /**
     *  Get the "id" paymentExecution.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentExecutionDTO findOne(Long id);

    /**
     *  Delete the "id" paymentExecution.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
