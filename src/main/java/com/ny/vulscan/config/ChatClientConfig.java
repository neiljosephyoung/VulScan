package com.ny.vulscan.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient ollama3Client(OllamaChatModel ollamaChatModel) {
        return ChatClient.create(ollamaChatModel);
    }
}
