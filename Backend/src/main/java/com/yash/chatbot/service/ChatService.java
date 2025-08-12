package com.yash.chatbot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatService {

    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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"; // Replace with your OpenRouter API Key

    public String getAIResponse(String userInput) {
        RestTemplate restTemplate = new RestTemplate();

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);
        headers.set("HTTP-Referer", "https://yourdomain.com"); // optional but recommended

        // Payload (Request Body)
        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "mistralai/mistral-7b-instruct"); // or any supported model
        payload.put("messages", List.of(
                Map.of("role", "user", "content", userInput)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(OPENROUTER_API_URL, entity, Map.class);

            // Extracting response
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            } else {
                return "No response from AI.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}











//import org.springframework.stereotype.Service;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class ChatService {
//
//    private static final Map<String, String> FAQ_MAP = new HashMap<>();
//
//    static {
//        FAQ_MAP.put("what is your name", "I am yash personal chat bot.");
//        FAQ_MAP.put("how can i contact support", "You can contact support at support@example.com.");
//        FAQ_MAP.put("what courses are available", "We offer Java, Python, Web Dev, and more.");
//        FAQ_MAP.put("hi", "Hello wassup");
//        FAQ_MAP.put("good mrng", "have a good day");
//    }
//
//    public String getResponse(String question) {
//        if (question == null) return "Please ask something.";
//
//        String lowerCaseQuestion = question.trim().toLowerCase();
//        return FAQ_MAP.getOrDefault(lowerCaseQuestion, "Sorry, I don't understand that yet.");
//    }
//}

