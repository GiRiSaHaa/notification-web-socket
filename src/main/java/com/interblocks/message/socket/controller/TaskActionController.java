package com.interblocks.message.socket.controller;

import com.interblocks.message.socket.model.PendingTask;
import com.interblocks.message.socket.service.TaskActionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskActionController {

    private final TaskActionService actionService;
    Map<String, List<String>> userRoleMap = new HashMap<>();

    public TaskActionController(TaskActionService actionService) {
        this.actionService = actionService;

        userRoleMap.put("Role1", Arrays.asList("111", "222"));
        userRoleMap.put("Role2", Arrays.asList("111", "222", "333"));
        userRoleMap.put("Role3", Arrays.asList("333"));
    }

    @PostMapping("/push-action")
//    @SendTo("/tasks/pending-actions")
    @CrossOrigin(origins = {"*"})
    public void task(@RequestBody PendingTask task) {
        actionService.pushingAction(task);
    }


}
