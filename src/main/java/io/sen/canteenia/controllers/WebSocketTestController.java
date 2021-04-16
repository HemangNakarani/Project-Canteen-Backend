package io.sen.canteenia.controllers;

import io.sen.canteenia.models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class WebSocketTestController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
//        System.out.print(chatMessage.getMessage());
        messagingTemplate.convertAndSendToUser(
                chatMessage.getUsername(),"/queue/messages",
                new ChatMessage(chatMessage.getUserid(), chatMessage.getUsername(), "Hello From Hemang Via Socket !!!"));
    }
}
