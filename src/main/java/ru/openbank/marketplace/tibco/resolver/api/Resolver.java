package ru.openbank.marketplace.tibco.resolver.api;

public interface Resolver {

    Version version();

    String makeQueueBySend(Direction direction,
                           TibcoEntity entity,
                           TibcoOperation operation,
                           TibcoMode mode,
                           TibcoClarification clarification);

    String makeQueueByListener(String destination);
}
