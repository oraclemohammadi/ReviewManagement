package com.milo.amz.review.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.amazonservices.mws.orders._2013_09_01.model.Address;
import com.amazonservices.mws.orders._2013_09_01.model.Order;
import com.amazonservices.mws.orders._2013_09_01.model.PaymentExecutionDetailItem;
import com.milo.amz.review.service.dto.PaymentExecutionDTO;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.dto.ShippingAddressDTO;


@Mapper(componentModel = "spring")

public interface OrderMapper {
    @Mapping(source="orderTotal.currencyCode",target="currencyCode")
    @Mapping(source="orderTotal.amount",target="orderTotal")
    public PurchaseOrderDTO orderDTOTOPurchaseOrder(Order order);
    
    public List<PurchaseOrderDTO> orderDTOTOPurchaseOrder(List<Order> orderList);


    /*@Mapping(source="shippingAddress.name",target="name")
    @Mapping(source="shippingAddress.addressLine1",target="addressLine1")
    @Mapping(source="shippingAddress.addressLine2",target="addressLine2")
    @Mapping(source="shippingAddress.addressLine3",target="addressLine3")
    @Mapping(source="shippingAddress.city",target="city")
    @Mapping(source="shippingAddress.district",target="district")
    @Mapping(source="shippingAddress.stateOrRegion",target="stateOrRegion")
    @Mapping(source="shippingAddress.postalCode",target="postalCode")
    @Mapping(source="shippingAddress.countryCode",target="county")
    @Mapping(source="shippingAddress.phone",target="phone")*/
    public ShippingAddressDTO orderToShippingAddressDTO(Address address);
    
    @Mapping(source="payment.currencyCode",target="currencyCode")
    @Mapping(source="payment.amount",target="paymentAmount")
    public PaymentExecutionDTO orderToShippingAddressDTO(PaymentExecutionDetailItem paymentExecutionDetailItem);
    
    public List<PaymentExecutionDTO> orderToShippingAddressDTO(List<PaymentExecutionDetailItem> paymentExecutionDetailItem);
}
