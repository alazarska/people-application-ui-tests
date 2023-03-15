package com.alazarska.peopleapplication.uitests.pages;

import com.alazarska.peopleapplication.uitests.utils.TestConfiguration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class SavePersonPage {

    private WebDriver driver;

    public SavePersonPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "dob")
    private WebElement dobInput;

    @FindBy(id = "salary")
    private WebElement salaryInput;

    @FindBy(id = "photoFileName")
    private WebElement photoFileNameInput;

    @FindBy(css = "button[data-selenium-id= 'save-button']")
    private WebElement savePersonButton;

    @FindBy(className = "invalid-feedback")
    private List<WebElement> validationInfo;

    public WebElement getSavePersonButton() {
        return savePersonButton;
    }

    public PersonDetailsPage savePerson(String firstName, String lastName, String email, String dob, BigDecimal salary) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        setAttribute(dobInput, "value", dob);
        salaryInput.sendKeys(salary.toString());
        savePersonButton.click();
        return new PersonDetailsPage(driver);
    }

    public SavePersonPage setPhotoFileName(String photoFileName) {
        Path filepath = Path.of(TestConfiguration.imagesDirectory).resolve(photoFileName).normalize();
        photoFileNameInput.sendKeys(filepath.toString());
        return this;
    }

    public List<String> getValidationInfo() {
        return validationInfo.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private void setAttribute(WebElement element, String attName, String attValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attName, attValue);
    }
}
