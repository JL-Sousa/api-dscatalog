package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.CategoryDTO;
import com.tecsoftblue.api.dto.ProductDTO;
import com.tecsoftblue.api.entities.Category;
import com.tecsoftblue.api.entities.Product;
import com.tecsoftblue.api.repositories.CategoryRepository;
import com.tecsoftblue.api.repositories.ProductRepository;
import com.tecsoftblue.api.services.exceptions.ControllerNotFoundException;
import com.tecsoftblue.api.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> list = productRepository.findAll(pageable);
        return list.map(ProductDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        var result = productRepository.findById(id);
        Product entity = result.orElseThrow(() -> new ControllerNotFoundException("Entity not found!"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Override
    @Transactional
    public ProductDTO insert(ProductDTO request) {
        Product entity = new Product();
        copyDtoToEntity(request, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO request) {
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(request, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Id not found " + id);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ControllerNotFoundException("Recurso nao encrontrado!");
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de intregridade referencial!");
        }

    }

    private void copyDtoToEntity(ProductDTO request, Product entity) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setDate(request.getDate());
        entity.setImgUrl(request.getImgUrl());
        entity.setPrice(request.getPrice());

        entity.getCategories().clear();
        for(CategoryDTO catDto: request.getCategories()) {
            Category category = categoryRepository.getReferenceById(catDto.getId());
            entity.getCategories().add(category);
        }

    }
}
