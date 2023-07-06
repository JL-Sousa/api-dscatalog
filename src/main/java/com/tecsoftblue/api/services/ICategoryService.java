package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.CategoryResponse;
import com.tecsoftblue.api.dto.CreateCategoryRequest;

import java.util.List;

public interface ICategoryService {

    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    CategoryResponse insert(CreateCategoryRequest request);

}
