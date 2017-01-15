package com.milo.amz.review.service.impl;

import com.milo.amz.review.service.ReviewService;
import com.milo.amz.review.domain.Review;
import com.milo.amz.review.repository.ReviewRepository;
import com.milo.amz.review.service.dto.ReviewDTO;
import com.milo.amz.review.service.mapper.ReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Review.
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);
    
    @Inject
    private ReviewRepository reviewRepository;

    @Inject
    private ReviewMapper reviewMapper;

    /**
     * Save a review.
     *
     * @param reviewDTO the entity to save
     * @return the persisted entity
     */
    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save Review : {}", reviewDTO);
        Review review = reviewMapper.reviewDTOToReview(reviewDTO);
        review = reviewRepository.save(review);
        ReviewDTO result = reviewMapper.reviewToReviewDTO(review);
        return result;
    }

    /**
     *  Get all the reviews.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reviews");
        Page<Review> result = reviewRepository.findAll(pageable);
        return result.map(review -> reviewMapper.reviewToReviewDTO(review));
    }

    /**
     *  Get one review by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ReviewDTO findOne(Long id) {
        log.debug("Request to get Review : {}", id);
        Review review = reviewRepository.findOne(id);
        ReviewDTO reviewDTO = reviewMapper.reviewToReviewDTO(review);
        return reviewDTO;
    }

    /**
     *  Delete the  review by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.delete(id);
    }

	@Override
	public long countReviewByproduct(long productId) {
		// TODO Auto-generated method stub
		return reviewRepository.countReviewByproduct(productId);
	}

	@Override
	public ReviewDTO findByReviewID(String reviewid) {
		// TODO Auto-generated method stub
		return  reviewMapper.reviewToReviewDTO(reviewRepository.findByReviewID(reviewid));
	}
    
   
}
