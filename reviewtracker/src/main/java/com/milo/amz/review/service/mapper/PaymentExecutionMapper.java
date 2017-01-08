package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.PaymentExecutionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PaymentExecution and its DTO PaymentExecutionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentExecutionMapper {

    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    PaymentExecutionDTO paymentExecutionToPaymentExecutionDTO(PaymentExecution paymentExecution);

    List<PaymentExecutionDTO> paymentExecutionsToPaymentExecutionDTOs(List<PaymentExecution> paymentExecutions);

    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PaymentExecution paymentExecutionDTOToPaymentExecution(PaymentExecutionDTO paymentExecutionDTO);

    List<PaymentExecution> paymentExecutionDTOsToPaymentExecutions(List<PaymentExecutionDTO> paymentExecutionDTOs);

    default PurchaseOrder purchaseOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        return purchaseOrder;
    }
}
