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

    PaymentExecutionDTO paymentExecutionToPaymentExecutionDTO(PaymentExecution paymentExecution);

    List<PaymentExecutionDTO> paymentExecutionsToPaymentExecutionDTOs(List<PaymentExecution> paymentExecutions);

    PaymentExecution paymentExecutionDTOToPaymentExecution(PaymentExecutionDTO paymentExecutionDTO);

    List<PaymentExecution> paymentExecutionDTOsToPaymentExecutions(List<PaymentExecutionDTO> paymentExecutionDTOs);
}
