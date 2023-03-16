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

    public static String buildPersonImageUrl(String personId, String fileExtension) {
        return TestConfiguration.applicationUrl + "/people/images/" + personId + "." + fileExtension;
    }

    public static String buildPersonImageUrlToDefaultAvatar() {
        return TestConfiguration.applicationUrl + "/people/images/default-avatar.jpg";
    }
}
