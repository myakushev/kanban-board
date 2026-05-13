package com.wkrzywiec.medium.kanban.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Task")
public class TaskDTO {

    @Schema(description = "Title of the task", example = "Implement login feature", required = true)
    private String title;

    @Schema(description = "Detailed description of the task", example = "Add OAuth2 authentication")
    private String description;

    @Schema(description = "Color code for the task card", example = "#FF5733")
    private String color;

    @Schema(description = "Current status of the task", example = "TODO")
    private TaskStatus status;

    @Schema(description = "Flag indicating if this is a system task", example = "false")
    private boolean systemTask;

    @Schema(description = "External system identifier if task is promoted to system task", example = "ext_12345")
    private String externalId;
}