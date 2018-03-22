package com.lavalabs.csr.service;

import com.lavalabs.csr.domain.Category;
import com.lavalabs.csr.domain.Merchant;
import com.lavalabs.csr.domain.MerchantPackage;
import com.lavalabs.csr.repository.CategoryRepository;
import com.lavalabs.csr.repository.MerchantPackageRepository;
import com.lavalabs.csr.repository.MerchantRepository;
import com.lavalabs.csr.web.rest.vm.SearchResponseVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for Search.
 */
@Service
@Transactional
public class SearchService {

    private final Logger log = LoggerFactory.getLogger(SearchService.class);

    private final MerchantRepository merchantRepository;

    private final CategoryRepository categoryRepository;

    private final MerchantPackageRepository merchantPackageRepository;

    public SearchService(MerchantRepository merchantRepository, CategoryRepository categoryRepository, MerchantPackageRepository merchantPackageRepository) {
        this.merchantRepository = merchantRepository;
        this.categoryRepository = categoryRepository;
        this.merchantPackageRepository = merchantPackageRepository;
    }


    public SearchResponseVM search(Long categoryId, String name, String location){
        SearchResponseVM result = new SearchResponseVM();

        if(categoryId != null){

            if(StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(location)){
                // Get all the Merchants
                String lowerCaseName = StringUtils.isNotEmpty(name) ? name.toLowerCase() : "";
                String lowerCaseLocation = StringUtils.isNotEmpty(location) ? location.toLowerCase() : "";

                List<Merchant> merchants = merchantRepository.searchMerchant(lowerCaseName, lowerCaseLocation);
                if(!CollectionUtils.isEmpty(merchants)){
                    result.setMerchants(merchants);
                }
            }
        }

        return result;
    }
}
