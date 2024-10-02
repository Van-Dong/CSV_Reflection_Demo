package com.example.AccessDatabase.util;

import com.example.AccessDatabase.entity.Category;

public class CategoryFactory implements EntityFactoryFromString<Category> {
    public Category fromStringArray(String[] values) {
        Class<?> clazz = Category.class;
        if (values.length != clazz.getDeclaredFields().length) throw new IllegalArgumentException("Số trường dữ liệu không khớp.");

        Integer id = Integer.valueOf(values[0]);
        String name = values[1];

        return new Category(id, name);
    }
}
