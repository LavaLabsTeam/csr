package com.lavalabs.csr.web.rest.vm;

import com.lavalabs.csr.domain.Category;
import com.lavalabs.csr.domain.Merchant;
import com.lavalabs.csr.domain.MerchantPackage;

import java.util.List;

public class SearchResponseVM {

    private List<Merchant> merchants;


    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }
}
