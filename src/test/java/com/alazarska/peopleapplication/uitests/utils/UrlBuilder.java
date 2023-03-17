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

    public static String buildPersonImageUrlWithId(String personId, String fileExtension) {
        return buildPersonImageUrl(personId + "." + fileExtension);
    }

    public static String buildPersonImageUrlToDefaultAvatar() {
        return buildPersonImageUrl("default-avatar.jpg");
    }

    private static String buildPersonImageUrl(String fileName) {
        return TestConfiguration.applicationUrl + "/people/images/" + fileName;
    }
}
