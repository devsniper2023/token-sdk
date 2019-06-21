package com.r3.corda.lib.tokens.workflows

import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.common.internal.testNetworkParameters
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.Timeout
import java.util.concurrent.TimeUnit

abstract class JITMockNetworkTests(val names: List<CordaX500Name> = emptyList()) {

    @get:Rule
    //overly generous but we just want to find
    val timeoutRule = Timeout(5, TimeUnit.MINUTES)

    companion object {
        const val DEFAULT_LOCALITY: String = "London"
        const val DEFAULT_COUNTRY: String = "GB"
    }

    constructor(vararg names: String) : this(names.map { CordaX500Name(it, DEFAULT_LOCALITY, DEFAULT_COUNTRY) })

    constructor(numberOfNodes: Int) : this(*(1..numberOfNodes).map { "Party${it.toChar() + 64}" }.toTypedArray())

    protected val network = MockNetwork(
            cordappPackages = listOf(
                    "com.r3.corda.lib.tokens.money",
                    "com.r3.corda.lib.tokens.contracts",
                    "com.r3.corda.lib.tokens.workflows"
            ),
            threadPerNode = true,
            networkParameters = testNetworkParameters(minimumPlatformVersion = 4)
    )

    /** The nodes which makes up the network. */
    protected val nodes: LinkedHashMap<CordaX500Name, StartedMockNode> = LinkedHashMap()

    fun node(organisation: String, locality: String = DEFAULT_LOCALITY, country: String = DEFAULT_COUNTRY): StartedMockNode {
        return node(CordaX500Name(organisation, locality, country))
    }

    fun node(name: CordaX500Name): StartedMockNode {
        return nodes.getOrPut(name) { network.createPartyNode(name) }
    }

    fun identity(organisation: String, locality: String = DEFAULT_LOCALITY, country: String = DEFAULT_COUNTRY): Party {
        return identity(CordaX500Name(organisation, locality, country))
    }

    fun identity(name: CordaX500Name): Party {
        return nodes.getOrPut(name) { network.createPartyNode(name) }.legalIdentity()
    }

    @Before
    fun startNetwork() {
        network.startNodes()
        names.forEach { node(it) }
    }

    @After
    fun stopNetwork() {
        network.stopNodes()
    }

    fun restartNetwork() {
        stopNetwork()
        startNetwork()
    }

    protected val notary: StartedMockNode get() = network.defaultNotaryNode

    protected val notaryIdentity: Party get() = notary.legalIdentity()

    /**
     * Smart helper for [assertHasTransaction] that passes in the test suite's network.
     */
    protected fun assertHasTransaction(tx: SignedTransaction, vararg nodes: StartedMockNode) = assertHasTransaction(tx, network, *nodes)

    /**
     * Smart helper for [assertNotHasTransaction] that passes in the test suite's network.
     */
    protected fun assertNotHasTransaction(tx: SignedTransaction, vararg nodes: StartedMockNode) = assertNotHasTransaction(tx, network, *nodes)
}