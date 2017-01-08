package com.milo.amz.review.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Promotions.
 */
@Entity
@Table(name = "promotions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Promotions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "promotion_id")
    private String promotionId;

    @ManyToOne
    private PurchaseOrderItem purchaseOrderItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public Promotions promotionId(String promotionId) {
        this.promotionId = promotionId;
        return this;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public PurchaseOrderItem getPurchaseOrderItem() {
        return purchaseOrderItem;
    }

    public Promotions purchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        this.purchaseOrderItem = purchaseOrderItem;
        return this;
    }

    public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        this.purchaseOrderItem = purchaseOrderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotions promotions = (Promotions) o;
        if (promotions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, promotions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Promotions{" +
            "id=" + id +
            ", promotionId='" + promotionId + "'" +
            '}';
    }
}
