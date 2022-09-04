package com.interblocks.message.socket.model;

import lombok.Data;

@Data
public class PendingTask {
    private String roleId;
    private String action;
}
