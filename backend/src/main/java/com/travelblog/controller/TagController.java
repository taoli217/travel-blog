package com.travelblog.controller;

import com.travelblog.dto.*;
import com.travelblog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public Result<?> list() {
        return tagService.list();
    }
}
