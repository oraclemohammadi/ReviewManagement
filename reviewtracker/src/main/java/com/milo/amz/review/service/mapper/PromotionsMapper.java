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

    @Mapping(source = "purchaseOrderItem.id", target = "purchaseOrderItemId")
    PromotionsDTO promotionsToPromotionsDTO(Promotions promotions);

    List<PromotionsDTO> promotionsToPromotionsDTOs(List<Promotions> promotions);

    @Mapping(source = "purchaseOrderItemId", target = "purchaseOrderItem")
    Promotions promotionsDTOToPromotions(PromotionsDTO promotionsDTO);

    List<Promotions> promotionsDTOsToPromotions(List<PromotionsDTO> promotionsDTOs);

    default PurchaseOrderItem purchaseOrderItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
        purchaseOrderItem.setId(id);
        return purchaseOrderItem;
    }
}
