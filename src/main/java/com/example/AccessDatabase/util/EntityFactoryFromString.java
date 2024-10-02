package com.example.AccessDatabase.util;

public interface  EntityFactoryFromString<T> {
    T fromStringArray(String[] values, String[] headers) throws NoSuchFieldException, IllegalAccessException;
}
