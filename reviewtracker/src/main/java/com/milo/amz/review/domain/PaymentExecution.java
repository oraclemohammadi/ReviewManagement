package com.milo.amz.review.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Task entity.@author The JHipster team.
 */
@ApiModel(description = "Task entity.@author The JHipster team.")
@Entity
@Table(name = "payment_execution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentExecution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "payment_method")
    private String paymentMethod;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public PaymentExecution paymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public PaymentExecution currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentExecution paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public PaymentExecution purchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentExecution paymentExecution = (PaymentExecution) o;
        if (paymentExecution.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, paymentExecution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PaymentExecution{" +
            "id=" + id +
            ", paymentAmount='" + paymentAmount + "'" +
            ", currencyCode='" + currencyCode + "'" +
            ", paymentMethod='" + paymentMethod + "'" +
            '}';
    }
}
