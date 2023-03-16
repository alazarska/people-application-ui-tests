package com.alazarska.peopleapplication.uitests.pages;

import com.alazarska.peopleapplication.uitests.utils.SeleniumHelper;
import com.alazarska.peopleapplication.uitests.utils.TestConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class UpdatePersonPage {

    private final WebDriver driver;

    public UpdatePersonPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div/p[text() = 'Update Person']")
    private WebElement updatePageHeader;

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "dob")
    private WebElement dateOfBirthInput;

    @FindBy(id = "salary")
    private WebElement salaryInput;

    @FindBy(id = "photoFileName")
    private WebElement photoFileNameInput;

    @FindBy(css = "img[data-selenium-id= 'person-image-form']")
    private WebElement photoFileName;

    @FindBy(css = "button[data-selenium-id= 'save-button']")
    private WebElement savePersonButton;

    @FindBy(className = "invalid-feedback")
    private List<WebElement> validationInfo;


    public WebElement getUpdatePageHeader() {
        return updatePageHeader;
    }

    public WebElement getFirstNameInput() {
        return firstNameInput;
    }

    public WebElement getLastNameInput() {
        return lastNameInput;
    }

    public WebElement getDateOfBirthInput() {
        return dateOfBirthInput;
    }

    public WebElement getSalaryInput() {
        return salaryInput;
    }

    public WebElement getPhotoFileName() {
        return photoFileName;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public UpdatePersonPage setFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public UpdatePersonPage setLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        return this;
    }

    public UpdatePersonPage setEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    public UpdatePersonPage setDob(String dob) {
        SeleniumHelper.setAttribute(dateOfBirthInput, "value", dob, driver);
        return this;
    }

    public UpdatePersonPage setSalary(String salary) {
        salaryInput.clear();
        salaryInput.sendKeys(salary);
        return this;
    }

    public UpdatePersonPage setPhotoFileName(String photoFileName) {
        Path filePath = Path.of(TestConfiguration.imagesDirectory).resolve(photoFileName).normalize();
        photoFileNameInput.sendKeys(filePath.toString());
        return this;
    }

    public List<String> getValidationInfo() {
        return validationInfo.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public PersonDetailsPage saveUpdateFormWithValidData() {
        SeleniumHelper.scrollToElement(savePersonButton, driver);
        savePersonButton.click();
        return new PersonDetailsPage(driver);
    }

    public UpdatePersonPage saveUpdateFormWithInvalidData() {
        SeleniumHelper.scrollToElement(savePersonButton, driver);
        savePersonButton.click();
        return this;
    }
}
