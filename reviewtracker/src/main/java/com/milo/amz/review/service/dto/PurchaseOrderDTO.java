package com.milo.amz.review.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.milo.amz.review.domain.enumeration.PaymentMethodEnum;

/**
 * A DTO for the PurchaseOrder entity.
 */
public class PurchaseOrderDTO implements Serializable {

    private Long id;

    private String sellerOrderId;

    private LocalDate purchaseDate;

    private LocalDate lastUpdateDate;

    private String orderStatus;

    private String fulfillmentChannel;

    private String orderChannel;

    private String shipServiceLevel;

    private String orderTotal;

    private String currencyCode;

    private Long amount;

    private Long numberOfItemsShipped;

    private Long numberOfItemsUnshipped;

    private PaymentMethodEnum paymentMethod;

    private String marketplaceId;

    private String buyerId;

    private String buyerEmail;

    private String buyerName;

    private String shipmentServiceLevelCategory;

    private Boolean shippedByAmazonTFM;

    private String tfmShipmentStatus;

    private String cbaDisplayableShippingLabel;

    private String orderType;

    private LocalDate earliestShipDate;

    private LocalDate latestShipDate;


    private Long invoiceId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSellerOrderId() {
        return sellerOrderId;
    }

    public void setSellerOrderId(String sellerOrderId) {
        this.sellerOrderId = sellerOrderId;
    }
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getFulfillmentChannel() {
        return fulfillmentChannel;
    }

    public void setFulfillmentChannel(String fulfillmentChannel) {
        this.fulfillmentChannel = fulfillmentChannel;
    }
    public String getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
    }
    public String getShipServiceLevel() {
        return shipServiceLevel;
    }

    public void setShipServiceLevel(String shipServiceLevel) {
        this.shipServiceLevel = shipServiceLevel;
    }
    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Long getNumberOfItemsShipped() {
        return numberOfItemsShipped;
    }

    public void setNumberOfItemsShipped(Long numberOfItemsShipped) {
        this.numberOfItemsShipped = numberOfItemsShipped;
    }
    public Long getNumberOfItemsUnshipped() {
        return numberOfItemsUnshipped;
    }

    public void setNumberOfItemsUnshipped(Long numberOfItemsUnshipped) {
        this.numberOfItemsUnshipped = numberOfItemsUnshipped;
    }
    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }
    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
    public String getShipmentServiceLevelCategory() {
        return shipmentServiceLevelCategory;
    }

    public void setShipmentServiceLevelCategory(String shipmentServiceLevelCategory) {
        this.shipmentServiceLevelCategory = shipmentServiceLevelCategory;
    }
    public Boolean getShippedByAmazonTFM() {
        return shippedByAmazonTFM;
    }

    public void setShippedByAmazonTFM(Boolean shippedByAmazonTFM) {
        this.shippedByAmazonTFM = shippedByAmazonTFM;
    }
    public String getTfmShipmentStatus() {
        return tfmShipmentStatus;
    }

    public void setTfmShipmentStatus(String tfmShipmentStatus) {
        this.tfmShipmentStatus = tfmShipmentStatus;
    }
    public String getCbaDisplayableShippingLabel() {
        return cbaDisplayableShippingLabel;
    }

    public void setCbaDisplayableShippingLabel(String cbaDisplayableShippingLabel) {
        this.cbaDisplayableShippingLabel = cbaDisplayableShippingLabel;
    }
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public LocalDate getEarliestShipDate() {
        return earliestShipDate;
    }

    public void setEarliestShipDate(LocalDate earliestShipDate) {
        this.earliestShipDate = earliestShipDate;
    }
    public LocalDate getLatestShipDate() {
        return latestShipDate;
    }

    public void setLatestShipDate(LocalDate latestShipDate) {
        this.latestShipDate = latestShipDate;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceDataId) {
        this.invoiceId = invoiceDataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseOrderDTO purchaseOrderDTO = (PurchaseOrderDTO) o;

        if ( ! Objects.equals(id, purchaseOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
            "id=" + id +
            ", sellerOrderId='" + sellerOrderId + "'" +
            ", purchaseDate='" + purchaseDate + "'" +
            ", lastUpdateDate='" + lastUpdateDate + "'" +
            ", orderStatus='" + orderStatus + "'" +
            ", fulfillmentChannel='" + fulfillmentChannel + "'" +
            ", orderChannel='" + orderChannel + "'" +
            ", shipServiceLevel='" + shipServiceLevel + "'" +
            ", orderTotal='" + orderTotal + "'" +
            ", currencyCode='" + currencyCode + "'" +
            ", amount='" + amount + "'" +
            ", numberOfItemsShipped='" + numberOfItemsShipped + "'" +
            ", numberOfItemsUnshipped='" + numberOfItemsUnshipped + "'" +
            ", paymentMethod='" + paymentMethod + "'" +
            ", marketplaceId='" + marketplaceId + "'" +
            ", buyerId='" + buyerId + "'" +
            ", buyerEmail='" + buyerEmail + "'" +
            ", buyerName='" + buyerName + "'" +
            ", shipmentServiceLevelCategory='" + shipmentServiceLevelCategory + "'" +
            ", shippedByAmazonTFM='" + shippedByAmazonTFM + "'" +
            ", tfmShipmentStatus='" + tfmShipmentStatus + "'" +
            ", cbaDisplayableShippingLabel='" + cbaDisplayableShippingLabel + "'" +
            ", orderType='" + orderType + "'" +
            ", earliestShipDate='" + earliestShipDate + "'" +
            ", latestShipDate='" + latestShipDate + "'" +
            '}';
    }
}
