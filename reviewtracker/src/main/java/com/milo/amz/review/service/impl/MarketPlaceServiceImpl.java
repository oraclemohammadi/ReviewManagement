package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.MarketPlaceService;
import com.milo.amz.review.domain.MarketPlace;
import com.milo.amz.review.repository.MarketPlaceRepository;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
import com.milo.amz.review.service.mapper.MarketPlaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MarketPlace.
 */
@Service
@Transactional
public class MarketPlaceServiceImpl implements MarketPlaceService{

    private final Logger log = LoggerFactory.getLogger(MarketPlaceServiceImpl.class);
    
    @Inject
    private MarketPlaceRepository marketPlaceRepository;

    @Inject
    private MarketPlaceMapper marketPlaceMapper;

    /**
     * Save a marketPlace.
     *
     * @param marketPlaceDTO the entity to save
     * @return the persisted entity
     */
    public MarketPlaceDTO save(MarketPlaceDTO marketPlaceDTO) {
        log.debug("Request to save MarketPlace : {}", marketPlaceDTO);
        MarketPlace marketPlace = marketPlaceMapper.marketPlaceDTOToMarketPlace(marketPlaceDTO);
        marketPlace = marketPlaceRepository.save(marketPlace);
        MarketPlaceDTO result = marketPlaceMapper.marketPlaceToMarketPlaceDTO(marketPlace);
        return result;
    }

    /**
     *  Get all the marketPlaces.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MarketPlaceDTO> findAll() {
        log.debug("Request to get all MarketPlaces");
        List<MarketPlaceDTO> result = marketPlaceRepository.findAll().stream()
            .map(marketPlaceMapper::marketPlaceToMarketPlaceDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one marketPlace by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MarketPlaceDTO findOne(Long id) {
        log.debug("Request to get MarketPlace : {}", id);
        MarketPlace marketPlace = marketPlaceRepository.findOne(id);
        MarketPlaceDTO marketPlaceDTO = marketPlaceMapper.marketPlaceToMarketPlaceDTO(marketPlace);
        return marketPlaceDTO;
    }

    /**
     *  Delete the  marketPlace by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketPlace : {}", id);
        marketPlaceRepository.delete(id);
    }
}
