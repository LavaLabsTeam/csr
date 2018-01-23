package com.lavalabs.csr.web.rest.vm;

import com.lavalabs.csr.domain.Category;
import com.lavalabs.csr.domain.Merchant;
import com.lavalabs.csr.domain.MerchantPackage;

import java.util.List;

public class SearchResponseVM {

    private List<Merchant> merchants;
    private List<Category> categories;
    private List<MerchantPackage> merchantPackages;

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<MerchantPackage> getMerchantPackages() {
        return merchantPackages;
    }

    public void setMerchantPackages(List<MerchantPackage> merchantPackages) {
        this.merchantPackages = merchantPackages;
    }
}
