package com.alazarska.peopleapplication.uitests.tests;

import com.alazarska.peopleapplication.uitests.pages.ErrorPage;
import com.alazarska.peopleapplication.uitests.pages.PeopleListPage;
import com.alazarska.peopleapplication.uitests.pages.PersonDetailsPage;
import com.alazarska.peopleapplication.uitests.pages.UpdatePersonPage;
import com.alazarska.peopleapplication.uitests.utils.UrlBuilder;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PeopleListTest extends BaseTest {

    @Test
    public void shouldAllowToNavigateToPersonDetailsPage() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Tom", "New", "test@test.com", "1990-07-17", new BigDecimal(70000));

        String personId = personDetailsPage.getPersonId();

        personDetailsPage
                .getPeopleListPage()
                .getPersonDetailsPage(personId);

        assertThat(personDetailsPage.getPersonNameHeader()).isEqualTo("Tom New");
        assertThat(personDetailsPage.getPersonEmail().getText()).isEqualTo("test@test.com");
    }

    @Test
    public void shouldAllowToNavigateToUpdatePersonPage() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Jacob", "Smith", "smith@test.com", "2015-06-30", new BigDecimal(50000));

        String personId = personDetailsPage.getPersonId();

        UpdatePersonPage updatePersonPage = personDetailsPage
                .getPeopleListPage()
                .getUpdatePersonPage(personId);

        assertThat(updatePersonPage.getUpdatePageHeader().getText()).isEqualToIgnoringCase("update person");
        assertThat(updatePersonPage.getEmailInput().getAttribute("value")).isEqualTo("smith@test.com");
    }

    @Test
    public void shouldAllowToDeleteSelectedPerson() {
        PersonDetailsPage personDetailsPage = new PeopleListPage(driver)
                .getSavePersonPage()
                .savePerson("Ann", "Smith", "ann.smith@test.com", "2001-08-15", new BigDecimal(100000));

        String personId = personDetailsPage.getPersonId();

        personDetailsPage.getPeopleListPage().deleteSelectedPerson(personId);
        driver.get(UrlBuilder.buildPersonDetailsPageUrl(personId));
        assertThat(new ErrorPage(driver).getErrorInformation().getText()).isEqualTo("Person not found");
    }
}
