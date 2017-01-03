package com.milo.amz.review.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Promotions entity.
 */
public class PromotionsDTO implements Serializable {

    private Long id;

    private String promotionId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromotionsDTO promotionsDTO = (PromotionsDTO) o;

        if ( ! Objects.equals(id, promotionsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PromotionsDTO{" +
            "id=" + id +
            ", promotionId='" + promotionId + "'" +
            '}';
    }
}
