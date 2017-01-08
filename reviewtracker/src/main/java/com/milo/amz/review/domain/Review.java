package com.milo.amz.review.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "item_id")
    private String itemID;

    @Column(name = "review_id")
    private String reviewID;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_id")
    private String customerID;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "full_rating")
    private Long fullRating;

    @Column(name = "helpful_votes")
    private Long helpfulVotes;

    @Column(name = "total_votes")
    private Long totalVotes;

    @Column(name = "verified_purchase")
    private Boolean verifiedPurchase;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Column(name = "content")
    private String content;

    @Column(name = "specific_note")
    private String specificNote;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemID() {
        return itemID;
    }

    public Review itemID(String itemID) {
        this.itemID = itemID;
        return this;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getReviewID() {
        return reviewID;
    }

    public Review reviewID(String reviewID) {
        this.reviewID = reviewID;
        return this;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Review customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public Review customerID(String customerID) {
        this.customerID = customerID;
        return this;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getTitle() {
        return title;
    }

    public Review title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRating() {
        return rating;
    }

    public Review rating(Long rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getFullRating() {
        return fullRating;
    }

    public Review fullRating(Long fullRating) {
        this.fullRating = fullRating;
        return this;
    }

    public void setFullRating(Long fullRating) {
        this.fullRating = fullRating;
    }

    public Long getHelpfulVotes() {
        return helpfulVotes;
    }

    public Review helpfulVotes(Long helpfulVotes) {
        this.helpfulVotes = helpfulVotes;
        return this;
    }

    public void setHelpfulVotes(Long helpfulVotes) {
        this.helpfulVotes = helpfulVotes;
    }

    public Long getTotalVotes() {
        return totalVotes;
    }

    public Review totalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
        return this;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Boolean isVerifiedPurchase() {
        return verifiedPurchase;
    }

    public Review verifiedPurchase(Boolean verifiedPurchase) {
        this.verifiedPurchase = verifiedPurchase;
        return this;
    }

    public void setVerifiedPurchase(Boolean verifiedPurchase) {
        this.verifiedPurchase = verifiedPurchase;
    }

    public String getRealName() {
        return realName;
    }

    public Review realName(String realName) {
        this.realName = realName;
        return this;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public Review reviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getContent() {
        return content;
    }

    public Review content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecificNote() {
        return specificNote;
    }

    public Review specificNote(String specificNote) {
        this.specificNote = specificNote;
        return this;
    }

    public void setSpecificNote(String specificNote) {
        this.specificNote = specificNote;
    }

    public Product getProduct() {
        return product;
    }

    public Review product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        if (review.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + id +
            ", itemID='" + itemID + "'" +
            ", reviewID='" + reviewID + "'" +
            ", customerName='" + customerName + "'" +
            ", customerID='" + customerID + "'" +
            ", title='" + title + "'" +
            ", rating='" + rating + "'" +
            ", fullRating='" + fullRating + "'" +
            ", helpfulVotes='" + helpfulVotes + "'" +
            ", totalVotes='" + totalVotes + "'" +
            ", verifiedPurchase='" + verifiedPurchase + "'" +
            ", realName='" + realName + "'" +
            ", reviewDate='" + reviewDate + "'" +
            ", content='" + content + "'" +
            ", specificNote='" + specificNote + "'" +
            '}';
    }
}
