package ru.openbank.marketplace.tibco.resolver.api;

import java.util.Arrays;

public enum Version {
    V_OLD(""),
    V_1("version1"),

    ;

    private final String name;

    Version(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Version getByName (String name) {
        return Arrays.stream(Version.values())
                .filter(n->n.name.equals(name))
                .findFirst()
                .orElse(V_OLD);
    }
}
