package com.example.wbcorps.ecom.services.jwt.admin.category;

import com.example.wbcorps.ecom.dto.CategoryDto;
import com.example.wbcorps.ecom.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategories();
}
