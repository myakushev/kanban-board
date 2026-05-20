package com.myakushev.ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class KanbanPage extends Page {
    private SelenideElement kanbanPageTitleElement = $("div.page-title h1");

    private SelenideElement createTaskButtonElement = $x(".//button[@class='mat-raised-button']");
    private SelenideElement createNewTaskPopupElement = $x("//mat-dialog-container");
    private SelenideElement createNewTaskPopupNameElement = $x(".//mat-dialog-container//div[@class='dialog-title']/h1[text()='Create New Task']");
    private SelenideElement createNewTaskTitleInputElement = createNewTaskPopupElement.$x(".//input[@placeholder='Title']");
    private SelenideElement createNewTaskColorInputElement = createNewTaskPopupElement.$x(".//span[contains(@class, 'mat-select-placeholder') and text()='Select color']");
    private SelenideElement createNewTaskColorPopupElement = $x("//div[starts-with(@id, 'cdk-overlay') and .//span[contains(text(), 'YELLOW')] and .//span[contains(text(), 'BLUE')] and .//span[contains(text(), 'PINK')]]");
    private ElementsCollection createNewTaskConcreteColorElements = createNewTaskColorPopupElement.$$x(".//mat-option");
    private SelenideElement createNewTaskDescriptionTextareaElement = createNewTaskPopupElement.$x(".//textarea[@placeholder='Description']");
    private SelenideElement createNewTaskSaveButtonElement = createNewTaskPopupElement.$x(".//button[text()='Save']");
    private ElementsCollection listContainerElements = $$("div.list-container");

    // Selector templates
    private static final String COLUMN_BY_TITLE_TEMPLATE = "//h2[normalize-space(text())='%s']/parent::div[@class='list-container']";
    private static final String TASK_SELECTOR = "//div[contains(@class, 'sticky-note')]";

    public void checkKanbanPageTitle(String kanbanPageTitle) {
        kanbanPageTitleElement.shouldBe(text(kanbanPageTitle));
    }

    public void checkKanbanUrl() {
        webdriver().shouldHave(urlContaining("/kanbans/"));
    }

    public void createNewTask(String taskTitle, String color, String description) {
        int numberOfToDoTasksBefore = $$x(String.format(COLUMN_BY_TITLE_TEMPLATE, "To do") + TASK_SELECTOR).size();

        createTaskButtonElement.click();
        createNewTaskPopupElement.shouldBe(visible);
        createNewTaskPopupNameElement.shouldBe(visible);

        createNewTaskTitleInputElement.click();
        createNewTaskTitleInputElement.setValue(taskTitle);

        createNewTaskColorPopupElement.shouldBe(hidden);
        createNewTaskColorInputElement.click();
        createNewTaskColorPopupElement.shouldBe(visible);

        createNewTaskColorPopupElement
                .$x(String.format(".//mat-option[.//span[contains(text(), '%s')]]", color.toUpperCase()))
                .click();
        createNewTaskColorPopupElement.shouldBe(hidden);

        createNewTaskDescriptionTextareaElement.click();
        createNewTaskDescriptionTextareaElement.setValue(description);
        createNewTaskSaveButtonElement.click();

        $$x(String.format(COLUMN_BY_TITLE_TEMPLATE, "To do") + TASK_SELECTOR).shouldHave(size(numberOfToDoTasksBefore + 1));
    }
}
