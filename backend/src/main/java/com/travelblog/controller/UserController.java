package com.travelblog.controller;

import com.travelblog.dto.LoginRequest;
import com.travelblog.dto.RegisterRequest;
import com.travelblog.dto.Result;
import com.travelblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/{id}")
    public Result<?> getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }
}
