package com.example.AccessDatabase.service;

import com.example.AccessDatabase.entity.Category;
import com.example.AccessDatabase.repository.CategoryRepository;
import com.example.AccessDatabase.repository.MovieRepository;
import com.example.AccessDatabase.util.CategoryFactory;
import com.example.AccessDatabase.util.EntityFactoryFromString;
import com.example.AccessDatabase.util.MovieFactory;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HandleCSVService {
    MovieRepository movieRepository;
    CategoryRepository categoryRepository;

    Map<String, JpaRepository<?, ?>> repositories;
    Map<String, EntityFactoryFromString<?>> entityFactories;

    @Autowired
    public HandleCSVService(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.repositories = Map.of("Movie", movieRepository, "Category", categoryRepository);
        this.entityFactories = Map.of("Movie", new MovieFactory(), "Category", new CategoryFactory());
    }



    public String exportData(String tableName) {
        JpaRepository<?, ?> repository = repositories.get(tableName);

        List<?> list = repository.findAll();
        if (list.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        Class<?> clazz = list.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            sb.append(field.getName()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("\n");

        for (Object object : list) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    sb.append(value).append(", ");

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Transactional
    public Integer importData(String tableName, MultipartFile file) throws IOException, NoSuchFieldException, IllegalAccessException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        JpaRepository repository = repositories.get(tableName);
        EntityFactoryFromString<?> entityFactory = entityFactories.get(tableName);

        String headerLine = reader.readLine();
        if (headerLine == null || headerLine.isEmpty()) return 0;

        String line;
        int countRowAffect = 0;

        while (((line = reader.readLine()) != null)) {
            String[] data = line.split(", ");

            Object obj = entityFactory.fromStringArray(data, headerLine.split(", "));

            Class<?> clazz = obj.getClass();
            System.out.println("Class of object: " + obj.getClass());
            repository.save(obj);
            countRowAffect++;
       }
        return countRowAffect;
    }
}
