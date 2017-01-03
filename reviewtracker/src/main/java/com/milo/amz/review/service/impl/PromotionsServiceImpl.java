package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.PromotionsService;
import com.milo.amz.review.domain.Promotions;
import com.milo.amz.review.repository.PromotionsRepository;
import com.milo.amz.review.service.dto.PromotionsDTO;
import com.milo.amz.review.service.mapper.PromotionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Promotions.
 */
@Service
@Transactional
public class PromotionsServiceImpl implements PromotionsService{

    private final Logger log = LoggerFactory.getLogger(PromotionsServiceImpl.class);
    
    @Inject
    private PromotionsRepository promotionsRepository;

    @Inject
    private PromotionsMapper promotionsMapper;

    /**
     * Save a promotions.
     *
     * @param promotionsDTO the entity to save
     * @return the persisted entity
     */
    public PromotionsDTO save(PromotionsDTO promotionsDTO) {
        log.debug("Request to save Promotions : {}", promotionsDTO);
        Promotions promotions = promotionsMapper.promotionsDTOToPromotions(promotionsDTO);
        promotions = promotionsRepository.save(promotions);
        PromotionsDTO result = promotionsMapper.promotionsToPromotionsDTO(promotions);
        return result;
    }

    /**
     *  Get all the promotions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PromotionsDTO> findAll() {
        log.debug("Request to get all Promotions");
        List<PromotionsDTO> result = promotionsRepository.findAll().stream()
            .map(promotionsMapper::promotionsToPromotionsDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one promotions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PromotionsDTO findOne(Long id) {
        log.debug("Request to get Promotions : {}", id);
        Promotions promotions = promotionsRepository.findOne(id);
        PromotionsDTO promotionsDTO = promotionsMapper.promotionsToPromotionsDTO(promotions);
        return promotionsDTO;
    }

    /**
     *  Delete the  promotions by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Promotions : {}", id);
        promotionsRepository.delete(id);
    }
}
