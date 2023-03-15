package com.alazarska.peopleapplication.uitests.tests;

import com.alazarska.peopleapplication.uitests.pages.PeopleListPage;
import com.alazarska.peopleapplication.uitests.pages.PersonDetailsPage;
import com.alazarska.peopleapplication.uitests.pages.UpdatePersonPage;
import com.alazarska.peopleapplication.uitests.utils.SeleniumHelper;
import com.alazarska.peopleapplication.uitests.utils.TestConfiguration;
import com.alazarska.peopleapplication.uitests.utils.UrlBuilder;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePersonTest extends BaseTest {

    private final SeleniumHelper seleniumHelper = new SeleniumHelper();

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
        softAssertions.assertThat(updatePersonPage.getDobInput().getAttribute("value")).isEqualTo("1990-07-17");
        softAssertions.assertThat(updatePersonPage.getSalaryInput().getAttribute("value")).isEqualTo("70000.00");
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
                .saveUpdatedDataValid();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(personDetailsPage.getPersonFirstName().getText()).isEqualTo("Thomas");
        softAssertions.assertThat(personDetailsPage.getPersonLastName().getText()).isEqualTo("Newman");
        softAssertions.assertThat(personDetailsPage.getPersonEmail().getText()).isEqualTo("thomas.newman@test.com");
        softAssertions.assertThat(personDetailsPage.getPersonDob().getText()).isEqualTo("October 20, 1970");
        softAssertions.assertThat(personDetailsPage.getPersonSalary().getText()).contains("900,000.00");
        softAssertions.assertThat(personDetailsPage.getPersonPhoto().getAttribute("src")).contains(
                TestConfiguration.applicationUrl + "/people/images/" + personDetailsPage.getPersonId() + ".jpg");
        softAssertions.assertAll();
    }

    @Test
    public void shouldThrowValidationInformationWhenPersonIsUpdatedWithInvalidData() {
        testValidationInformationWhenPersonIsUpdatedWithInvalidData(
                updatePersonPage -> updatePersonPage.setFirstName("").setEmail("a"),
                List.of(
                        "First Name can not be empty",
                        "Email must be valid"
                )
        );
    }

    private void testValidationInformationWhenPersonIsUpdatedWithInvalidData(Consumer<UpdatePersonPage> setFields, List<String> expectedValidationErrors) {
        UpdatePersonPage updatePersonPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "Jones", "tom.jones@test.com", "1960-06-06", new BigDecimal(850000))
                .getUpdatePersonPage();

        setFields.accept(updatePersonPage);

        updatePersonPage.saveUpdatedDataInvalid();

        List<String> validationInfo = updatePersonPage.getValidationInfo();

        assertThat(validationInfo).containsExactlyInAnyOrderElementsOf(expectedValidationErrors);
    }

    @Test
    public void shouldNavigateToNotFoundPersonPageWhenPersonWithGivenIdNotExist() {
        String url = UrlBuilder.buildPersonUpdatePageUrl("0");
        seleniumHelper.checkIfUrlWithIdWhichNotExistNavigateToNotFoundPersonPage(url, driver);
    }

    @Test
    public void shouldNavigateToErrorPageWhenIdInUrlIsNotNumber() {
        String url = UrlBuilder.buildPersonUpdatePageUrl("aa");
        seleniumHelper.checkIfInvalidUrlNavigateToErrorPage(url, driver);
    }
}
