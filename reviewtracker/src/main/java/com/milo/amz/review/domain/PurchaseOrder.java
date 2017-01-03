package com.milo.amz.review.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.milo.amz.review.domain.enumeration.PaymentMethodEnum;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "seller_order_id")
    private String sellerOrderId;

    @Column(name = "purchase_local_date")
    private LocalDate purchaseLocalDate;

    @Column(name = "last_update_local_date")
    private LocalDate lastUpdateLocalDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "fulfillment_channel")
    private String fulfillmentChannel;

    @Column(name = "order_channel")
    private String orderChannel;

    @Column(name = "ship_service_level")
    private String shipServiceLevel;

    @Column(name = "order_total")
    private Long orderTotal;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "number_of_items_shipped")
    private Long numberOfItemsShipped;

    @Column(name = "number_of_items_unshipped")
    private Long numberOfItemsUnshipped;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethodEnum paymentMethod;

    @Column(name = "marketplace_id")
    private String marketplaceId;

    @Column(name = "buyer_email")
    private String buyerEmail;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "shipment_service_level_category")
    private String shipmentServiceLevelCategory;

    @Column(name = "shipped_by_amazon_tfm")
    private Boolean shippedByAmazonTFM;

    @Column(name = "tfm_shipment_status")
    private String tfmShipmentStatus;

    @Column(name = "cba_displayable_shipping_label")
    private String cbaDisplayableShippingLabel;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "earliest_ship_local_date")
    private LocalDate earliestShipLocalDate;

    @Column(name = "latest_ship_local_date")
    private LocalDate latestShipLocalDate;

    @OneToOne
    @JoinColumn(unique = true)
    private InvoiceData invoice;

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentExecution payment;

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShippingAddress> shippedTos = new HashSet<>();

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseOrderItem> contains = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSellerOrderId() {
        return sellerOrderId;
    }

    public PurchaseOrder sellerOrderId(String sellerOrderId) {
        this.sellerOrderId = sellerOrderId;
        return this;
    }

    public void setSellerOrderId(String sellerOrderId) {
        this.sellerOrderId = sellerOrderId;
    }

    public LocalDate getPurchaseLocalDate() {
        return purchaseLocalDate;
    }

    public PurchaseOrder purchaseLocalDate(LocalDate purchaseLocalDate) {
        this.purchaseLocalDate = purchaseLocalDate;
        return this;
    }

    public void setPurchaseLocalDate(LocalDate purchaseLocalDate) {
        this.purchaseLocalDate = purchaseLocalDate;
    }

    public LocalDate getLastUpdateLocalDate() {
        return lastUpdateLocalDate;
    }

    public PurchaseOrder lastUpdateLocalDate(LocalDate lastUpdateLocalDate) {
        this.lastUpdateLocalDate = lastUpdateLocalDate;
        return this;
    }

    public void setLastUpdateLocalDate(LocalDate lastUpdateLocalDate) {
        this.lastUpdateLocalDate = lastUpdateLocalDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public PurchaseOrder orderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFulfillmentChannel() {
        return fulfillmentChannel;
    }

    public PurchaseOrder fulfillmentChannel(String fulfillmentChannel) {
        this.fulfillmentChannel = fulfillmentChannel;
        return this;
    }

    public void setFulfillmentChannel(String fulfillmentChannel) {
        this.fulfillmentChannel = fulfillmentChannel;
    }

    public String getOrderChannel() {
        return orderChannel;
    }

    public PurchaseOrder orderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
        return this;
    }

    public void setOrderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
    }

    public String getShipServiceLevel() {
        return shipServiceLevel;
    }

    public PurchaseOrder shipServiceLevel(String shipServiceLevel) {
        this.shipServiceLevel = shipServiceLevel;
        return this;
    }

    public void setShipServiceLevel(String shipServiceLevel) {
        this.shipServiceLevel = shipServiceLevel;
    }

    public Long getOrderTotal() {
        return orderTotal;
    }

    public PurchaseOrder orderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
        return this;
    }

    public void setOrderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public PurchaseOrder currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getAmount() {
        return amount;
    }

    public PurchaseOrder amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getNumberOfItemsShipped() {
        return numberOfItemsShipped;
    }

    public PurchaseOrder numberOfItemsShipped(Long numberOfItemsShipped) {
        this.numberOfItemsShipped = numberOfItemsShipped;
        return this;
    }

    public void setNumberOfItemsShipped(Long numberOfItemsShipped) {
        this.numberOfItemsShipped = numberOfItemsShipped;
    }

    public Long getNumberOfItemsUnshipped() {
        return numberOfItemsUnshipped;
    }

    public PurchaseOrder numberOfItemsUnshipped(Long numberOfItemsUnshipped) {
        this.numberOfItemsUnshipped = numberOfItemsUnshipped;
        return this;
    }

    public void setNumberOfItemsUnshipped(Long numberOfItemsUnshipped) {
        this.numberOfItemsUnshipped = numberOfItemsUnshipped;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public PurchaseOrder paymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public PurchaseOrder marketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
        return this;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public PurchaseOrder buyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
        return this;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public PurchaseOrder buyerName(String buyerName) {
        this.buyerName = buyerName;
        return this;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getShipmentServiceLevelCategory() {
        return shipmentServiceLevelCategory;
    }

    public PurchaseOrder shipmentServiceLevelCategory(String shipmentServiceLevelCategory) {
        this.shipmentServiceLevelCategory = shipmentServiceLevelCategory;
        return this;
    }

    public void setShipmentServiceLevelCategory(String shipmentServiceLevelCategory) {
        this.shipmentServiceLevelCategory = shipmentServiceLevelCategory;
    }

    public Boolean isShippedByAmazonTFM() {
        return shippedByAmazonTFM;
    }

    public PurchaseOrder shippedByAmazonTFM(Boolean shippedByAmazonTFM) {
        this.shippedByAmazonTFM = shippedByAmazonTFM;
        return this;
    }

    public void setShippedByAmazonTFM(Boolean shippedByAmazonTFM) {
        this.shippedByAmazonTFM = shippedByAmazonTFM;
    }

    public String getTfmShipmentStatus() {
        return tfmShipmentStatus;
    }

    public PurchaseOrder tfmShipmentStatus(String tfmShipmentStatus) {
        this.tfmShipmentStatus = tfmShipmentStatus;
        return this;
    }

    public void setTfmShipmentStatus(String tfmShipmentStatus) {
        this.tfmShipmentStatus = tfmShipmentStatus;
    }

    public String getCbaDisplayableShippingLabel() {
        return cbaDisplayableShippingLabel;
    }

    public PurchaseOrder cbaDisplayableShippingLabel(String cbaDisplayableShippingLabel) {
        this.cbaDisplayableShippingLabel = cbaDisplayableShippingLabel;
        return this;
    }

    public void setCbaDisplayableShippingLabel(String cbaDisplayableShippingLabel) {
        this.cbaDisplayableShippingLabel = cbaDisplayableShippingLabel;
    }

    public String getOrderType() {
        return orderType;
    }

    public PurchaseOrder orderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public LocalDate getEarliestShipLocalDate() {
        return earliestShipLocalDate;
    }

    public PurchaseOrder earliestShipLocalDate(LocalDate earliestShipLocalDate) {
        this.earliestShipLocalDate = earliestShipLocalDate;
        return this;
    }

    public void setEarliestShipLocalDate(LocalDate earliestShipLocalDate) {
        this.earliestShipLocalDate = earliestShipLocalDate;
    }

    public LocalDate getLatestShipLocalDate() {
        return latestShipLocalDate;
    }

    public PurchaseOrder latestShipLocalDate(LocalDate latestShipLocalDate) {
        this.latestShipLocalDate = latestShipLocalDate;
        return this;
    }

    public void setLatestShipLocalDate(LocalDate latestShipLocalDate) {
        this.latestShipLocalDate = latestShipLocalDate;
    }

    public InvoiceData getInvoice() {
        return invoice;
    }

    public PurchaseOrder invoice(InvoiceData invoiceData) {
        this.invoice = invoiceData;
        return this;
    }

    public void setInvoice(InvoiceData invoiceData) {
        this.invoice = invoiceData;
    }

    public PaymentExecution getPayment() {
        return payment;
    }

    public PurchaseOrder payment(PaymentExecution paymentExecution) {
        this.payment = paymentExecution;
        return this;
    }

    public void setPayment(PaymentExecution paymentExecution) {
        this.payment = paymentExecution;
    }

    public Set<ShippingAddress> getShippedTos() {
        return shippedTos;
    }

    public PurchaseOrder shippedTos(Set<ShippingAddress> shippingAddresses) {
        this.shippedTos = shippingAddresses;
        return this;
    }

    public PurchaseOrder addShippedTo(ShippingAddress shippingAddress) {
        shippedTos.add(shippingAddress);
        shippingAddress.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removeShippedTo(ShippingAddress shippingAddress) {
        shippedTos.remove(shippingAddress);
        shippingAddress.setPurchaseOrder(null);
        return this;
    }

    public void setShippedTos(Set<ShippingAddress> shippingAddresses) {
        this.shippedTos = shippingAddresses;
    }

    public Set<PurchaseOrderItem> getContains() {
        return contains;
    }

    public PurchaseOrder contains(Set<PurchaseOrderItem> purchaseOrderItems) {
        this.contains = purchaseOrderItems;
        return this;
    }

    public PurchaseOrder addContains(PurchaseOrderItem purchaseOrderItem) {
        contains.add(purchaseOrderItem);
        purchaseOrderItem.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removeContains(PurchaseOrderItem purchaseOrderItem) {
        contains.remove(purchaseOrderItem);
        purchaseOrderItem.setPurchaseOrder(null);
        return this;
    }

    public void setContains(Set<PurchaseOrderItem> purchaseOrderItems) {
        this.contains = purchaseOrderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        if (purchaseOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, purchaseOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
            "id=" + id +
            ", sellerOrderId='" + sellerOrderId + "'" +
            ", purchaseLocalDate='" + purchaseLocalDate + "'" +
            ", lastUpdateLocalDate='" + lastUpdateLocalDate + "'" +
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
            ", buyerEmail='" + buyerEmail + "'" +
            ", buyerName='" + buyerName + "'" +
            ", shipmentServiceLevelCategory='" + shipmentServiceLevelCategory + "'" +
            ", shippedByAmazonTFM='" + shippedByAmazonTFM + "'" +
            ", tfmShipmentStatus='" + tfmShipmentStatus + "'" +
            ", cbaDisplayableShippingLabel='" + cbaDisplayableShippingLabel + "'" +
            ", orderType='" + orderType + "'" +
            ", earliestShipLocalDate='" + earliestShipLocalDate + "'" +
            ", latestShipLocalDate='" + latestShipLocalDate + "'" +
            '}';
    }
}
