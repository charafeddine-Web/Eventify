package com.sec.eventify.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public String profile(HttpServletRequest req) {
        return "Authenticated as: " + req.getUserPrincipal().getName();
    }
}
