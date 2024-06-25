package com.duy.spring.demo.authorization.api;

import com.duy.spring.demo.authorization.config.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @PostMapping
    public Map<String, String> login(@RequestBody Map<String, String> data) {
        String userName = data.get("username");
        String roles = data.get("roles");

        // authenticate userName against Database
        // Here, just mock and generate JWT Token
        return Map.of("access_token", jwtTokenGenerator.generate(userName, roles));
    }
}
