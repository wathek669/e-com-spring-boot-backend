package com.example.wbcorps.ecom.controllers.customer;

import com.example.wbcorps.ecom.dto.ProductDto;
import com.example.wbcorps.ecom.services.jwt.customer.product.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = customerProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name ){
        List<ProductDto> productDtos = customerProductService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }
}
