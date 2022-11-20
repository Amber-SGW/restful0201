package com.ambersgw.restful0201.dao;

import com.ambersgw.restful0201.dto.ProductRequest;
import com.ambersgw.restful0201.model.Product;
import com.ambersgw.restful0201.rowmapper.ProductRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name, category, image_url, price," +
                " stock, description, created_date, last_modified_date FROM product " +
                "WHERE product_id=:productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name,category, image_url, " +
                "price,stock,description,created_date, last_modified_date) " +
                "VALUES (:productName,:category, :imgUrl, :price, :stock, " +
                ":description, :createDate, :lastModifiedDate);";

        Map<String, Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imgUrl",productRequest.getImgUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        //記錄當下時間點，並將參數加到map裡
        Date now = new Date();
        map.put("createDate",now);
        map.put("lastModifiedDate",now);

        //儲存product ID
        KeyHolder keyHolder =new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map), keyHolder);


        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql ="UPDATE product SET product_name = :productName, " +
                "category = :category, image_url = :imgUrl, price = :price," +
                " stock = :stock, description = :description, " +
                "last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imgUrl",productRequest.getImgUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }
}
