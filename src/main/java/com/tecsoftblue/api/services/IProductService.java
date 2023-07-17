package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Page<ProductDTO> findAllPaged(Pageable pageable);

    ProductDTO findById(Long id);

    ProductDTO insert(ProductDTO request);

    ProductDTO update(Long id, ProductDTO request);

    void delete(Long id);
}
