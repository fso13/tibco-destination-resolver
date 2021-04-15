package ru.openbank.marketplace.tibco.resolver.api;

public class TibcoOperation {
    private final String value;

    private TibcoOperation(String value) {
        this.value = value;
    }

    public static TibcoOperation fromString(String value) {
        return new TibcoOperation(value);
    }

    public String getValue() {
        return value;
    }
}
