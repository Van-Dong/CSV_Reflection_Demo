package com.example.AccessDatabase.controller;

import com.example.AccessDatabase.annotation.ValidNameTable;
import com.example.AccessDatabase.service.HandleCSVService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandleCSVController {
    HandleCSVService handleCSVService;

    @GetMapping
    public String handleCSV() {
        return "index";
    }

    @GetMapping("/export")
    public void exportToCSV(@ValidNameTable(acceptedValues = {"Movie", "Category"}) @RequestParam(defaultValue = "") String tableName, HttpServletResponse response) throws IOException {
        String result = handleCSVService.exportData(tableName);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + ".csv\"");
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            writer.write(result);
        }
    }

    @PostMapping("/import")
    public String importCSV(@ValidNameTable(acceptedValues = {"Movie", "Category"}) @RequestParam(defaultValue = "") String tableName, @RequestParam MultipartFile file, Model m) throws IOException, IllegalAccessException, NoSuchFieldException {
        // handle CSV
        int result = handleCSVService.importData(tableName, file);
        m.addAttribute("resultImport", result);
        return "index";
    }
}
