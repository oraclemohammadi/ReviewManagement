package com.milo.amz.review.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the PaymentExecution entity.
 */
public class PaymentExecutionDTO implements Serializable {

    private Long id;

    private Long paymentAmount;

    private String currencyCode;

    private String paymentMethod;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentExecutionDTO paymentExecutionDTO = (PaymentExecutionDTO) o;

        if ( ! Objects.equals(id, paymentExecutionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PaymentExecutionDTO{" +
            "id=" + id +
            ", paymentAmount='" + paymentAmount + "'" +
            ", currencyCode='" + currencyCode + "'" +
            ", paymentMethod='" + paymentMethod + "'" +
            '}';
    }
}
