package com.alazarska.peopleapplication.uitests.utils;

public class UrlBuilder {

    public static String buildPeopleListUrl() {
        return TestConfiguration.applicationUrl + "/people";
    }

    public static String buildPersonDetailsPageUrl(String personId) {
        return TestConfiguration.applicationUrl + "/people/" + personId;
    }

    public static String buildPersonUpdatePageUrl(String personId) {
        return TestConfiguration.applicationUrl + "/people/" + personId + "/update";
    }
}
