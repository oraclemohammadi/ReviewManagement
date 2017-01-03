package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.PaymentExecutionService;
import com.milo.amz.review.domain.PaymentExecution;
import com.milo.amz.review.repository.PaymentExecutionRepository;
import com.milo.amz.review.service.dto.PaymentExecutionDTO;
import com.milo.amz.review.service.mapper.PaymentExecutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PaymentExecution.
 */
@Service
@Transactional
public class PaymentExecutionServiceImpl implements PaymentExecutionService{

    private final Logger log = LoggerFactory.getLogger(PaymentExecutionServiceImpl.class);
    
    @Inject
    private PaymentExecutionRepository paymentExecutionRepository;

    @Inject
    private PaymentExecutionMapper paymentExecutionMapper;

    /**
     * Save a paymentExecution.
     *
     * @param paymentExecutionDTO the entity to save
     * @return the persisted entity
     */
    public PaymentExecutionDTO save(PaymentExecutionDTO paymentExecutionDTO) {
        log.debug("Request to save PaymentExecution : {}", paymentExecutionDTO);
        PaymentExecution paymentExecution = paymentExecutionMapper.paymentExecutionDTOToPaymentExecution(paymentExecutionDTO);
        paymentExecution = paymentExecutionRepository.save(paymentExecution);
        PaymentExecutionDTO result = paymentExecutionMapper.paymentExecutionToPaymentExecutionDTO(paymentExecution);
        return result;
    }

    /**
     *  Get all the paymentExecutions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PaymentExecutionDTO> findAll() {
        log.debug("Request to get all PaymentExecutions");
        List<PaymentExecutionDTO> result = paymentExecutionRepository.findAll().stream()
            .map(paymentExecutionMapper::paymentExecutionToPaymentExecutionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one paymentExecution by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PaymentExecutionDTO findOne(Long id) {
        log.debug("Request to get PaymentExecution : {}", id);
        PaymentExecution paymentExecution = paymentExecutionRepository.findOne(id);
        PaymentExecutionDTO paymentExecutionDTO = paymentExecutionMapper.paymentExecutionToPaymentExecutionDTO(paymentExecution);
        return paymentExecutionDTO;
    }

    /**
     *  Delete the  paymentExecution by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentExecution : {}", id);
        paymentExecutionRepository.delete(id);
    }
}
