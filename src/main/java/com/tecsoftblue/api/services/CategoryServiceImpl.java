package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.CategoryResponse;
import com.tecsoftblue.api.dto.CategoryRequest;
import com.tecsoftblue.api.entities.Category;
import com.tecsoftblue.api.repositories.CategoryRepository;
import com.tecsoftblue.api.services.exceptions.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(category -> new CategoryResponse( category.getId(),category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        var result = categoryRepository.findById(id);
        Category entity = result.orElseThrow(() -> new ControllerNotFoundException("Entity not found!"));
        return new CategoryResponse(entity.getId(), entity.getName());
    }

    @Override
    @Transactional
    public CategoryResponse insert(CategoryRequest request) {
        Category entity = new Category();
        entity.setName(request.name());
        entity = categoryRepository.save(entity);
        return new CategoryResponse(entity.getId(), entity.getName());
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        try {
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(request.name());
            entity = categoryRepository.save(entity);
            return new CategoryResponse(entity.getId(), entity.getName());
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Id not found " + id);
        }
    }
}
