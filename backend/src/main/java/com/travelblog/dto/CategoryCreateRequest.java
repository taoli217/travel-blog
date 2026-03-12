package com.travelblog.dto;

import lombok.Data;

@Data
public class CategoryCreateRequest {
    private String name;
    private String description;
}
