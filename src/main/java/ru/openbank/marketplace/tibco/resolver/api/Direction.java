package ru.openbank.marketplace.tibco.resolver.api;

public enum Direction {
    REQUEST("Rq"),
    RESPONSE("Rs"),

    ;

    private final String name;

    Direction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
