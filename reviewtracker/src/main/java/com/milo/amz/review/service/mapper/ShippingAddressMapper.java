package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.ShippingAddressDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ShippingAddress and its DTO ShippingAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShippingAddressMapper {

    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    ShippingAddressDTO shippingAddressToShippingAddressDTO(ShippingAddress shippingAddress);

    List<ShippingAddressDTO> shippingAddressesToShippingAddressDTOs(List<ShippingAddress> shippingAddresses);

    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    ShippingAddress shippingAddressDTOToShippingAddress(ShippingAddressDTO shippingAddressDTO);

    List<ShippingAddress> shippingAddressDTOsToShippingAddresses(List<ShippingAddressDTO> shippingAddressDTOs);

    default PurchaseOrder purchaseOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        return purchaseOrder;
    }
}
