package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.PurchaseOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PurchaseOrder and its DTO PurchaseOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseOrderMapper {

    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "payment.id", target = "paymentId")
    PurchaseOrderDTO purchaseOrderToPurchaseOrderDTO(PurchaseOrder purchaseOrder);

    List<PurchaseOrderDTO> purchaseOrdersToPurchaseOrderDTOs(List<PurchaseOrder> purchaseOrders);

    @Mapping(source = "invoiceId", target = "invoice")
    @Mapping(source = "paymentId", target = "payment")
    @Mapping(target = "shippedTos", ignore = true)
    @Mapping(target = "contains", ignore = true)
    PurchaseOrder purchaseOrderDTOToPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);

    List<PurchaseOrder> purchaseOrderDTOsToPurchaseOrders(List<PurchaseOrderDTO> purchaseOrderDTOs);

    default InvoiceData invoiceDataFromId(Long id) {
        if (id == null) {
            return null;
        }
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setId(id);
        return invoiceData;
    }

    default PaymentExecution paymentExecutionFromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setId(id);
        return paymentExecution;
    }
}
