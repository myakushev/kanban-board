package com.wkrzywiec.medium.kanban.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "task")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Schema(description = "Task entity representing a single task in a kanban board")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Title of the task", example = "Implement login feature", required = true)
    private String title;

    @Schema(description = "Detailed description of the task", example = "Add OAuth2 authentication")
    private String description;

    @Schema(description = "Color code for the task card", example = "#FF5733")
    private String color;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the task", example = "TODO")
    private TaskStatus status;

    @Column(name = "is_system_task")
    @Schema(description = "Flag indicating if this is a system task", example = "false")
    private boolean isSystemTask = false;

    @Column(name = "external_id")
    @Schema(description = "External system identifier if task is promoted to system task", example = "ext_12345")
    private String externalId;
}