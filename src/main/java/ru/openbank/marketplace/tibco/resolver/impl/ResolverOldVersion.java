package ru.openbank.marketplace.tibco.resolver.impl;

import ru.openbank.marketplace.tibco.resolver.api.Direction;
import ru.openbank.marketplace.tibco.resolver.api.Resolver;
import ru.openbank.marketplace.tibco.resolver.api.TibcoClarification;
import ru.openbank.marketplace.tibco.resolver.api.TibcoEntity;
import ru.openbank.marketplace.tibco.resolver.api.TibcoMode;
import ru.openbank.marketplace.tibco.resolver.api.TibcoOperation;
import ru.openbank.marketplace.tibco.resolver.api.Version;

public class ResolverOldVersion implements Resolver {

    private static final String patternRequest = "MarketPlace.%s.Rq";
    private static final String patternResponse = "MarketPlace.%s.Rs";

    @Override
    public Version version() {
        return Version.V_OLD;
    }

    @Override
    public String makeQueueBySend(Direction direction, TibcoEntity entity, TibcoOperation operation, TibcoMode mode, TibcoClarification clarification) {
        if (direction == Direction.REQUEST) {
            return String.format(patternRequest, String.join(".", operation.getValue(), entity.getValue()));
        } else {
            return String.format(patternResponse, String.join(".", operation.getValue(), entity.getValue()));
        }
    }

    @Override
    public String makeQueueByListener(String destination) {

        int index = destination.indexOf(".");
        String prefix = destination.substring(0, index);

        switch (prefix) {
            case "push":
                return String.format(patternRequest, destination.substring(5));
            case "async":
                return String.format(patternResponse, destination.substring(6));
        }

        throw new IllegalArgumentException("Unknown configuration listener: " + destination);
    }
}
