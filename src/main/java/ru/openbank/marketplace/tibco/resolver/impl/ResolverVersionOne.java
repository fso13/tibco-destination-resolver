package ru.openbank.marketplace.tibco.resolver.impl;

import ru.openbank.marketplace.tibco.resolver.api.Direction;
import ru.openbank.marketplace.tibco.resolver.api.Resolver;
import ru.openbank.marketplace.tibco.resolver.api.TibcoClarification;
import ru.openbank.marketplace.tibco.resolver.api.TibcoEntity;
import ru.openbank.marketplace.tibco.resolver.api.TibcoMode;
import ru.openbank.marketplace.tibco.resolver.api.TibcoOperation;
import ru.openbank.marketplace.tibco.resolver.api.Version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResolverVersionOne implements Resolver {

    private static final String patternV1Request = "%s.%s.MarketPlace.TIBCO.Rq";
    private static final String patternV1Response = "%s.%s.TIBCO.MarketPlace.Rs";
    private static final String patternV1Listener = "%s.%s.TIBCO.MarketPlace.%s";

    @Override
    public Version version() {
        return Version.V_1;
    }

    @Override
    public String makeQueueBySend(Direction direction, TibcoEntity entity, TibcoOperation operation, TibcoMode mode, TibcoClarification clarification) {
        if (direction == Direction.REQUEST) {
            return String.format(patternV1Request, joinQueueAttributes(entity.getValue(), operation.getValue(), clarification.getValue()), mode.getName());
        } else {
            return String.format(patternV1Response, joinQueueAttributes(entity.getValue(), operation.getValue(), clarification.getValue()), mode.getName());
        }
    }

    @Override
    public String makeQueueByListener(String destination) {
        String destinationWithoutVersion = destination.substring(version().getName().length() + 1);
        int index = destinationWithoutVersion.indexOf(".");
        String prefix = destinationWithoutVersion.substring(0, index);

        switch (prefix) {
            case "push":
                return String.format(patternV1Listener, destinationWithoutVersion.substring(5), TibcoMode.ASYNC.getName(), Direction.REQUEST.getName());
            case "async":
                return String.format(patternV1Listener, destinationWithoutVersion.substring(6), TibcoMode.SYNC.getName(), Direction.RESPONSE.getName());
            case "sync":
                return String.format(patternV1Listener, destinationWithoutVersion.substring(5), TibcoMode.SYNC.getName(), Direction.REQUEST.getName());
        }

        throw new IllegalArgumentException("Unknown configuration listener: " + destination);
    }

    private String joinQueueAttributes(String... attributes) {
        List<String> attributesWithoutNulls = new ArrayList<>(Arrays.asList(attributes));
        attributesWithoutNulls.removeAll(Collections.singletonList(null));
        return String.join(".", attributesWithoutNulls);
    }
}
