package com.alazarska.peopleapplication.uitests.utils;

import com.alazarska.peopleapplication.uitests.pages.ErrorPage;
import com.alazarska.peopleapplication.uitests.pages.PersonDetailsPage;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class TestAssertionsHelper {

    public static void assertExpectedDataOnPersonDetailsPage(PersonDetailsPage personDetailsPage, String firstName, String lastName, String email, String dateOfBirth, String salary, String imageSourceValue) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(personDetailsPage.getPersonNameHeader()).isEqualTo(firstName + " " + lastName);
        softAssertions.assertThat(personDetailsPage.getPersonFirstName().getText()).isEqualTo(firstName);
        softAssertions.assertThat(personDetailsPage.getPersonLastName().getText()).isEqualTo(lastName);
        softAssertions.assertThat(personDetailsPage.getPersonEmail().getText()).isEqualTo(email);
        softAssertions.assertThat(personDetailsPage.getPersonDateOfBirth().getText()).isEqualTo(dateOfBirth);
        softAssertions.assertThat(personDetailsPage.getPersonSalary().getText()).contains(salary);
        softAssertions.assertThat(personDetailsPage.getPersonPhoto().getAttribute("src")).isEqualTo(imageSourceValue);
        softAssertions.assertAll();
    }

    public static void checkIfUrlWithIdWhichNotExistNavigateToNotFoundPersonPage(String url, WebDriver driver) {
        driver.get(url);
        String errorInformation = new ErrorPage(driver).getErrorInformation().getText();

        assertThat(errorInformation).isEqualTo("Person not found");
    }

    public static void checkIfInvalidUrlNavigateToErrorPage(String url, WebDriver driver) {
        driver.get(url);
        String errorInformation = new ErrorPage(driver).getErrorInformation().getText();

        assertThat(errorInformation).isEqualTo("Something went wrong. Please try again.");
    }
}
