package com.ambersgw.restful0201.dao;

import com.ambersgw.restful0201.dto.ProductRequest;
import com.ambersgw.restful0201.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

}
