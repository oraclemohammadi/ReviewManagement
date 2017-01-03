package com.milo.amz.review.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "invoice_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvoiceData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "invoice_requirement")
    private String invoiceRequirement;

    @Column(name = "buyer_selected_invoice_category")
    private String buyerSelectedInvoiceCategory;

    @Column(name = "invoice_title")
    private String invoiceTitle;

    @Column(name = "invoice_information")
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

    public InvoiceData invoiceRequirement(String invoiceRequirement) {
        this.invoiceRequirement = invoiceRequirement;
        return this;
    }

    public void setInvoiceRequirement(String invoiceRequirement) {
        this.invoiceRequirement = invoiceRequirement;
    }

    public String getBuyerSelectedInvoiceCategory() {
        return buyerSelectedInvoiceCategory;
    }

    public InvoiceData buyerSelectedInvoiceCategory(String buyerSelectedInvoiceCategory) {
        this.buyerSelectedInvoiceCategory = buyerSelectedInvoiceCategory;
        return this;
    }

    public void setBuyerSelectedInvoiceCategory(String buyerSelectedInvoiceCategory) {
        this.buyerSelectedInvoiceCategory = buyerSelectedInvoiceCategory;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public InvoiceData invoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
        return this;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceInformation() {
        return invoiceInformation;
    }

    public InvoiceData invoiceInformation(String invoiceInformation) {
        this.invoiceInformation = invoiceInformation;
        return this;
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
        InvoiceData invoiceData = (InvoiceData) o;
        if (invoiceData.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, invoiceData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvoiceData{" +
            "id=" + id +
            ", invoiceRequirement='" + invoiceRequirement + "'" +
            ", buyerSelectedInvoiceCategory='" + buyerSelectedInvoiceCategory + "'" +
            ", invoiceTitle='" + invoiceTitle + "'" +
            ", invoiceInformation='" + invoiceInformation + "'" +
            '}';
    }
}
