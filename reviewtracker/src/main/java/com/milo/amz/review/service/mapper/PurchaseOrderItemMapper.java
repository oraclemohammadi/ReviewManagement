package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PurchaseOrderItem and its DTO PurchaseOrderItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseOrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    PurchaseOrderItemDTO purchaseOrderItemToPurchaseOrderItemDTO(PurchaseOrderItem purchaseOrderItem);

    List<PurchaseOrderItemDTO> purchaseOrderItemsToPurchaseOrderItemDTOs(List<PurchaseOrderItem> purchaseOrderItems);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PurchaseOrderItem purchaseOrderItemDTOToPurchaseOrderItem(PurchaseOrderItemDTO purchaseOrderItemDTO);

    List<PurchaseOrderItem> purchaseOrderItemDTOsToPurchaseOrderItems(List<PurchaseOrderItemDTO> purchaseOrderItemDTOs);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default PurchaseOrder purchaseOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        return purchaseOrder;
    }
}
