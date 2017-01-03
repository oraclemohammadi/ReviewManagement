package com.milo.amz.review.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the InvoiceData entity.
 */
public class InvoiceDataDTO implements Serializable {

    private Long id;

    private String invoiceRequirement;

    private String buyerSelectedInvoiceCategory;

    private String invoiceTitle;

    private String invoiceInformation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getInvoiceRequirement() {
        return invoiceRequirement;
    }

    public void setInvoiceRequirement(String invoiceRequirement) {
        this.invoiceRequirement = invoiceRequirement;
    }
    public String getBuyerSelectedInvoiceCategory() {
        return buyerSelectedInvoiceCategory;
    }

    public void setBuyerSelectedInvoiceCategory(String buyerSelectedInvoiceCategory) {
        this.buyerSelectedInvoiceCategory = buyerSelectedInvoiceCategory;
    }
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }
    public String getInvoiceInformation() {
        return invoiceInformation;
    }

    public void setInvoiceInformation(String invoiceInformation) {
        this.invoiceInformation = invoiceInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoiceDataDTO invoiceDataDTO = (InvoiceDataDTO) o;

        if ( ! Objects.equals(id, invoiceDataDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvoiceDataDTO{" +
            "id=" + id +
            ", invoiceRequirement='" + invoiceRequirement + "'" +
            ", buyerSelectedInvoiceCategory='" + buyerSelectedInvoiceCategory + "'" +
            ", invoiceTitle='" + invoiceTitle + "'" +
            ", invoiceInformation='" + invoiceInformation + "'" +
            '}';
    }
}
