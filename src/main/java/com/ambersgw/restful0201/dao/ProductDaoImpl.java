package com.ambersgw.restful0201.dao;

import com.ambersgw.restful0201.constant.ProductCategory;
import com.ambersgw.restful0201.dto.ProductQueryParams;
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
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();

        //查詢條件
        if(productQueryParams.getCategory() != null){
            //" AND"前面一定要預留一個空白，才不會將sql語句黏在一起
            sql = sql + " AND category = :category";

            //將ENUM類型轉換成字串再丟進map(.name())
            map.put("category",productQueryParams.getCategory().name());
        }

        if(productQueryParams.getSearch() != null){
            sql = sql + " AND product_name LIKE :search";
            //"%"表示任意字符(詳見sql語句)
            //"%"要寫在map值裡，不能寫在sql語句裡，這是JDBC使用上限制
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        //傳回的值：第三個為轉換成integer的值
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id,product_name, category, image_url, price," +
                " stock, description, created_date, last_modified_date " +
                "FROM product " +
                "WHERE 1=1";
            //"WHERE 1=1"不會對sql直接造成影響，但可以順利將以下sql(以後可能會有很多個)拼接起來
            //如果是spring data JPA會自己處理好多查詢條件的組合問題
        Map<String, Object> map = new HashMap<>();

        //查詢條件
        if(productQueryParams.getCategory() != null){
            //" AND"前面一定要預留一個空白，才不會將sql語句黏在一起
            sql = sql + " AND category = :category";

            //將ENUM類型轉換成字串再丟進map(.name())
            map.put("category",productQueryParams.getCategory().name());
        }

        if(productQueryParams.getSearch() != null){
            sql = sql + " AND product_name LIKE :search";
            //"%"表示任意字符(詳見sql語句)
            //"%"要寫在map值裡，不能寫在sql語句裡，這是JDBC使用上限制
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        //排序
        //在where後面拼接下列語法，實作orderby只能用這樣拼接方式實作(應該是JDBCTemplate限制)
        //沒有判斷參數是否為null，是因為在comtroller有預設值
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();


        //分頁
        //limit & offset語句排在order by後面
        sql = sql + " LIMIT :limit OFFSET :offset ";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        List<Product> productList =namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

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

    @Override
    public void deleteProductById(Integer productId) {

        String sql ="DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map =new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
