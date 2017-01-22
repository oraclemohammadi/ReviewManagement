package com.milo.amz.review.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.milo.amz.review.service.ContactReviewerService;
import com.milo.amz.review.service.dto.ContactReviewerDTO;
import com.milo.amz.review.service.dto.ReviewDTO;
import com.milo.amz.review.web.rest.util.HeaderUtil;
@RestController
@RequestMapping("/api")
public class ContactReviewerResource {
    private final Logger log = LoggerFactory.getLogger(ContactReviewerResource.class);
	@Inject
	ContactReviewerService contactReviewerService;
	
	/**
    * POST  /reviews : Create a new review.
    *
    * @param reviewDTO the reviewDTO to create
    * @return the ResponseEntity with status 201 (Created) and with body the new reviewDTO, or with status 400 (Bad Request) if the review has already an ID
    * @throws URISyntaxException if the Location URI syntax is incorrect
    */
   @PostMapping("/contactReviewer")
   @Timed
   public ResponseEntity<?> sendEmailToReviewer(@RequestBody ContactReviewerDTO contactReviewerDTO) throws URISyntaxException {
       log.debug("REST request to sendEmailToReviewer : {}", contactReviewerDTO);
      
       contactReviewerService.sendEmail(contactReviewerDTO.getOrderId(),contactReviewerDTO.getBuyerId(),contactReviewerDTO.getMarketPlaceId(),contactReviewerDTO.getMessage());
       return ResponseEntity.created(new URI("/api/contactReviewer/")).headers(HeaderUtil.createEntityCreationAlert("contactReviewer", "1"))
           .body("OK");
   }
}
