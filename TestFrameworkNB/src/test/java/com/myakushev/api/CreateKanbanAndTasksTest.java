package com.myakushev.api;

import com.myakushev.api.models.kanban.Kanban;
import com.myakushev.api.requests.checked.CheckedKanban;
import com.myakushev.api.requests.unchecked.UncheckedKanban;
import com.myakushev.api.spec.Specifications;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class CreateKanbanAndTasksTest extends BaseApiTest {

    @Test
    public void createSimpleKanban() {
        var testData = testDataStorage.addTestData();
        var checkedKanbanRequester = new CheckedKanban(Specifications.getSpec().noAuthSpec());
        var uncheckedKanbanRequester = new UncheckedKanban(Specifications.getSpec().noAuthSpec());

        Kanban checkedKanban = checkedKanbanRequester
                .create(testData.getKanbanRequest());

        checkedKanban.setTitle("New Kanban Title");
        Kanban checkedKanbanUpdated = checkedKanbanRequester
                .update(checkedKanban.getId(), checkedKanban);


        Kanban tasksAddedToKanban = checkedKanbanRequester
                .addNewTaskToKanban(checkedKanbanUpdated.getId(), testData.getTaskRequest());

        System.out.println(tasksAddedToKanban);


        String idRegex = "\\d+";
        assertThat(tasksAddedToKanban)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(Kanban.builder()
                        .title(tasksAddedToKanban.getTitle())
                        .description(tasksAddedToKanban.getDescription())
                        .tasks(tasksAddedToKanban.getTasks())
                        .build());
    }

}
