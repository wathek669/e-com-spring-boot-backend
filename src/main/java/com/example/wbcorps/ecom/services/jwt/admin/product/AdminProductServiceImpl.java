package com.example.wbcorps.ecom.services.jwt.admin.product;

import com.example.wbcorps.ecom.dto.ProductDto;
import com.example.wbcorps.ecom.entity.Category;
import com.example.wbcorps.ecom.entity.Product;
import com.example.wbcorps.ecom.repository.CategoryRepository;
import com.example.wbcorps.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{
    private final ProductRepository productRepository ;
    private final CategoryRepository categoryRepository ;

    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow() ;
        product.setCategory(category);
        return productRepository.save(product).getDto();

    }
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll() ;
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByName(String name ){
        List<Product> products = productRepository.findAllByNameContaining(name) ;
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id){
        Optional<Product> optionalProduct =productRepository.findById(id) ;
        if (optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false ;
    }

}

