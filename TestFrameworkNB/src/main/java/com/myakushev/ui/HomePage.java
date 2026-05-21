package com.myakushev.ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends Page {
    private SelenideElement createKanbanButton = element(withText("Create Kanban"));
    private SelenideElement popup = $("mat-dialog-container[id^=mat-dialog]");

    private SelenideElement titleInput = popup.$("input[placeholder='Title']");
    private SelenideElement saveButton = popup.$x(".//button[text()='Save']");
    private SelenideElement closeButton = popup.$x(".//button[text()='Close']");

//    private SelenideElement mainCard = $(byXpath(".//div[@class='main-card']"));
//    private ElementsCollection kanbanItem = mainCard.$$x(".//a[@class='mat-list-item']");


//    private SelenideElement mainCard = $(byXpath(".//div[@class='main-card']"));

    private ElementsCollection kanbanItems() {
        return $$x("//div[contains(@class,'main-card')]//a[contains(@class,'mat-list-item')]");
    }

    public HomePage open() {
        Selenide.open("http://localhost:4200/");
        return this;
    }

    public void openCreateKanbanPopup() {
        createKanbanButton.click();
    }

    public void typeTitle(String kanbanTitle) {
        popup.shouldBe(visible);
        titleInput.click();
        titleInput.sendKeys(kanbanTitle);
    }

    public void saveKanban() {
        saveButton.click();
        popup.shouldBe(hidden);
        refreshPage(); // workaround to avoid bug in UI, when first kanban is not appeared after creation
    }

    public void closeKanban() {
        closeButton.click();
    }

    public KanbanPage openKanbanBoardWithName(String kanbanName) {
        kanbanItems()
                .findBy(text(kanbanName))
                .shouldBe(visible)
                .shouldBe(clickable)
                .click();
        KanbanPage kanbanPage = new KanbanPage();
        kanbanPage.checkKanbanUrl();
        kanbanPage.checkKanbanPageTitle(kanbanName);
        return kanbanPage;
    }
}
