package com.travelblog.controller;

import com.travelblog.dto.*;
import com.travelblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Result<?> list(@PathVariable Long articleId,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer size) {
        return commentService.list(articleId, page, size);
    }

    @PostMapping
    public Result<?> create(@PathVariable Long articleId, @RequestBody CommentCreateRequest request) {
        return commentService.create(articleId, request);
    }
}
