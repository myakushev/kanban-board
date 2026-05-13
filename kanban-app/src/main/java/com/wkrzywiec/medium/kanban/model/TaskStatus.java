package com.wkrzywiec.medium.kanban.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of the task")
public enum TaskStatus {
    TODO, INPROGRESS, DONE
}