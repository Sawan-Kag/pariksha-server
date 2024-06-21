package com.sawan.exam.serviceInterface;

import java.util.Optional;
import java.util.Set;

import com.sawan.exam.models.Category;

public interface CategoryService {
    public Category addCategory(Category category);

    public Category updateCategory(Category category);

    public Set<Category> getCategories();

    public Optional<Category> getCategory(String categoryId);

    public void deleteCategory(String categoryId);
}
