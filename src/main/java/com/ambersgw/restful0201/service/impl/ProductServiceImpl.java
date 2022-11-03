package com.ambersgw.restful0201.service.impl;

import com.ambersgw.restful0201.dao.ProductDao;
import com.ambersgw.restful0201.service.impl.model.Product;
import com.ambersgw.restful0201.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
