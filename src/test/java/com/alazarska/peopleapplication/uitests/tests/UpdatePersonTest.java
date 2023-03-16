package com.alazarska.peopleapplication.uitests.tests;

import com.alazarska.peopleapplication.uitests.pages.PeopleListPage;
import com.alazarska.peopleapplication.uitests.pages.PersonDetailsPage;
import com.alazarska.peopleapplication.uitests.pages.UpdatePersonPage;
import com.alazarska.peopleapplication.uitests.utils.SeleniumHelper;
import com.alazarska.peopleapplication.uitests.utils.UrlBuilder;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePersonTest extends BaseTest {

    @Test
    public void shouldShowUpdatePersonFormWithFilledInputFields() {
        UpdatePersonPage updatePersonPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "New", "test@test.com", "1990-07-17", new BigDecimal(70000))
                .getUpdatePersonPage();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(updatePersonPage.getUpdatePageHeader().getText()).isEqualTo("Update Person");
        softAssertions.assertThat(updatePersonPage.getFirstNameInput().getAttribute("value")).isEqualTo("Tom");
        softAssertions.assertThat(updatePersonPage.getLastNameInput().getAttribute("value")).isEqualTo("New");
        softAssertions.assertThat(updatePersonPage.getEmailInput().getAttribute("value")).isEqualTo("test@test.com");
        softAssertions.assertThat(updatePersonPage.getDateOfBirthInput().getAttribute("value")).isEqualTo("1990-07-17");
        softAssertions.assertThat(updatePersonPage.getSalaryInput().getAttribute("value")).isEqualTo("70000.00");
        softAssertions.assertThat(updatePersonPage.getPhotoFileName().getAttribute("src")).isEqualTo(UrlBuilder.buildPersonImageUrlToDefaultAvatar());
        softAssertions.assertAll();
    }

    @Test
    public void shouldAllowToUpdatePersonWithValidData() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "New ", "new@test.com", "1970-01-20", new BigDecimal(850000))
                .getUpdatePersonPage()
                .setFirstName("Thomas")
                .setLastName("Newman")
                .setEmail("thomas.newman@test.com")
                .setDob("1970-10-20")
                .setSalary("900000")
                .setPhotoFileName("tomnew.jpg")
                .saveUpdateFormWithValidData();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(personDetailsPage.getPersonFirstName().getText()).isEqualTo("Thomas");
        softAssertions.assertThat(personDetailsPage.getPersonLastName().getText()).isEqualTo("Newman");
        softAssertions.assertThat(personDetailsPage.getPersonEmail().getText()).isEqualTo("thomas.newman@test.com");
        softAssertions.assertThat(personDetailsPage.getPersonDateOfBirth().getText()).isEqualTo("October 20, 1970");
        softAssertions.assertThat(personDetailsPage.getPersonSalary().getText()).contains("900,000.00");
        softAssertions.assertThat(personDetailsPage.getPersonPhoto().getAttribute("src")).isEqualTo(
                UrlBuilder.buildPersonImageUrl(personDetailsPage.getPersonId(), "jpg"));
        softAssertions.assertAll();
    }

    @Test
    public void shouldThrowValidationInformationWhenPersonIsUpdatedWithEmptyData() {
        testValidationInformationWhenPersonIsUpdatedWithInvalidData(
                updatePersonPage -> {
                    updatePersonPage
                            .setFirstName("")
                            .setLastName("")
                            .setEmail("")
                            .setDob("")
                            .setSalary("");
                },
                List.of(
                        "First Name can not be empty",
                        "Last Name can not be empty",
                        "The email address is required",
                        "Date of birth can not be empty",
                        "Salary can not be empty"
                )
        );
    }

    @Test
    public void shouldThrowValidationInformationWhenPersonIsUpdatedWithInvalidEmail() {
        testValidationInformationWhenPersonIsUpdatedWithInvalidData(
                updatePersonPage -> {
                    updatePersonPage
                            .setEmail("aa");
                },
                List.of(
                        "Email must be valid"
                )
        );
    }

    @Test
    public void shouldThrowValidationInformationWhenPersonIsUpdatedWithDateOfBirthInFuture() {
        testValidationInformationWhenPersonIsUpdatedWithInvalidData(
                updatePersonPage -> {
                    updatePersonPage
                            .setDob("3000-01-01");
                },
                List.of(
                        "Date of birth must be in past"
                )
        );
    }

    private void testValidationInformationWhenPersonIsUpdatedWithInvalidData(Consumer<UpdatePersonPage> setFields, List<String> expectedValidationErrors) {
        UpdatePersonPage updatePersonPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "Jones", "tom.jones@test.com", "1960-06-06", new BigDecimal(850000))
                .getUpdatePersonPage();

        setFields.accept(updatePersonPage);
        updatePersonPage.saveUpdateFormWithInvalidData();
        List<String> validationInfo = updatePersonPage.getValidationInfo();

        assertThat(validationInfo).containsExactlyInAnyOrderElementsOf(expectedValidationErrors);
    }

    @Test
    public void shouldNavigateToNotFoundPersonPageWhenPersonWithGivenIdNotExist() {
        String url = UrlBuilder.buildPersonUpdatePageUrl("0");
        SeleniumHelper.checkIfUrlWithIdWhichNotExistNavigateToNotFoundPersonPage(url, driver);
    }

    @Test
    public void shouldNavigateToErrorPageWhenIdInUrlIsNotNumber() {
        String url = UrlBuilder.buildPersonUpdatePageUrl("aa");
        SeleniumHelper.checkIfInvalidUrlNavigateToErrorPage(url, driver);
    }
}
