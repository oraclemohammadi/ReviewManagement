package com.milo.amz.review.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Review entity.
 */
public class ReviewDTO implements Serializable {

    private Long id;

    private String itemID;

    private String reviewID;

    private String customerName;

    private String customerID;

    private String title;

    private Long rating;

    private Long fullRating;

    private Long helpfulVotes;

    private Long totalVotes;

    private Boolean verifiedPurchase;

    private String realName;

    private LocalDate reviewLocalDate;

    private String content;

    private String specificNote;


    private Long productId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
    public Long getFullRating() {
        return fullRating;
    }

    public void setFullRating(Long fullRating) {
        this.fullRating = fullRating;
    }
    public Long getHelpfulVotes() {
        return helpfulVotes;
    }

    public void setHelpfulVotes(Long helpfulVotes) {
        this.helpfulVotes = helpfulVotes;
    }
    public Long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }
    public Boolean getVerifiedPurchase() {
        return verifiedPurchase;
    }

    public void setVerifiedPurchase(Boolean verifiedPurchase) {
        this.verifiedPurchase = verifiedPurchase;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    public LocalDate getReviewLocalDate() {
        return reviewLocalDate;
    }

    public void setReviewLocalDate(LocalDate reviewLocalDate) {
        this.reviewLocalDate = reviewLocalDate;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getSpecificNote() {
        return specificNote;
    }

    public void setSpecificNote(String specificNote) {
        this.specificNote = specificNote;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewDTO reviewDTO = (ReviewDTO) o;

        if ( ! Objects.equals(id, reviewDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
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
            ", reviewLocalDate='" + reviewLocalDate + "'" +
            ", content='" + content + "'" +
            ", specificNote='" + specificNote + "'" +
            '}';
    }
}
