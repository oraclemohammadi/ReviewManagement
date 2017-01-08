package com.milo.amz.review.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MarketPlace.
 */
@Entity
@Table(name = "market_place")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MarketPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "access_key")
    private String accessKey;

    @Column(name = "secrect_key")
    private String secrectKey;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "market_place_id")
    private String marketPlaceId;

    @Column(name = "service_url")
    private String serviceUrl;

    @Column(name = "activated")
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

    public MarketPlace accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecrectKey() {
        return secrectKey;
    }

    public MarketPlace secrectKey(String secrectKey) {
        this.secrectKey = secrectKey;
        return this;
    }

    public void setSecrectKey(String secrectKey) {
        this.secrectKey = secrectKey;
    }

    public String getSellerId() {
        return sellerId;
    }

    public MarketPlace sellerId(String sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public MarketPlace marketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
        return this;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public MarketPlace serviceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        return this;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public MarketPlace activated(boolean activated) {
        this.activated = activated;
        return this;
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
        MarketPlace marketPlace = (MarketPlace) o;
        if (marketPlace.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, marketPlace.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MarketPlace{" +
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
