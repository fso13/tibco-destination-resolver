package ru.openbank.marketplace.tibco.resolver.api;

public enum TibcoMode {
    SYNC("Sync"),
    ASYNC("Async"),

    ;

    private final String name;

    TibcoMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
