package com.myakushev.ui;

import org.testng.annotations.Test;

public class UITest extends BaseUITest {
    @Test
    public void createKanban() {
        var homePage = new HomePage();
        String kanbanTitle = "Kanban board new title";
        homePage.open().openCreateKanbanPopup();
        homePage.typeTitle(kanbanTitle);
        homePage.saveKanban();
        var kanbanPage = homePage.openKanbanBoardWithName(kanbanTitle);
        for (int i = 0; i < 3; i++) {
            kanbanPage.createNewTask(String.format("%d First Task", i), "BLUE", String.format("%d Task first description", i));
        }
    }
}
