package com.yash.chatbot.service;

import com.yash.chatbot.config.OpenAiConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenAiService {

    private final OpenAiConfig openAiConfig;
    private final RestTemplate restTemplate;

    public OpenAiService(OpenAiConfig openAiConfig) {
        this.openAiConfig = openAiConfig;
        this.restTemplate = new RestTemplate();
    }

    public String getChatResponse(String userMessage) {

        String url = "https://openrouter.ai/api/v1/chat/completions";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer YOUR_OPENROUTER_API_KEY");
        headers.set("HTTP-Referer", "https://yourwebsite.com"); // optional but recommended


        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openAiConfig.getModel());

        List<Map<String, String>> messages = List.of(
                Map.of("role", "user", "content", userMessage)
        );

        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return message.get("content").toString().trim();
                }
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

        return "Sorry, I couldn't get a response from OpenAI.";
    }
}
