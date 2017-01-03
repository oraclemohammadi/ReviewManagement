package com.milo.amz.review.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.PromotionsService;
import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.service.dto.PromotionsDTO;
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
 * REST controller for managing Promotions.
 */
@RestController
@RequestMapping("/api")
public class PromotionsResource {

    private final Logger log = LoggerFactory.getLogger(PromotionsResource.class);
        
    @Inject
    private PromotionsService promotionsService;

    /**
     * POST  /promotions : Create a new promotions.
     *
     * @param promotionsDTO the promotionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new promotionsDTO, or with status 400 (Bad Request) if the promotions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/promotions")
    @Timed
    public ResponseEntity<PromotionsDTO> createPromotions(@RequestBody PromotionsDTO promotionsDTO) throws URISyntaxException {
        log.debug("REST request to save Promotions : {}", promotionsDTO);
        if (promotionsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("promotions", "idexists", "A new promotions cannot already have an ID")).body(null);
        }
        PromotionsDTO result = promotionsService.save(promotionsDTO);
        return ResponseEntity.created(new URI("/api/promotions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("promotions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /promotions : Updates an existing promotions.
     *
     * @param promotionsDTO the promotionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated promotionsDTO,
     * or with status 400 (Bad Request) if the promotionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the promotionsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/promotions")
    @Timed
    public ResponseEntity<PromotionsDTO> updatePromotions(@RequestBody PromotionsDTO promotionsDTO) throws URISyntaxException {
        log.debug("REST request to update Promotions : {}", promotionsDTO);
        if (promotionsDTO.getId() == null) {
            return createPromotions(promotionsDTO);
        }
        PromotionsDTO result = promotionsService.save(promotionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("promotions", promotionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /promotions : get all the promotions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of promotions in body
     */
    @GetMapping("/promotions")
    @Timed
    public List<PromotionsDTO> getAllPromotions() {
        log.debug("REST request to get all Promotions");
        return promotionsService.findAll();
    }

    /**
     * GET  /promotions/:id : get the "id" promotions.
     *
     * @param id the id of the promotionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the promotionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/promotions/{id}")
    @Timed
    public ResponseEntity<PromotionsDTO> getPromotions(@PathVariable Long id) {
        log.debug("REST request to get Promotions : {}", id);
        PromotionsDTO promotionsDTO = promotionsService.findOne(id);
        return Optional.ofNullable(promotionsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /promotions/:id : delete the "id" promotions.
     *
     * @param id the id of the promotionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/promotions/{id}")
    @Timed
    public ResponseEntity<Void> deletePromotions(@PathVariable Long id) {
        log.debug("REST request to delete Promotions : {}", id);
        promotionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("promotions", id.toString())).build();
    }

}
