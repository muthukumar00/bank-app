package com.example.bank;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("demo")
public class DemoController {

    @GetMapping("/demo")
    public Map<String, String> demo() {

        return Map.of(
            "application", "Bank Application",
            "environment", "AWS Demo",
            "status", "Running",
            "message", "Spring Boot application is running successfully"
        );
    }
}