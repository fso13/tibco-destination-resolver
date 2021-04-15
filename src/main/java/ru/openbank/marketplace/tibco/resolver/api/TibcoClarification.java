package ru.openbank.marketplace.tibco.resolver.api;

public class TibcoClarification {
    private final String value;

    private TibcoClarification(String value) {
        this.value = value;
    }

    public static TibcoClarification fromString(String value) {
        return new TibcoClarification(value);
    }

    public String getValue() {
        return value;
    }
}
