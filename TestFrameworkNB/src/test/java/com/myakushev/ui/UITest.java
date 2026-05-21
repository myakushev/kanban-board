package com.myakushev.ui;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "ui")
public class UITest extends BaseUITest {

    @DataProvider
    public Object[][] tasksToTest() {
        return new Object[][] {
                { "Kanban board First title", "First Task", "BLUE", "Task First description" },
                { "Kanban board Second title", "First Task", "PINK", "Task Second description" },
                { "Kanban board Third title", "First Task", "YELLOW", "Task Third description" }
        };
    }


    @Test(dataProvider = "tasksToTest")
    public void createKanban(String kanbanTitle, String taskName, String taskColor, String taskDescription) {
        var homePage = new HomePage();
        homePage.open().openCreateKanbanPopup();
        homePage.typeTitle(kanbanTitle);
        homePage.saveKanban();
        var kanbanPage = homePage.openKanbanBoardWithName(kanbanTitle);
        for (int i = 0; i < 3; i++) {
            kanbanPage.createNewTask(String.format("%d" + taskName, i), taskColor, String.format("%d " + taskDescription, i));
        }
    }
}
