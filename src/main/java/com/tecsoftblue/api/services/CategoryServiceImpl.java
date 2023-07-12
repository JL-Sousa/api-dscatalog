package com.tecsoftblue.api.services;

import com.tecsoftblue.api.dto.CategoryResponse;
import com.tecsoftblue.api.dto.CategoryRequest;
import com.tecsoftblue.api.entities.Category;
import com.tecsoftblue.api.repositories.CategoryRepository;
import com.tecsoftblue.api.services.exceptions.ControllerNotFoundException;
import com.tecsoftblue.api.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAllPaged(PageRequest pageRequest) {
        Page<Category> list = categoryRepository.findAll(pageRequest);
        return list.map(category -> new CategoryResponse( category.getId(),category.getName()));
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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new ControllerNotFoundException("Recurso nao encrontrado!");
        }
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de intregridade referencial!");
        }

    }
}
