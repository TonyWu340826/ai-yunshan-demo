package com.example.aiyunshandemo.controller;

import com.example.aiyunshandemo.service.McpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mcp")
public class McpController {

    @Autowired
    private McpService mcpService;

    @PostMapping("/process")
    public String processInput(@RequestBody String input) {
        return mcpService.callMcpService(input);
    }
}