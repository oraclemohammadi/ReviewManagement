package com.milo.amz.review.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the MarketPlace entity.
 */
public class MarketPlaceDTO implements Serializable {

    private Long id;

    private String accessKey;

    private String secrectKey;

    private String sellerId;

    private String marketPlaceId;

    private String serviceUrl;

    private boolean activated;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
    public String getSecrectKey() {
        return secrectKey;
    }

    public void setSecrectKey(String secrectKey) {
        this.secrectKey = secrectKey;
    }
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }
    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketPlaceDTO marketPlaceDTO = (MarketPlaceDTO) o;

        if ( ! Objects.equals(id, marketPlaceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MarketPlaceDTO{" +
            "id=" + id +
            ", accessKey='" + accessKey + "'" +
            ", secrectKey='" + secrectKey + "'" +
            ", sellerId='" + sellerId + "'" +
            ", marketPlaceId='" + marketPlaceId + "'" +
            ", serviceUrl='" + serviceUrl + "'" +
            ", activated='" + activated + "'" +
            '}';
    }
}
