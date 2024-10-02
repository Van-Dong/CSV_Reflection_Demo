package com.example.AccessDatabase.util;

import com.example.AccessDatabase.entity.Category;
import com.example.AccessDatabase.entity.Movie;

import java.lang.reflect.Field;

public class CategoryFactory implements EntityFactoryFromString<Category> {
    public Category fromStringArray(String[] values, String[] headers) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Category.class;
        if (values.length != clazz.getDeclaredFields().length) throw new IllegalArgumentException("Số trường dữ liệu không khớp.");

        Category c = new Category();
        for (int i = 0; i < values.length; i++) {
            Field field = clazz.getDeclaredField(headers[i]);
            field.setAccessible(true);

            if (headers[i].equals("id")) {
                c.setId(Integer.parseInt(values[i]));
            } else {
                field.set(c, values[i]);
            }

        }


        return c;
    }
}
