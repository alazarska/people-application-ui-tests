package com.alazarska.peopleapplication.uitests.pages;

import com.alazarska.peopleapplication.uitests.utils.SeleniumHelper;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
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
        SeleniumHelper.scrollToElement(addPersonButton, driver);
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
        SeleniumHelper.scrollToElement(actionButton, driver);
        actionButton.click();
    }
}

