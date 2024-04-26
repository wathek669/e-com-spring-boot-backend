package com.example.wbcorps.ecom.services.jwt.admin;

import com.example.wbcorps.ecom.dto.CategoryDto;
import com.example.wbcorps.ecom.entity.Category;
import com.example.wbcorps.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category= new Category() ;
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category)
    }
}
