package com.tecsoftblue.api.controllers;

import com.tecsoftblue.api.dto.CategoryResponse;
import com.tecsoftblue.api.dto.CreateCategoryRequest;
import com.tecsoftblue.api.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private ICategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        var result = service.findAll();
        return  ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        var result = service.findById(id);
        return  ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> insert(@RequestBody CreateCategoryRequest request) {
        var result = service.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.id()).toUri();
        return ResponseEntity.created(uri).body(result);
    }
}
