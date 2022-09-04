package com.interblocks.message.socket.controller;

import com.interblocks.message.socket.model.PendingTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Controller
public class TaskActionController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    Map<String, List<String>> userRoleMap = new HashMap<>();

    public TaskActionController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;

        userRoleMap.put("Role1", Arrays.asList("111", "222"));
        userRoleMap.put("Role2", Arrays.asList("111", "222", "333"));
        userRoleMap.put("Role3", Arrays.asList("333"));
    }

    @MessageMapping("/action")
//    @SendTo("/tasks/pending-actions")
    @CrossOrigin(origins = {"*"})
    public void task(@Payload PendingTask task) {
        task.setAction("TODO : " + task.getAction());
//        String user = sha.getUser().getName();
        for (String user: userRoleMap.get(task.getRole_id())) {
            simpMessagingTemplate.convertAndSendToUser(user, "/tasks/pending-actions", task);
        }
    }


}
