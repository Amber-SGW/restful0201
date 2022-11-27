package com.ambersgw.restful0201.controller;


import com.ambersgw.restful0201.constant.ProductCategory;
import com.ambersgw.restful0201.dto.ProductQueryParams;
import com.ambersgw.restful0201.dto.ProductRequest;
import com.ambersgw.restful0201.model.Product;
import com.ambersgw.restful0201.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

//Validated使Max以及Min生效(4-13)
@Validated
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    //查詢商品列表一定要用"products"，s很重要
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //查詢條件 filtering
            //"required = false"讓分類為可選，非必選(4-7)
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序 sorting
            //目前設計為單排序(只能有一種種類排序)，若要多排序再查一下(未來優化)
            //預設排列種類/順序為最新時間創建對商品做排序(4-7)
            @RequestParam(defaultValue = "created_date") String orderBy,
            //預設降序對商品做排序(大到小)
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 pagination
            //對應sql語句的limit以及offset
            //可以保護後端資料庫存取的效能
            //避免前端數據過於大量或是傳遞負數，以Max / Min去限制(4-13)
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)Integer offset
            ){
        //new一個查詢class
        ProductQueryParams productQueryParams = new ProductQueryParams();
        //獲取前端傳進來的參數
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);


        List<Product> productList = productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(productList);

    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //要讓@NotNull生效，這邊一定要加上@Valid，易忘!
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

        //商品新增成功後，用商品ID查詢返回數據
          Integer productId = productService.createProduct(productRequest);

          //從資料庫取得商品資訊
          Product product = productService.getProductById(productId);
          //將http狀態碼以及商品資訊(裝在body)回傳到前端
          return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }
    //可以由前端修改的值均為productRequest的變數，因此採用ProductRequest這個class
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        //先確認根據id查詢之商品是否存在於資料庫
        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //根據id指定商品後，修改商品參數
        productService.updateProduct(productId, productRequest);

        //修改商品完成後，藉由id將該商品從資料庫查詢出來
        Product updateProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);

        //回傳空的body表示商品已經被刪除,狀態碼為204no content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
