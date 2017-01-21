package com.milo.amz.review.repository;

import com.milo.amz.review.domain.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.milo.amz.review.domain.Product;
import java.lang.String;

/**
 * Spring Data JPA repository for the Review entity.
 */
@SuppressWarnings("unused")
public interface ReviewRepository extends JpaRepository<Review,Long> {
  @Query("select count(review) from Review review where review.product.id=?1")
  long countReviewByproduct(@Param("productId") long productId);
  
  Review findByReviewID(String reviewid);
  
  @Query("select review from Review review where review.product.asin=?1")
  Page<Review> findByProductAsin(String asin,Pageable pageable);
}
