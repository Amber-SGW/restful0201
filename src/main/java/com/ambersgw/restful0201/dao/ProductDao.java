package com.ambersgw.restful0201.dao;

import com.ambersgw.restful0201.service.impl.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

}
