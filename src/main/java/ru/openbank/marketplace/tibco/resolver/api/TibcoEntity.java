package ru.openbank.marketplace.tibco.resolver.api;

public class TibcoEntity {
    private final String value;

    private TibcoEntity(String value) {
        this.value = value;
    }

    public static TibcoEntity fromString(String value) {
        return new TibcoEntity(value);
    }

    public String getValue() {
        return value;
    }
}
