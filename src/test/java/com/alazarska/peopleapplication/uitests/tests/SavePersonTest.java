package com.alazarska.peopleapplication.uitests.tests;

import com.alazarska.peopleapplication.uitests.pages.PeopleListPage;
import com.alazarska.peopleapplication.uitests.pages.PersonDetailsPage;
import com.alazarska.peopleapplication.uitests.pages.SavePersonPage;
import com.alazarska.peopleapplication.uitests.utils.TestAssertionsHelper;
import com.alazarska.peopleapplication.uitests.utils.UrlBuilder;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SavePersonTest extends BaseTest {

    @Test
    public void shouldAllowToSaveNewPersonWithValidDataWithoutPhoto() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "Smith", "smith@test.com", "1950-02-25", new BigDecimal(11000));

        TestAssertionsHelper.assertExpectedDataOnPersonDetailsPage(
                personDetailsPage,
                "Tom",
                "Smith",
                "smith@test.com",
                "February 25, 1950",
                "11,000.00",
                UrlBuilder.buildPersonImageUrlToDefaultAvatar()
        );
    }

    @Test
    public void shouldAllowToSaveNewPersonWithValidDataWithPhoto() {
        SavePersonPage savePersonPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .setPhotoFileName("annnew.JPG");

        PersonDetailsPage personDetailsPage = savePersonPage
                .savePerson("Ann", "New", "new@test.com", "1970-10-10", new BigDecimal(11000));

        TestAssertionsHelper.assertExpectedDataOnPersonDetailsPage(
                personDetailsPage,
                "Ann",
                "New",
                "new@test.com",
                "October 10, 1970",
                "11,000.00",
                UrlBuilder.buildPersonImageUrlWithId(personDetailsPage.getPersonId(), "JPG")
        );
    }

    @Test
    public void shouldNotAllowToSavePersonWithInvalidData() {
        SavePersonPage savePersonPage = new PeopleListPage(driver)
                .getSavePersonPage();

        savePersonPage.getSavePersonButton().click();
        List<String> validationInfo = savePersonPage.getValidationInfo();

        assertThat(validationInfo).containsExactlyInAnyOrder(
                "First Name can not be empty",
                "Last Name can not be empty",
                "The email address is required",
                "Date of birth can not be empty",
                "Salary can not be empty"
        );
    }

    @Test
    public void shouldNotAllowToSavePersonWithDateOfBirthInFuture() {
        SavePersonPage savePersonPage = new PeopleListPage(driver)
                .getSavePersonPage();

        savePersonPage.savePerson("Anny", "New", "new@test.com", "3000-01-01", new BigDecimal(1100));
        List<String> validationInfo = savePersonPage.getValidationInfo();

        assertThat(validationInfo).contains("Date of birth must be in past");
    }
}
