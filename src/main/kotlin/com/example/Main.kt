package com.example

import net.corda.node.driver.driver
import net.corda.node.services.transactions.SimpleNotaryService
import net.corda.core.node.services.ServiceInfo

/**
 * This file is exclusively for being able to run your nodes through an IDE (as opposed to running deployNodes)
 * Do not use in a production environment.
 */
fun main(args: Array<String>) {
    driver(dsl = {
        startNode("Notary", setOf(ServiceInfo(SimpleNotaryService.type)))
        startNode("Bank A")
        startNode("Bank B")
        waitForAllNodesToFinish()
    }, isDebug = true)
}
