package com.milo.amz.review.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "asin")
    private String asin;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseOrderItem> canBeOrderItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsin() {
        return asin;
    }

    public Product asin(String asin) {
        this.asin = asin;
        return this;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public Product title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Product reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Product addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
        return this;
    }

    public Product removeReview(Review review) {
        reviews.remove(review);
        review.setProduct(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<PurchaseOrderItem> getCanBeOrderItems() {
        return canBeOrderItems;
    }

    public Product canBeOrderItems(Set<PurchaseOrderItem> purchaseOrderItems) {
        this.canBeOrderItems = purchaseOrderItems;
        return this;
    }

    public Product addCanBeOrderItem(PurchaseOrderItem purchaseOrderItem) {
        canBeOrderItems.add(purchaseOrderItem);
        purchaseOrderItem.setProduct(this);
        return this;
    }

    public Product removeCanBeOrderItem(PurchaseOrderItem purchaseOrderItem) {
        canBeOrderItems.remove(purchaseOrderItem);
        purchaseOrderItem.setProduct(null);
        return this;
    }

    public void setCanBeOrderItems(Set<PurchaseOrderItem> purchaseOrderItems) {
        this.canBeOrderItems = purchaseOrderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", asin='" + asin + "'" +
            ", title='" + title + "'" +
            '}';
    }
}
