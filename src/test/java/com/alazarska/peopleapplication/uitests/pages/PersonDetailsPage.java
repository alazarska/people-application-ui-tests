package com.alazarska.peopleapplication.uitests.pages;

import com.alazarska.peopleapplication.uitests.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PersonDetailsPage {

    private WebDriver driver;

    public PersonDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "h2[data-selenium-id = 'person-name']")
    private WebElement personNameHeader;

    @FindBy(css = "td[data-selenium-id = 'first-name']")
    private WebElement personFirstName;

    @FindBy(css = "td[data-selenium-id = 'last-name']")
    private WebElement personLastName;

    @FindBy(css = "td[data-selenium-id = 'email']")
    private WebElement personEmail;

    @FindBy(css = "td[data-selenium-id = 'dob']")
    private WebElement personDob;

    @FindBy(css = "td[data-selenium-id = 'salary']")
    private WebElement personSalary;

    @FindBy(css = "img[data-selenium-id = 'person-image-lg']")
    private WebElement personPhoto;

    @FindBy(css = "a[data-selenium-id = 'update-button']")
    private WebElement updateButton;

    @FindBy(css = "button[data-selenium-id = 'delete-button']")
    private WebElement deleteButton;

    @FindBy(css = "div.actions a[href ='/people']")
    private WebElement peopleListButton;

    public WebElement getPersonPhoto() {
        return personPhoto;
    }

    public WebElement getPersonFirstName() {
        return personFirstName;
    }

    public WebElement getPersonLastName() {
        return personLastName;
    }

    public WebElement getPersonEmail() {
        return personEmail;
    }

    public WebElement getPersonDob() {
        return personDob;
    }

    public WebElement getPersonSalary() {
        return personSalary;
    }

    public String getPersonNameHeader() {
        SeleniumHelper.waitForElementToBeVisible(personNameHeader, driver);
        return personNameHeader.getText();
    }

    public UpdatePersonPage getUpdatePersonPage() {
        updateButton.click();
        return new UpdatePersonPage(driver);
    }

    public PeopleListPage deletePerson() {
        deleteButton.click();
        return new PeopleListPage(driver);
    }

    public PeopleListPage getPeopleListPage() {
        peopleListButton.click();
        return new PeopleListPage(driver);
    }

    public String getPersonId() {
        String personDetailsPageUrl = driver.getCurrentUrl();
        String[] splitUrl = personDetailsPageUrl.split("/");
        return splitUrl[splitUrl.length - 1];
    }
}
