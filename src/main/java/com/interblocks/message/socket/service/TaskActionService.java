package com.interblocks.message.socket.service;

import com.interblocks.message.socket.model.PendingTask;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskActionService {
    private final SimpMessagingTemplate messagingTemplate;
    Map<String, List<String>> userRoleMap = new HashMap<>();

    public TaskActionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;

        userRoleMap.put("Role1", Arrays.asList("111", "222"));
        userRoleMap.put("Role2", Arrays.asList("111", "222", "333"));
        userRoleMap.put("Role3", Arrays.asList("333", "444"));
    }

    public void pushingAction(PendingTask task){
        task.setAction("TODO : " + task.getAction());
//        String user = sha.getUser().getName();
        try {
            for (String user: userRoleMap.get(task.getRoleId())) {
                messagingTemplate.convertAndSendToUser(user, "/tasks/pending-actions", task);
            }
        } catch (NullPointerException ex){
            System.out.println("Can not found Role ID");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
