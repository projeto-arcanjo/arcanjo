package br.com.cmabreu.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/classes/create")
    @SendTo("/classes/echo")
    public String echo(@Payload String payload ) {
    	System.out.println("Echo: " + payload );
        return payload;
    }	

}
