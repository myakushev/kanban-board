package com.myakushev.api.generators;

import com.myakushev.api.enums.Color;
import com.myakushev.api.enums.TaskStatus;
import com.myakushev.api.models.Kanban;
import com.myakushev.api.models.KanbanDTO;
import com.myakushev.api.models.Task;
import com.myakushev.api.models.Tasks;

import java.util.Arrays;

public class TestDataGenerator {
    public static TestData generate() {
        var task = Task.builder()
                .id(RandomData.uniqueId())
                .description("Task Description: " + RandomData.getRandomString())
                .color(RandomData.getRandomEnum(Color.class))
                .status(RandomData.getRandomEnum(TaskStatus.class))
                .build();

        var kanbanDTO = KanbanDTO.builder()
                .title("Kanban Board title: " + RandomData.getRandomString())
                .build();

        var kanban = Kanban.builder()
                .id(RandomData.uniqueId())
                .title("Kanban Board title: " + RandomData.getRandomString())
                .tasks(Tasks.builder()
                        .task(Arrays.asList(task))
                        .build())
                .build();

        return TestData.builder()
                .kanbanDTO(kanbanDTO)
                .kanban(kanban)
                .task(task)
                .build();
    }
}
