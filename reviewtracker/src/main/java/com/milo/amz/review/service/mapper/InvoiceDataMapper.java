package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.InvoiceDataDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity InvoiceData and its DTO InvoiceDataDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvoiceDataMapper {

    InvoiceDataDTO invoiceDataToInvoiceDataDTO(InvoiceData invoiceData);

    List<InvoiceDataDTO> invoiceDataToInvoiceDataDTOs(List<InvoiceData> invoiceData);

    InvoiceData invoiceDataDTOToInvoiceData(InvoiceDataDTO invoiceDataDTO);

    List<InvoiceData> invoiceDataDTOsToInvoiceData(List<InvoiceDataDTO> invoiceDataDTOs);
}
