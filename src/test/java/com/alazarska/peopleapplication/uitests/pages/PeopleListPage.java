package com.alazarska.peopleapplication.uitests.pages;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PeopleListPage {

    private final WebDriver driver;

    public PeopleListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "a[data-selenium-id = 'delete-button']")
    private WebElement deleteButton;

    @FindBy(css = "a[data-selenium-id = 'save-button']")
    private WebElement addPersonButton;

    @FindBy(id = "deleteAlert")
    private WebElement deleteAlert;

    public WebElement getDeleteAlert() {
        return deleteAlert;
    }

    @SneakyThrows
    public SavePersonPage getSavePersonPage() {
        scrollToElement(addPersonButton);
        addPersonButton.click();
        return new SavePersonPage(driver);
    }

    public PersonDetailsPage getPersonDetailsPage(String id) {
        clickRowAction(id, "info-button");
        return new PersonDetailsPage(driver);
    }

    public UpdatePersonPage getUpdatePersonPage(String id) {
        clickRowAction(id, "update-button");
        return new UpdatePersonPage(driver);
    }

    public PeopleListPage deleteSelectedPerson(String id) {
        clickRowAction(id, "delete-button");
        return this;
    }

    private void clickRowAction(String id, String buttonSeleniumId) {
        String xpath = "//th[@data-selenium-id='person-id' and .='" + id + "']/parent::*/descendant::*[@data-selenium-id='" + buttonSeleniumId + "']";
        WebElement actionButton = driver.findElement(By.xpath(xpath));
        scrollToElement(actionButton);
        actionButton.click();
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo({ top: arguments[0].getBoundingClientRect().y, left:0, behavior: \"instant\"});", element);
    }
}

