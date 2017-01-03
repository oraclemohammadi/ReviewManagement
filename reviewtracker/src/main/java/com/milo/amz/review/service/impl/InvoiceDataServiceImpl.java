package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.InvoiceDataService;
import com.milo.amz.review.domain.InvoiceData;
import com.milo.amz.review.repository.InvoiceDataRepository;
import com.milo.amz.review.service.dto.InvoiceDataDTO;
import com.milo.amz.review.service.mapper.InvoiceDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InvoiceData.
 */
@Service
@Transactional
public class InvoiceDataServiceImpl implements InvoiceDataService{

    private final Logger log = LoggerFactory.getLogger(InvoiceDataServiceImpl.class);
    
    @Inject
    private InvoiceDataRepository invoiceDataRepository;

    @Inject
    private InvoiceDataMapper invoiceDataMapper;

    /**
     * Save a invoiceData.
     *
     * @param invoiceDataDTO the entity to save
     * @return the persisted entity
     */
    public InvoiceDataDTO save(InvoiceDataDTO invoiceDataDTO) {
        log.debug("Request to save InvoiceData : {}", invoiceDataDTO);
        InvoiceData invoiceData = invoiceDataMapper.invoiceDataDTOToInvoiceData(invoiceDataDTO);
        invoiceData = invoiceDataRepository.save(invoiceData);
        InvoiceDataDTO result = invoiceDataMapper.invoiceDataToInvoiceDataDTO(invoiceData);
        return result;
    }

    /**
     *  Get all the invoiceData.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<InvoiceDataDTO> findAll() {
        log.debug("Request to get all InvoiceData");
        List<InvoiceDataDTO> result = invoiceDataRepository.findAll().stream()
            .map(invoiceDataMapper::invoiceDataToInvoiceDataDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one invoiceData by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InvoiceDataDTO findOne(Long id) {
        log.debug("Request to get InvoiceData : {}", id);
        InvoiceData invoiceData = invoiceDataRepository.findOne(id);
        InvoiceDataDTO invoiceDataDTO = invoiceDataMapper.invoiceDataToInvoiceDataDTO(invoiceData);
        return invoiceDataDTO;
    }

    /**
     *  Delete the  invoiceData by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoiceData : {}", id);
        invoiceDataRepository.delete(id);
    }
}
