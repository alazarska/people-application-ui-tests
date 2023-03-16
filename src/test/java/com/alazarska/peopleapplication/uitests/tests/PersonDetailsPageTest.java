package com.alazarska.peopleapplication.uitests.tests;

import com.alazarska.peopleapplication.uitests.pages.PeopleListPage;
import com.alazarska.peopleapplication.uitests.pages.PersonDetailsPage;
import com.alazarska.peopleapplication.uitests.pages.UpdatePersonPage;
import com.alazarska.peopleapplication.uitests.utils.SeleniumHelper;
import com.alazarska.peopleapplication.uitests.utils.UrlBuilder;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDetailsPageTest extends BaseTest {

    @Test
    public void shouldShowPageWithPersonDetails() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "New", "test@test.com", "1990-07-17", new BigDecimal(70000));

        assertThat(personDetailsPage.getPersonNameHeader()).isEqualTo("Tom New");
        assertThat(personDetailsPage.getPersonEmail().getText()).isEqualTo("test@test.com");
    }

    @Test
    public void shouldAllowToNavigateToUpdatePersonPage() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "New", "test@test.com", "1990-07-17", new BigDecimal(70000));

        UpdatePersonPage updatePersonPage = personDetailsPage.getUpdatePersonPage();

        assertThat(updatePersonPage.getUpdatePageHeader().getText()).isEqualTo("Update Person");
        assertThat(updatePersonPage.getEmailInput().getAttribute("value")).isEqualTo("test@test.com");
    }

    @Test
    public void shouldAllowToDeletePerson() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Ann", "New", "test@test.com", "2000-01-01", new BigDecimal(11000));

        String personId = personDetailsPage.getPersonId();

        PeopleListPage peopleListPage = personDetailsPage.deletePerson();
        assertThat(peopleListPage.getDeleteAlert().getText()).isEqualTo("Selected person has been removed from database.");

        SeleniumHelper.checkIfUrlWithIdWhichNotExistNavigateToNotFoundPersonPage(UrlBuilder.buildPersonDetailsPageUrl(personId), driver);
    }

    @Test
    public void shouldNavigateToNotFoundPersonPageWhenPersonWithGivenIdNotExist() {
        String url = UrlBuilder.buildPersonDetailsPageUrl("0");
        SeleniumHelper.checkIfUrlWithIdWhichNotExistNavigateToNotFoundPersonPage(url, driver);
    }

    @Test
    public void shouldNavigateToErrorPageWhenIdInUrlIsNotNumber() {
        String url = UrlBuilder.buildPersonDetailsPageUrl("aa");
        SeleniumHelper.checkIfInvalidUrlNavigateToErrorPage(url, driver);
    }
}
