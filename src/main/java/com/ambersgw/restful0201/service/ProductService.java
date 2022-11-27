package com.ambersgw.restful0201.service;


import com.ambersgw.restful0201.dto.ProductQueryParams;
import com.ambersgw.restful0201.dto.ProductRequest;
import com.ambersgw.restful0201.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    //沒有返回值，故用void
    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
