package com.alazarska.peopleapplication.uitests.utils;

public class TestConfiguration {

    public static String imagesDirectory = PropertiesLoader.getPropertyValue("images.directory");
    public static String browserName = PropertiesLoader.getPropertyValue("browser.name");
    public static String applicationUrl = PropertiesLoader.getPropertyValue("application.url");
}
