package com.milo.amz.review.service;

import com.milo.amz.review.service.dto.InvoiceDataDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing InvoiceData.
 */
public interface InvoiceDataService {

    /**
     * Save a invoiceData.
     *
     * @param invoiceDataDTO the entity to save
     * @return the persisted entity
     */
    InvoiceDataDTO save(InvoiceDataDTO invoiceDataDTO);

    /**
     *  Get all the invoiceData.
     *  
     *  @return the list of entities
     */
    List<InvoiceDataDTO> findAll();

    /**
     *  Get the "id" invoiceData.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InvoiceDataDTO findOne(Long id);

    /**
     *  Delete the "id" invoiceData.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
