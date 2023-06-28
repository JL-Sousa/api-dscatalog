package com.tecsoftblue.api.services;

import com.tecsoftblue.api.entities.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAll();
}
