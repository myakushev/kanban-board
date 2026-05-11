package com.myakushev.api.models.kanban;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myakushev.api.models.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Kanban {
    private Integer id;
    private String title;
    private String description;
    private List<Task> tasks;
}
