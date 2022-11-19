package com.ambersgw.restful0201.controller;

import com.ambersgw.restful0201.dto.ProductRequest;
import com.ambersgw.restful0201.model.Product;
import com.ambersgw.restful0201.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

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
//    @PutMapping("/products/{productId}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId){
//
//    }
}
