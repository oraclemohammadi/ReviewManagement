package com.milo.amz.review.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.MarketPlaceService;
import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.service.dto.MarketPlaceDTO;
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
 * REST controller for managing MarketPlace.
 */
@RestController
@RequestMapping("/api")
public class MarketPlaceResource {

    private final Logger log = LoggerFactory.getLogger(MarketPlaceResource.class);
        
    @Inject
    private MarketPlaceService marketPlaceService;

    /**
     * POST  /market-places : Create a new marketPlace.
     *
     * @param marketPlaceDTO the marketPlaceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketPlaceDTO, or with status 400 (Bad Request) if the marketPlace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-places")
    @Timed
    public ResponseEntity<MarketPlaceDTO> createMarketPlace(@RequestBody MarketPlaceDTO marketPlaceDTO) throws URISyntaxException {
        log.debug("REST request to save MarketPlace : {}", marketPlaceDTO);
        if (marketPlaceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("marketPlace", "idexists", "A new marketPlace cannot already have an ID")).body(null);
        }
        MarketPlaceDTO result = marketPlaceService.save(marketPlaceDTO);
        return ResponseEntity.created(new URI("/api/market-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("marketPlace", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-places : Updates an existing marketPlace.
     *
     * @param marketPlaceDTO the marketPlaceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketPlaceDTO,
     * or with status 400 (Bad Request) if the marketPlaceDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketPlaceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-places")
    @Timed
    public ResponseEntity<MarketPlaceDTO> updateMarketPlace(@RequestBody MarketPlaceDTO marketPlaceDTO) throws URISyntaxException {
        log.debug("REST request to update MarketPlace : {}", marketPlaceDTO);
        if (marketPlaceDTO.getId() == null) {
            return createMarketPlace(marketPlaceDTO);
        }
        MarketPlaceDTO result = marketPlaceService.save(marketPlaceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("marketPlace", marketPlaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-places : get all the marketPlaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketPlaces in body
     */
    @GetMapping("/market-places")
    @Timed
    public List<MarketPlaceDTO> getAllMarketPlaces() {
        log.debug("REST request to get all MarketPlaces");
        return marketPlaceService.findAll();
    }

    /**
     * GET  /market-places/:id : get the "id" marketPlace.
     *
     * @param id the id of the marketPlaceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketPlaceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-places/{id}")
    @Timed
    public ResponseEntity<MarketPlaceDTO> getMarketPlace(@PathVariable Long id) {
        log.debug("REST request to get MarketPlace : {}", id);
        MarketPlaceDTO marketPlaceDTO = marketPlaceService.findOne(id);
        return Optional.ofNullable(marketPlaceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /market-places/:id : delete the "id" marketPlace.
     *
     * @param id the id of the marketPlaceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-places/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketPlace(@PathVariable Long id) {
        log.debug("REST request to delete MarketPlace : {}", id);
        marketPlaceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("marketPlace", id.toString())).build();
    }

}
