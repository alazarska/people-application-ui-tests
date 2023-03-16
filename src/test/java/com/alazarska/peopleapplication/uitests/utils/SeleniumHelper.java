package com.alazarska.peopleapplication.uitests.utils;

import com.alazarska.peopleapplication.uitests.pages.ErrorPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumHelper {

    public static void scrollToElement(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo({ top: arguments[0].getBoundingClientRect().y, left:0, behavior: \"instant\"});", element);
    }

    public static void setAttribute(WebElement element, String attName, String attValue, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attName, attValue);
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
