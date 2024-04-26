package com.example.wbcorps.ecom.services.jwt.admin;

import com.example.wbcorps.ecom.dto.CategoryDto;
import com.example.wbcorps.ecom.entity.Category;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto)
}
