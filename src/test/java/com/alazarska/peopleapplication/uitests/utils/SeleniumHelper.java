package com.alazarska.peopleapplication.uitests.utils;

import com.alazarska.peopleapplication.uitests.pages.ErrorPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumHelper {

    public static void waitForElementToBeVisible(WebElement element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void checkIfUrlWithIdWhichNotExistNavigateToNotFoundPersonPage(String url, WebDriver driver) {
        driver.get(url);
        String errorInformation = new ErrorPage(driver).getErrorInformation().getText();

        assertThat(errorInformation).isEqualTo("Person not found");
    }

    public void checkIfInvalidUrlNavigateToErrorPage(String url, WebDriver driver) {
        driver.get(url);
        String errorInformation = new ErrorPage(driver).getErrorInformation().getText();

        assertThat(errorInformation).isEqualTo("Something went wrong. Please try again.");
    }
}
