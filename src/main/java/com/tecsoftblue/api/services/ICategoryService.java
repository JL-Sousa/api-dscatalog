package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.CategoryResponse;
import com.tecsoftblue.api.dto.CategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICategoryService {

    Page<CategoryResponse> findAllPaged(Pageable pageable);

    CategoryResponse findById(Long id);

    CategoryResponse insert(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);
}
