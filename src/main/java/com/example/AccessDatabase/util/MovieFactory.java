package com.example.AccessDatabase.util;

import com.example.AccessDatabase.entity.Movie;

import java.lang.reflect.Field;

public class MovieFactory implements EntityFactoryFromString<Movie> {
    public Movie fromStringArray(String[] values, String[] headers) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Movie.class;
        if (values.length != clazz.getDeclaredFields().length) throw new IllegalArgumentException("Số trường dữ liệu không khớp: " + values.length + " " + clazz.getDeclaredFields().length);

        Movie movie = new Movie();
        for (int i = 0; i < values.length; i++) {
            Field field = clazz.getDeclaredField(headers[i].trim());
            if (headers[i].trim().equals("id")) {
                movie.setId(Integer.parseInt(values[i].trim()));
            } else if (headers[i].trim().equals("year")) {
                movie.setYear(Integer.parseInt(values[i].trim()));
            } else {
                field.setAccessible(true);
                field.set(movie, values[i].trim());
            }

        }
        return movie;
    }
}
