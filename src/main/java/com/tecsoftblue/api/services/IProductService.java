package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.CategoryRequest;
import com.tecsoftblue.api.dto.CategoryResponse;
import com.tecsoftblue.api.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    Page<ProductDTO> findAllPaged(PageRequest pageRequest);

    ProductDTO findById(Long id);

    ProductDTO insert(ProductDTO request);

    ProductDTO update(Long id, CategoryRequest request);

    void delete(Long id);
}