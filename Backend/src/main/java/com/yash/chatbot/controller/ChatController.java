package com.yash.chatbot.controller;


import com.yash.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody Map<String, String> payload) {
        String userInput = payload.get("input");
        String response = chatService.getAIResponse(userInput);
        return ResponseEntity.ok(response);
    }
}
