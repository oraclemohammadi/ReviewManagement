package com.milo.amz.review.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PurchaseOrderItem.
 */
@Entity
@Table(name = "purchase_order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "asin")
    private String asin;

    @Column(name = "seller_sku")
    private String sellerSKU;

    @Column(name = "order_item_id")
    private String orderItemId;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity_ordered")
    private Long quantityOrdered;

    @Column(name = "quantity_shipped")
    private Long quantityShipped;

    @Column(name = "item_price")
    private Long itemPrice;

    @Column(name = "shipping_price")
    private Long shippingPrice;

    @Column(name = "gift_wrap_price")
    private Long giftWrapPrice;

    @Column(name = "item_tax")
    private Long itemTax;

    @Column(name = "shipping_tax")
    private Long shippingTax;

    @Column(name = "gift_wrap_tax")
    private Long giftWrapTax;

    @Column(name = "shipping_discount")
    private Long shippingDiscount;

    @Column(name = "promotion_discount")
    private Long promotionDiscount;

    @Column(name = "cod_fee")
    private Long codFee;

    @Column(name = "cod_fee_discount")
    private Long codFeeDiscount;

    @Column(name = "gift_message_text")
    private String giftMessageText;

    @Column(name = "gift_wrap_level")
    private String giftWrapLevel;

    @Column(name = "condition_note")
    private String conditionNote;

    @Column(name = "condition_id")
    private String conditionId;

    @Column(name = "condition_subtype_id")
    private String conditionSubtypeId;

    @Column(name = "scheduled_delivery_start_local_date")
    private String scheduledDeliveryStartLocalDate;

    @Column(name = "scheduled_delivery_end_local_date")
    private String scheduledDeliveryEndLocalDate;

    @ManyToOne
    private Product product;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsin() {
        return asin;
    }

    public PurchaseOrderItem asin(String asin) {
        this.asin = asin;
        return this;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getSellerSKU() {
        return sellerSKU;
    }

    public PurchaseOrderItem sellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
        return this;
    }

    public void setSellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public PurchaseOrderItem orderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
        return this;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getTitle() {
        return title;
    }

    public PurchaseOrderItem title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getQuantityOrdered() {
        return quantityOrdered;
    }

    public PurchaseOrderItem quantityOrdered(Long quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
        return this;
    }

    public void setQuantityOrdered(Long quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public Long getQuantityShipped() {
        return quantityShipped;
    }

    public PurchaseOrderItem quantityShipped(Long quantityShipped) {
        this.quantityShipped = quantityShipped;
        return this;
    }

    public void setQuantityShipped(Long quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public PurchaseOrderItem itemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getShippingPrice() {
        return shippingPrice;
    }

    public PurchaseOrderItem shippingPrice(Long shippingPrice) {
        this.shippingPrice = shippingPrice;
        return this;
    }

    public void setShippingPrice(Long shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Long getGiftWrapPrice() {
        return giftWrapPrice;
    }

    public PurchaseOrderItem giftWrapPrice(Long giftWrapPrice) {
        this.giftWrapPrice = giftWrapPrice;
        return this;
    }

    public void setGiftWrapPrice(Long giftWrapPrice) {
        this.giftWrapPrice = giftWrapPrice;
    }

    public Long getItemTax() {
        return itemTax;
    }

    public PurchaseOrderItem itemTax(Long itemTax) {
        this.itemTax = itemTax;
        return this;
    }

    public void setItemTax(Long itemTax) {
        this.itemTax = itemTax;
    }

    public Long getShippingTax() {
        return shippingTax;
    }

    public PurchaseOrderItem shippingTax(Long shippingTax) {
        this.shippingTax = shippingTax;
        return this;
    }

    public void setShippingTax(Long shippingTax) {
        this.shippingTax = shippingTax;
    }

    public Long getGiftWrapTax() {
        return giftWrapTax;
    }

    public PurchaseOrderItem giftWrapTax(Long giftWrapTax) {
        this.giftWrapTax = giftWrapTax;
        return this;
    }

    public void setGiftWrapTax(Long giftWrapTax) {
        this.giftWrapTax = giftWrapTax;
    }

    public Long getShippingDiscount() {
        return shippingDiscount;
    }

    public PurchaseOrderItem shippingDiscount(Long shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
        return this;
    }

    public void setShippingDiscount(Long shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public Long getPromotionDiscount() {
        return promotionDiscount;
    }

    public PurchaseOrderItem promotionDiscount(Long promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
        return this;
    }

    public void setPromotionDiscount(Long promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public Long getCodFee() {
        return codFee;
    }

    public PurchaseOrderItem codFee(Long codFee) {
        this.codFee = codFee;
        return this;
    }

    public void setCodFee(Long codFee) {
        this.codFee = codFee;
    }

    public Long getCodFeeDiscount() {
        return codFeeDiscount;
    }

    public PurchaseOrderItem codFeeDiscount(Long codFeeDiscount) {
        this.codFeeDiscount = codFeeDiscount;
        return this;
    }

    public void setCodFeeDiscount(Long codFeeDiscount) {
        this.codFeeDiscount = codFeeDiscount;
    }

    public String getGiftMessageText() {
        return giftMessageText;
    }

    public PurchaseOrderItem giftMessageText(String giftMessageText) {
        this.giftMessageText = giftMessageText;
        return this;
    }

    public void setGiftMessageText(String giftMessageText) {
        this.giftMessageText = giftMessageText;
    }

    public String getGiftWrapLevel() {
        return giftWrapLevel;
    }

    public PurchaseOrderItem giftWrapLevel(String giftWrapLevel) {
        this.giftWrapLevel = giftWrapLevel;
        return this;
    }

    public void setGiftWrapLevel(String giftWrapLevel) {
        this.giftWrapLevel = giftWrapLevel;
    }

    public String getConditionNote() {
        return conditionNote;
    }

    public PurchaseOrderItem conditionNote(String conditionNote) {
        this.conditionNote = conditionNote;
        return this;
    }

    public void setConditionNote(String conditionNote) {
        this.conditionNote = conditionNote;
    }

    public String getConditionId() {
        return conditionId;
    }

    public PurchaseOrderItem conditionId(String conditionId) {
        this.conditionId = conditionId;
        return this;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionSubtypeId() {
        return conditionSubtypeId;
    }

    public PurchaseOrderItem conditionSubtypeId(String conditionSubtypeId) {
        this.conditionSubtypeId = conditionSubtypeId;
        return this;
    }

    public void setConditionSubtypeId(String conditionSubtypeId) {
        this.conditionSubtypeId = conditionSubtypeId;
    }

    public String getScheduledDeliveryStartLocalDate() {
        return scheduledDeliveryStartLocalDate;
    }

    public PurchaseOrderItem scheduledDeliveryStartLocalDate(String scheduledDeliveryStartLocalDate) {
        this.scheduledDeliveryStartLocalDate = scheduledDeliveryStartLocalDate;
        return this;
    }

    public void setScheduledDeliveryStartLocalDate(String scheduledDeliveryStartLocalDate) {
        this.scheduledDeliveryStartLocalDate = scheduledDeliveryStartLocalDate;
    }

    public String getScheduledDeliveryEndLocalDate() {
        return scheduledDeliveryEndLocalDate;
    }

    public PurchaseOrderItem scheduledDeliveryEndLocalDate(String scheduledDeliveryEndLocalDate) {
        this.scheduledDeliveryEndLocalDate = scheduledDeliveryEndLocalDate;
        return this;
    }

    public void setScheduledDeliveryEndLocalDate(String scheduledDeliveryEndLocalDate) {
        this.scheduledDeliveryEndLocalDate = scheduledDeliveryEndLocalDate;
    }

    public Product getProduct() {
        return product;
    }

    public PurchaseOrderItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public PurchaseOrderItem purchaseOrder(PurchaseOrder purchaseOrder) {
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
        PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem) o;
        if (purchaseOrderItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, purchaseOrderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrderItem{" +
            "id=" + id +
            ", asin='" + asin + "'" +
            ", sellerSKU='" + sellerSKU + "'" +
            ", orderItemId='" + orderItemId + "'" +
            ", title='" + title + "'" +
            ", quantityOrdered='" + quantityOrdered + "'" +
            ", quantityShipped='" + quantityShipped + "'" +
            ", itemPrice='" + itemPrice + "'" +
            ", shippingPrice='" + shippingPrice + "'" +
            ", giftWrapPrice='" + giftWrapPrice + "'" +
            ", itemTax='" + itemTax + "'" +
            ", shippingTax='" + shippingTax + "'" +
            ", giftWrapTax='" + giftWrapTax + "'" +
            ", shippingDiscount='" + shippingDiscount + "'" +
            ", promotionDiscount='" + promotionDiscount + "'" +
            ", codFee='" + codFee + "'" +
            ", codFeeDiscount='" + codFeeDiscount + "'" +
            ", giftMessageText='" + giftMessageText + "'" +
            ", giftWrapLevel='" + giftWrapLevel + "'" +
            ", conditionNote='" + conditionNote + "'" +
            ", conditionId='" + conditionId + "'" +
            ", conditionSubtypeId='" + conditionSubtypeId + "'" +
            ", scheduledDeliveryStartLocalDate='" + scheduledDeliveryStartLocalDate + "'" +
            ", scheduledDeliveryEndLocalDate='" + scheduledDeliveryEndLocalDate + "'" +
            '}';
    }
}
