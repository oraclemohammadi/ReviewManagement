package com.milo.amz.review.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the PurchaseOrderItem entity.
 */
public class PurchaseOrderItemDTO implements Serializable {

    private Long id;

    private String asin;

    private String sellerSKU;

    private String orderItemId;

    private String title;

    private Long quantityOrdered;

    private Long quantityShipped;

    private float itemPrice;

    private float shippingPrice;

    private float giftWrapPrice;

    private float itemTax;

    private float shippingTax;

    private float giftWrapTax;

    private float shippingDiscount;

    private float promotionDiscount;

    private float codFee;

    private float codFeeDiscount;

    private String giftMessageText;

    private String giftWrapLevel;

    private String conditionNote;

    private String conditionId;

    private String conditionSubtypeId;

    private String scheduledDeliveryStartDate;

    private String scheduledDeliveryEndDate;


    private Long productId;
    
    private Long purchaseOrderId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getASIN() {
        return asin;
    }

    public void setASIN(String asin) {
        this.asin = asin;
    }
    public String getSellerSKU() {
        return sellerSKU;
    }

    public void setSellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
    }
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Long getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Long quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }
    public Long getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(Long quantityShipped) {
        this.quantityShipped = quantityShipped;
    }
   

    public void setShippingPrice(Long shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
   

    public void setGiftWrapPrice(Long giftWrapPrice) {
        this.giftWrapPrice = giftWrapPrice;
    }
    

    public void setItemTax(Long itemTax) {
        this.itemTax = itemTax;
    }
    
    public void setShippingTax(Long shippingTax) {
        this.shippingTax = shippingTax;
    }
   

    public void setGiftWrapTax(Long giftWrapTax) {
        this.giftWrapTax = giftWrapTax;
    }
    

    public void setShippingDiscount(Long shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }
    

    public void setPromotionDiscount(Long promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }
    

    public void setCodFee(Long codFee) {
        this.codFee = codFee;
    }
    

    public void setCodFeeDiscount(Long codFeeDiscount) {
        this.codFeeDiscount = codFeeDiscount;
    }
    public String getGiftMessageText() {
        return giftMessageText;
    }

    public void setGiftMessageText(String giftMessageText) {
        this.giftMessageText = giftMessageText;
    }
    public String getGiftWrapLevel() {
        return giftWrapLevel;
    }

    public void setGiftWrapLevel(String giftWrapLevel) {
        this.giftWrapLevel = giftWrapLevel;
    }
    public String getConditionNote() {
        return conditionNote;
    }

    public void setConditionNote(String conditionNote) {
        this.conditionNote = conditionNote;
    }
    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }
    public String getConditionSubtypeId() {
        return conditionSubtypeId;
    }

    public void setConditionSubtypeId(String conditionSubtypeId) {
        this.conditionSubtypeId = conditionSubtypeId;
    }
    public String getScheduledDeliveryStartDate() {
        return scheduledDeliveryStartDate;
    }

    public void setScheduledDeliveryStartDate(String scheduledDeliveryStartDate) {
        this.scheduledDeliveryStartDate = scheduledDeliveryStartDate;
    }
    public String getScheduledDeliveryEndDate() {
        return scheduledDeliveryEndDate;
    }

    public void setScheduledDeliveryEndDate(String scheduledDeliveryEndDate) {
        this.scheduledDeliveryEndDate = scheduledDeliveryEndDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public float getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public float getGiftWrapPrice() {
		return giftWrapPrice;
	}

	public void setGiftWrapPrice(float giftWrapPrice) {
		this.giftWrapPrice = giftWrapPrice;
	}

	public float getItemTax() {
		return itemTax;
	}

	public void setItemTax(float itemTax) {
		this.itemTax = itemTax;
	}

	public float getShippingTax() {
		return shippingTax;
	}

	public void setShippingTax(float shippingTax) {
		this.shippingTax = shippingTax;
	}

	public float getGiftWrapTax() {
		return giftWrapTax;
	}

	public void setGiftWrapTax(float giftWrapTax) {
		this.giftWrapTax = giftWrapTax;
	}

	public float getShippingDiscount() {
		return shippingDiscount;
	}

	public void setShippingDiscount(float shippingDiscount) {
		this.shippingDiscount = shippingDiscount;
	}

	public float getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(float promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public float getCodFee() {
		return codFee;
	}

	public void setCodFee(float codFee) {
		this.codFee = codFee;
	}

	public float getCodFeeDiscount() {
		return codFeeDiscount;
	}

	public void setCodFeeDiscount(float codFeeDiscount) {
		this.codFeeDiscount = codFeeDiscount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseOrderItemDTO purchaseOrderItemDTO = (PurchaseOrderItemDTO) o;

        if ( ! Objects.equals(id, purchaseOrderItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseOrderItemDTO{" +
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
            ", scheduledDeliveryStartDate='" + scheduledDeliveryStartDate + "'" +
            ", scheduledDeliveryEndDate='" + scheduledDeliveryEndDate + "'" +
            '}';
    }
}
