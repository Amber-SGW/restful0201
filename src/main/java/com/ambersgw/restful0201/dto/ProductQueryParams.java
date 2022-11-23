package com.ambersgw.restful0201.dto;

import com.ambersgw.restful0201.constant.ProductCategory;

public class ProductQueryParams {
    //將查詢條件列為一class
    //增加維護上的方便
    private ProductCategory category;
    private String search;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
