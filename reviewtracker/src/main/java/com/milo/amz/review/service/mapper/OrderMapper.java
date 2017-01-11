package com.milo.amz.review.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.amazonservices.mws.orders._2013_09_01.model.Address;
import com.amazonservices.mws.orders._2013_09_01.model.Order;
import com.amazonservices.mws.orders._2013_09_01.model.OrderItem;
import com.amazonservices.mws.orders._2013_09_01.model.PaymentExecutionDetailItem;
import com.milo.amz.review.service.dto.PaymentExecutionDTO;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;
import com.milo.amz.review.service.dto.PurchaseOrderItemDTO;
import com.milo.amz.review.service.dto.ShippingAddressDTO;



@Mapper(componentModel = "spring")

public interface OrderMapper {
    @Mapping(source="orderTotal.currencyCode",target="currencyCode")
    @Mapping(source="orderTotal.amount",target="orderTotal")
    public PurchaseOrderDTO orderDTOTOPurchaseOrder(Order order);
    
    public List<PurchaseOrderDTO> orderDTOTOPurchaseOrder(List<Order> orderList);


    public ShippingAddressDTO orderToShippingAddressDTO(Address address);
    
    @Mapping(source="payment.currencyCode",target="currencyCode")
    @Mapping(source="payment.amount",target="paymentAmount")
    public PaymentExecutionDTO orderToShippingAddressDTO(PaymentExecutionDetailItem paymentExecutionDetailItem);
    
    public List<PaymentExecutionDTO> orderToShippingAddressDTO(List<PaymentExecutionDetailItem> paymentExecutionDetailItem);
    
    @Mappings
    ({
        @Mapping(source = "itemPrice.amount", target = "itemPrice"),
        @Mapping(source = "shippingPrice.amount", target = "shippingPrice"),
        @Mapping(source = "giftWrapPrice.amount", target = "giftWrapPrice"),
        @Mapping(source = "itemTax.amount", target = "itemTax"),
        @Mapping(source = "shippingTax.amount", target = "shippingTax"),
        @Mapping(source = "giftWrapTax.amount", target = "giftWrapTax"),
        @Mapping(source = "shippingDiscount.amount", target = "shippingDiscount"),
        @Mapping(source = "promotionDiscount.amount", target = "promotionDiscount"),
        })

    public  PurchaseOrderItemDTO orderItemTOPurchaseOrderItem(OrderItem orderItem);
    public  List<PurchaseOrderItemDTO> orderItemTOPurchaseOrderItem(List<OrderItem> orderItemList);
}
