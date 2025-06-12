package com.example.aiyunshandemo.service;

import org.springframework.stereotype.Service;

@Service
public class McpService {

    public String callMcpService(String input) {
        return "MCP processed: " + input + " (Simulated)";
    }
}