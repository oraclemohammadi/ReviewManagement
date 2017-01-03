package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.ReviewDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReviewMapper {

    @Mapping(source = "product.id", target = "productId")
    ReviewDTO reviewToReviewDTO(Review review);

    List<ReviewDTO> reviewsToReviewDTOs(List<Review> reviews);

    @Mapping(source = "productId", target = "product")
    Review reviewDTOToReview(ReviewDTO reviewDTO);

    List<Review> reviewDTOsToReviews(List<ReviewDTO> reviewDTOs);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
