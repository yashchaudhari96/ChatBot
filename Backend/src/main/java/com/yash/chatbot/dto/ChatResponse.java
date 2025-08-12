package com.yash.chatbot.dto;



public class ChatResponse {
    private String answer;

    public ChatResponse(String answer) {
        this.answer = answer;
    }

    // Getter
    public String getAnswer() {
        return answer;
    }
}
