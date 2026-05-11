package com.myakushev.api.generators;

import com.myakushev.api.models.kanban.Kanban;
import com.myakushev.api.models.kanban.KanbanRequest;
import com.myakushev.api.models.tasks.Task;
import com.myakushev.api.models.tasks.TaskRequest;
import com.myakushev.api.models.tasks.TaskStatus;

import java.util.List;

public class TestDataGenerator {

    private TestDataGenerator() {}

    public static KanbanRequest validKanbanRequest() {
        return KanbanRequest.builder()
                .title("Kanban" + RandomData.getString())
                .build();
    }

    public static KanbanRequest validKanbanRequest(String title) {
        return KanbanRequest.builder()
                .title(title)
                .build();
    }

    public static Kanban validKanban() {
        return Kanban.builder()
                .title("Kanban" + RandomData.getString())
                .description("This is Kanban test")
                .tasks(List.of(Task.builder().title("").build()))
                .build();
    }

    public static Kanban validKanban(String title, String description, List<Task> tasks) {
        return Kanban.builder()
                .id(RandomData.getRandomID())
                .title(title)
                .description(description)
                .tasks(tasks)
                .build();
    }

    public static TestData generate() {
        var kanbanRequest = KanbanRequest.builder()
                .title("Kanban" + RandomData.getString())
                .build();

        var taskRequest = TaskRequest.builder()
                .title("Task of " + kanbanRequest.getTitle() + " " + RandomData.getString())
                .description("Description of the Task of " + kanbanRequest.getTitle() + " " + RandomData.getString())
                .color("BLUE")
                .status(TaskStatus.DONE)
                .build();

        return TestData.builder()
                .kanbanRequest(kanbanRequest)
                .taskRequest(taskRequest)
                .build();
    }
}
