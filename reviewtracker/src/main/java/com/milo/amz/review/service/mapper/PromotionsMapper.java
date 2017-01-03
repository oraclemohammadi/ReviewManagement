package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.PromotionsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Promotions and its DTO PromotionsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PromotionsMapper {

    PromotionsDTO promotionsToPromotionsDTO(Promotions promotions);

    List<PromotionsDTO> promotionsToPromotionsDTOs(List<Promotions> promotions);

    Promotions promotionsDTOToPromotions(PromotionsDTO promotionsDTO);

    List<Promotions> promotionsDTOsToPromotions(List<PromotionsDTO> promotionsDTOs);
}
