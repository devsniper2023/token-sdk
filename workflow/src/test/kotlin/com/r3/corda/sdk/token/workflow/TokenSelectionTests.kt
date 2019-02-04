package com.r3.corda.sdk.token.workflow

import com.r3.corda.sdk.token.money.GBP
import com.r3.corda.sdk.token.money.USD
import com.r3.corda.sdk.token.workflow.selection.TokenSelection
import com.r3.corda.sdk.token.workflow.types.PartyAndAmount
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.StartedMockNode
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// TODO: Improve these tests. E.g. Order states in a list by state ref, so we can specify exactly which will be picked.
class TokenSelectionTests : MockNetworkTest(numberOfNodes = 4) {

    lateinit var A: StartedMockNode
    lateinit var B: StartedMockNode
    lateinit var I: StartedMockNode
    lateinit var J: StartedMockNode

    @Before
    override fun initialiseNodes() {
        A = nodes[0]
        B = nodes[1]
        I = nodes[2]
        J = nodes[3]
    }

    // List of tokens to create for the tests.
    private val gbpTokens = listOf(100.GBP, 50.GBP, 25.GBP)
    private val usdTokens = listOf(200.USD, 100.USD)

    @Before
    fun setUp() {
        // Create some new token amounts.
        I.issueTokens(GBP, A, NOTARY, 100.GBP).getOrThrow()
        I.issueTokens(GBP, A, NOTARY, 50.GBP).getOrThrow()
        J.issueTokens(GBP, A, NOTARY, 25.GBP).getOrThrow()
        I.issueTokens(USD, A, NOTARY, 200.USD).getOrThrow()
        J.issueTokens(USD, A, NOTARY, 100.USD).getOrThrow()
        network.waitQuiescent()
    }

    @Test
    fun `select up to available amount with tokens sorted by state ref`() {
        val tokenSelection = TokenSelection(A.services)
        val uuid = UUID.randomUUID()
        val one = A.transaction { tokenSelection.attemptSpend(160.GBP, uuid) }
        assertEquals(gbpTokens.size, one.size)
        val two = A.transaction { tokenSelection.attemptSpend(175.GBP, uuid) }
        assertEquals(gbpTokens.size, two.size)
        val results = A.transaction { tokenSelection.attemptSpend(25.GBP, uuid) }
        assertEquals(1, results.size)
    }

    @Test
    fun `not enough tokens available`() {
        val tokenSelection = TokenSelection(A.services)
        val uuid = UUID.randomUUID()
        assertFailsWith<IllegalStateException> {
            A.transaction {
                tokenSelection.attemptSpend(176.GBP, uuid)
            }
        }
    }

    @Test
    fun `generate move test`() {
        val tokenSelection = TokenSelection(A.services)
        val moves = listOf(
                PartyAndAmount(B.legalIdentity(), 140.GBP),
                PartyAndAmount(I.legalIdentity(), 30.GBP)
        )
        val (builder, _) = A.transaction {
            tokenSelection.generateMove(TransactionBuilder(), moves)
        }
        println(builder.toWireTransaction(A.services))
        // Just using this to check and see if the output is as expected.
        // TODO: Assert something...
    }

    // TODO: Test with different notaries.

}