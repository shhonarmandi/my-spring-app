package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    class HelloController {
        @GetMapping("/hello")
        public HelloResponse hello(@RequestParam(required = false) String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Required parameter 'name' is not present.");
            }

            return new HelloResponse("Hello, " + name + " \uD83D\uDC4B!");
        }
    }
}
