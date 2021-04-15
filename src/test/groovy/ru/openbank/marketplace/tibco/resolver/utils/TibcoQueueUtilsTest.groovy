package ru.openbank.marketplace.tibco.resolver.utils

import ru.openbank.marketplace.tibco.resolver.api.TibcoClarification
import ru.openbank.marketplace.tibco.resolver.api.TibcoEntity
import ru.openbank.marketplace.tibco.resolver.api.TibcoMode
import ru.openbank.marketplace.tibco.resolver.api.TibcoOperation
import spock.lang.Specification

class TibcoQueueUtilsTest extends Specification {
    def queuePrefix = "queuePrefix"
    def pushPrefix = "pushPrefix"
    def tibcoQueueUtils = new TibcoQueueUtils(queuePrefix, pushPrefix);

    def "makeQueueByListener"() {

        expect:
        def result = tibcoQueueUtils.makeQueueByListener(param)
        result == queueName

        where:
        param                                                                       | queueName
        'push.NegativeOperations.Send.Status'                                       | 'queuePrefix.pushPrefix.MarketPlace.NegativeOperations.Send.Status.Rq'
        'async.Send.VirtualCard.Status'                                             | 'queuePrefix.pushPrefix.MarketPlace.Send.VirtualCard.Status.Rs'
        'version1.async.Card.Get.Balance'                                           | 'queuePrefix.pushPrefix.Card.Get.Balance.Sync.TIBCO.MarketPlace.Rs'
        'version1.sync.Application.Send.ADM.Application.Notification.From.InforCRM' | 'queuePrefix.pushPrefix.Application.Send.ADM.Application.Notification.From.InforCRM.Sync.TIBCO.MarketPlace.Rq'
        'version1.push.Account.Register.Permissions.From.CFT'                       | 'queuePrefix.pushPrefix.Account.Register.Permissions.From.CFT.Async.TIBCO.MarketPlace.Rq'
    }


    def "makeQueueNameSyncRequestAndResponseOld"() {
        given:
        def entity = TibcoEntity.fromString("Account.Blocking.Info")
        def operation = TibcoOperation.fromString("Get")
        when:

        def resultRq = tibcoQueueUtils.makeQueueNameRequest(entity, operation)
        def resultRs = tibcoQueueUtils.makeQueueNameResponse(entity, operation)
        then:
        resultRq == 'queuePrefix.MarketPlace.Get.Account.Blocking.Info.Rq'
        resultRs == 'queuePrefix.MarketPlace.Get.Account.Blocking.Info.Rs'

    }

    def "makeQueueNameSyncRequestAndResponseV1"() {
        given:
        def entity = TibcoEntity.fromString("Account")
        def operation = TibcoOperation.fromString("Register")
        def clarification = TibcoClarification.fromString("Permissions.To.CFT")

        expect:

        resultRq == tibcoQueueUtils.makeQueueNameRequest(entity, operation, mode, clarification)
        resultRs == tibcoQueueUtils.makeQueueNameResponse(entity, operation, mode, clarification)

        where:
        mode            | resultRq                                                                     | resultRs
        TibcoMode.SYNC  | 'queuePrefix.Account.Register.Permissions.To.CFT.Sync.MarketPlace.TIBCO.Rq'  | 'queuePrefix.Account.Register.Permissions.To.CFT.Sync.TIBCO.MarketPlace.Rs'
        TibcoMode.ASYNC | 'queuePrefix.Account.Register.Permissions.To.CFT.Async.MarketPlace.TIBCO.Rq' | 'queuePrefix.Account.Register.Permissions.To.CFT.Async.TIBCO.MarketPlace.Rs'


    }
}
