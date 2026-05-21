package com.myakushev.api;

import com.myakushev.api.models.kanban.Kanban;
import com.myakushev.api.models.tasks.Task;
import com.myakushev.api.requests.checked.CheckedKanban;
import com.myakushev.api.spec.Specifications;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Test(groups = "api")
public class CreateKanbanAndTasksTest extends BaseApiTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Basic first test to explore")
    @Owner("Mikhail Yakushev")
    public void createSimpleKanban() {
        var testData = Allure.step("Create test data", () -> testDataStorage.addTestData());

        var checkedKanbanRequester = Allure.step("Initialize API requester", () ->
            new CheckedKanban(Specifications.getSpec().noAuthSpec()));

        Kanban checkedKanban = Allure.step("Create kanban", () -> checkedKanbanRequester
                .create(testData.getKanbanRequest()));

        Kanban checkedKanbanUpdated = Allure.step("", () -> {
            checkedKanban.setTitle("New Kanban Title");
            return checkedKanbanRequester
                    .update(checkedKanban.getId(), checkedKanban);
        });

        Kanban tasksAddedToKanban = Allure.step("Add new task to kanban board", () -> checkedKanbanRequester
                .addNewTaskToKanban(checkedKanbanUpdated.getId(), testData.getTaskRequest()));

        log.info(tasksAddedToKanban.toString());

        assertThat(tasksAddedToKanban)
                .usingRecursiveComparison()
//                .ignoringFieldsMatchingRegexes(".*id")
                .ignoringFields("id", "tasks.id")
                .isEqualTo(Kanban.builder()
                        .title(checkedKanban.getTitle())
                        .description(null)
                        .tasks(List.of(Task.builder()
                                .title(testData.getTaskRequest().getTitle())
                                .description(testData.getTaskRequest().getDescription())
                                .color(testData.getTaskRequest().getColor())
                                .status(testData.getTaskRequest().getStatus())
                                .build()))
                        .build());


        List<Task> addedTasks = new ArrayList<>();

        addedTasks.add(Task.builder()
                .title(testData.getTaskRequest().getTitle())
                .description(testData.getTaskRequest().getDescription())
                .color(testData.getTaskRequest().getColor())
                .status(testData.getTaskRequest().getStatus())
                .build());

        for (int i = 0; i < 10; i++) {
            var task = testDataStorage.addTestData().getTaskRequest();
            tasksAddedToKanban = Allure.step("Add new task to kanban board. Step in loop - " + i, () -> checkedKanbanRequester
                    .addNewTaskToKanban(checkedKanbanUpdated.getId(), task));

            addedTasks.add(Task.builder()
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .color(task.getColor())
                    .status(task.getStatus())
                    .build());
        }

        assertThat(tasksAddedToKanban)
                .usingRecursiveComparison()
                .ignoringFields("id", "tasks.id")
                .ignoringCollectionOrder()
                .isEqualTo(Kanban.builder()
                        .title(checkedKanban.getTitle())
                        .description(null)
                        .tasks(addedTasks)
                        .build());

        System.out.println(tasksAddedToKanban);
    }




}
