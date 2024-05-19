package com.example.wbcorps.ecom.entity;

import com.example.wbcorps.ecom.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private Long price ;
    @Lob
    private String description;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img ;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category ;

    public ProductDto getDto() {
        ProductDto productDto=new ProductDto() ;
        productDto.setId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setByteImg( img);
        productDto.setCategoryId(category.getId());
        productDto.setPrice(price);
        productDto.setCategoryName(category.getName());
        return productDto;
    }
}
