package com.travelblog.controller;

import com.travelblog.dto.*;
import com.travelblog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword) {
        return articleService.list(page, size, categoryId, keyword);
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable Long id) {
        return articleService.get(id);
    }

    @PostMapping
    public Result<?> create(@RequestBody ArticleCreateRequest request) {
        return articleService.create(request);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody ArticleCreateRequest request) {
        return articleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        return articleService.delete(id);
    }

    @PostMapping("/{id}/like")
    public Result<?> like(@PathVariable Long id) {
        return articleService.toggleLike(id);
    }
}
