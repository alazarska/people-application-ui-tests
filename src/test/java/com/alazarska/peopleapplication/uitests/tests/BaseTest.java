package com.alazarska.peopleapplication.uitests.tests;

import com.alazarska.peopleapplication.uitests.utils.DriverFactory;
import com.alazarska.peopleapplication.uitests.utils.UrlBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get(UrlBuilder.buildPeopleListUrl());
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
