package com.myakushev.api.generators;

import com.myakushev.api.models.kanban.KanbanRequest;
import com.myakushev.api.models.tasks.TaskRequest;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private KanbanRequest kanbanRequest;
    private TaskRequest taskRequest;
}
