package com.ambersgw.restful0201.dao;

import com.ambersgw.restful0201.constant.ProductCategory;
import com.ambersgw.restful0201.dto.ProductQueryParams;
import com.ambersgw.restful0201.dto.ProductRequest;
import com.ambersgw.restful0201.model.Product;

import java.util.List;

public interface ProductDao {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    void updateStock(Integer productId, Integer stock);

}
