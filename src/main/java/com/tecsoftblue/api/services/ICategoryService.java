package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.CategoryResponse;
import com.tecsoftblue.api.dto.CategoryRequest;

import java.util.List;

public interface ICategoryService {

    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    CategoryResponse insert(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);
}
