package com.travelblog.controller;

import com.travelblog.dto.*;
import com.travelblog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Result<?> list() {
        return categoryService.list();
    }

    @PostMapping
    public Result<?> create(@RequestBody CategoryCreateRequest request) {
        return categoryService.create(request);
    }
}
