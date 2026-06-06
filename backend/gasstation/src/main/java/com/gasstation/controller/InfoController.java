package com.gasstation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        String instanceId = System.getenv().getOrDefault("HOSTNAME", "unknown");
        System.out.println("===> Request handled by instance: " + instanceId);
        Map<String, String> response = new HashMap<>();
        response.put("instanceId", instanceId);
        response.put("status", "OK");
        return ResponseEntity.ok(response);
    }
}
