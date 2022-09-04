package com.interblocks.message.socket.model;

import lombok.Data;

@Data
public class PendingTask {
    private String role_id;
    private String action;
}
