package com.myakushev.api;

import com.myakushev.api.models.Kanban;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class KanbanTests extends BaseApiTest {

    @Test
    public void testGetKanbanByTitle() {
        Response kanban = uncheckedRequests.getKanbanRequest().getByTitle("444");
    }

    @Test
    public void testCreateNewKanban() {
        var testData1 = testDataStorage.addTestData();
        Kanban kanban = checkedRequests.getKanbanRequest().create(testData1.getKanbanDTO());

        softy.assertThat(kanban.getTitle()).isEqualTo(testData1.getKanbanDTO().getTitle());
    }

    @Test
    public void testNegativeCreateNewKanban() {
        var testData1 = testDataStorage.addTestData();
        testData1.getKanbanDTO().setTitle(null);
        Kanban kanban = checkedRequests.getKanbanRequest().create(testData1.getKanbanDTO());

        softy.assertThat(kanban.getTitle()).isEqualTo(testData1.getKanbanDTO().getTitle());
    }
}
