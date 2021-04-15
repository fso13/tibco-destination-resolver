package ru.openbank.marketplace.tibco.resolver.utils;

import ru.openbank.marketplace.tibco.resolver.api.Direction;
import ru.openbank.marketplace.tibco.resolver.api.Resolver;
import ru.openbank.marketplace.tibco.resolver.api.TibcoClarification;
import ru.openbank.marketplace.tibco.resolver.api.TibcoEntity;
import ru.openbank.marketplace.tibco.resolver.api.TibcoMode;
import ru.openbank.marketplace.tibco.resolver.api.TibcoOperation;
import ru.openbank.marketplace.tibco.resolver.api.Version;
import ru.openbank.marketplace.tibco.resolver.impl.ResolverOldVersion;
import ru.openbank.marketplace.tibco.resolver.impl.ResolverVersionOne;

import java.util.HashMap;
import java.util.Map;

public class TibcoQueueUtils {
    private final Map<Version, Resolver> resolverMap = new HashMap<Version, Resolver>() {{
        put(Version.V_OLD, new ResolverOldVersion());
        put(Version.V_1, new ResolverVersionOne());
    }};

    private final String queuePrefix;
    private final String pushPrefix;

    public TibcoQueueUtils(String queuePrefix, String pushPrefix) {
        this.queuePrefix = queuePrefix;
        this.pushPrefix = pushPrefix;
    }

    /**
     * Метод генерирует название очереди для старой версии протокола взаимодествия
     *
     * @param entity    example "Account.Blocking.Info"
     * @param operation example "Get"
     * @return example MarketPlace.Get.Account.Blocking.Info.Rq
     * @see ru.openbank.marketplace.tibco.resolver.utils.TibcoQueueUtilsTest#makeQueueNameSyncRequestAndResponseOld
     */
    public String makeQueueNameRequest(TibcoEntity entity, TibcoOperation operation) {
        return makeQueue(Version.V_OLD, Direction.REQUEST, entity, operation, TibcoMode.SYNC, null, queuePrefix);
    }

    public String makeQueueNameResponse(TibcoEntity entity, TibcoOperation operation) {
        return makeQueue(Version.V_OLD, Direction.RESPONSE, entity, operation, TibcoMode.SYNC, null, queuePrefix);
    }

    /**
     * @param entity        example "Account"
     * @param operation     example "Register"
     * @param mode          example "Sync"
     * @param clarification example "Permissions.To.CFT"
     * @return example Account.Register.Permissions.To.CFT.Sync.MarketPlace.TIBCO.Rq
     * @see ru.openbank.marketplace.tibco.resolver.utils.TibcoQueueUtilsTest#makeQueueNameSyncRequestAndResponseV1
     */
    public String makeQueueNameRequest(TibcoEntity entity, TibcoOperation operation, TibcoMode mode, TibcoClarification clarification) {
        return makeQueue(Version.V_1, Direction.REQUEST, entity, operation, mode, clarification, queuePrefix);
    }

    public String makeQueueNameResponse(TibcoEntity entity, TibcoOperation operation, TibcoMode mode, TibcoClarification clarification) {
        return makeQueue(Version.V_1, Direction.RESPONSE, entity, operation, mode, clarification, queuePrefix);
    }

    /**
     * Метод преднозначен для получения названия очереди из destination JmsListener
     * 'push.NegativeOperations.Send.Status'                                       | 'queuePrefix.pushPrefix.MarketPlace.NegativeOperations.Send.Status.Rq'
     * 'async.Send.VirtualCard.Status'                                             | 'queuePrefix.pushPrefix.MarketPlace.Send.VirtualCard.Status.Rs'
     * 'version1.async.Card.Get.Balance'                                           | 'queuePrefix.pushPrefix.Card.Get.Balance.Sync.TIBCO.MarketPlace.Rs'
     * 'version1.sync.Application.Send.ADM.Application.Notification.From.InforCRM' | 'queuePrefix.pushPrefix.Application.Send.ADM.Application.Notification.From.InforCRM.Sync.TIBCO.MarketPlace.Rq'
     * 'version1.push.Account.Register.Permissions.From.CFT'                       | 'queuePrefix.pushPrefix.Account.Register.Permissions.From.CFT.Async.TIBCO.MarketPlace.Rq'
     *
     * @see ru.openbank.marketplace.tibco.resolver.utils.TibcoQueueUtilsTest#makeQueueByListener
     */
    public String makeQueueByListener(String destination) {

        int index = destination.indexOf(".");
        String version = destination.substring(0, index);
        return makePrefixForListener() + "." + resolverMap.get(Version.getByName(version)).makeQueueByListener(destination);
    }

    private String makeQueue(Version version,
                             Direction direction,
                             TibcoEntity entity,
                             TibcoOperation operation,
                             TibcoMode mode,
                             TibcoClarification clarification,
                             String prefix) {
        return prefix + "." + resolverMap.get(version).makeQueueBySend(direction, entity, operation, mode, clarification);
    }

    private String makePrefixForListener() {
        return pushPrefix == null || pushPrefix.isEmpty() ? queuePrefix : queuePrefix + "." + pushPrefix;
    }

}
