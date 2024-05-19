package com.example.wbcorps.ecom.controllers.admin;

import com.example.wbcorps.ecom.dto.CategoryDto;
import com.example.wbcorps.ecom.entity.Category;
import com.example.wbcorps.ecom.services.jwt.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("category")
    @ResponseBody

    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
        Category category= categoryService.createCategory(categoryDto);
        System.out.println("salemmmmmmmmmmmmmmmm");
        return  ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
