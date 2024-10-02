package com.example.AccessDatabase.util;

import com.example.AccessDatabase.entity.Movie;

public class MovieFactory implements EntityFactoryFromString<Movie> {
    public Movie fromStringArray(String[] values)  {
        Class<?> clazz = Movie.class;
        if (values.length != clazz.getDeclaredFields().length) throw new IllegalArgumentException("Số trường dữ liệu không khớp: " + values.length + " " + clazz.getDeclaredFields().length);

        Integer id = Integer.valueOf(values[0]);
        String title = values[1];
        String description = values[2];
        Integer year = Integer.valueOf(values[3]);

        return new Movie(id, title, description, year);
    }
}
