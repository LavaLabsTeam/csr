package com.lavalabs.csr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MerchantPackage.
 */
@Entity
@Table(name = "merchant_package")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MerchantPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "package_includes")
    private String packageIncludes;

    @Lob
    @Column(name = "terms_and_condition")
    private String termsAndCondition;

    @Column(name = "price")
    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @ManyToOne
    private Merchant merchant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MerchantPackage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MerchantPackage description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageIncludes() {
        return packageIncludes;
    }

    public MerchantPackage packageIncludes(String packageIncludes) {
        this.packageIncludes = packageIncludes;
        return this;
    }

    public void setPackageIncludes(String packageIncludes) {
        this.packageIncludes = packageIncludes;
    }

    public String getTermsAndCondition() {
        return termsAndCondition;
    }

    public MerchantPackage termsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
        return this;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public Double getPrice() {
        return price;
    }

    public MerchantPackage price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public MerchantPackage oldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public MerchantPackage startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public MerchantPackage endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public MerchantPackage photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public MerchantPackage photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public MerchantPackage merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MerchantPackage merchantPackage = (MerchantPackage) o;
        if (merchantPackage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), merchantPackage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MerchantPackage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", packageIncludes='" + getPackageIncludes() + "'" +
            ", termsAndCondition='" + getTermsAndCondition() + "'" +
            ", price=" + getPrice() +
            ", oldPrice=" + getOldPrice() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
