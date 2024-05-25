package com.example.wbcorps.ecom.services.jwt.customer.product;

import com.example.wbcorps.ecom.dto.ProductDto;


import java.util.List;


public interface CustomerProductService {
    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByName(String name );
}
