package com.r3.corda.sdk.token.workflow.flows.redeem

import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.sdk.token.workflow.flows.finality.ObserverAwareFinalityFlow
import com.r3.corda.sdk.token.workflow.utilities.ourSigningKeys
import net.corda.confidential.IdentitySyncFlow
import net.corda.core.flows.CollectSignaturesFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

abstract class AbstractRedeemTokensFlow : FlowLogic<SignedTransaction>() {
    abstract val issuerSession: FlowSession
    abstract val observerSessions: List<FlowSession>

    companion object {
        object SELECTING_STATES : ProgressTracker.Step("Selecting states to redeem.")
        object SYNC_IDS : ProgressTracker.Step("Synchronising confidential identities.")
        object COLLECT_SIGS : ProgressTracker.Step("Collecting signatures")
        object FINALISING_TX : ProgressTracker.Step("Finalising transaction")

        fun tracker() = ProgressTracker(SELECTING_STATES, SYNC_IDS, COLLECT_SIGS, FINALISING_TX)
    }

    override val progressTracker: ProgressTracker = tracker()

    @Suspendable
    abstract fun generateExit(transactionBuilder: TransactionBuilder): TransactionBuilder

    @Suspendable
    override fun call(): SignedTransaction {
        val txBuilder = TransactionBuilder()
        progressTracker.currentStep = SELECTING_STATES
        generateExit(txBuilder)
        // First synchronise identities between issuer and our states.
        progressTracker.currentStep = SYNC_IDS
        subFlow(IdentitySyncFlow.Send(issuerSession, txBuilder.toWireTransaction(serviceHub)))
        val ourSigningKeys = txBuilder.toLedgerTransaction(serviceHub).ourSigningKeys(serviceHub)
        val partialStx = serviceHub.signInitialTransaction(txBuilder, ourSigningKeys)
        // Call collect signatures flow, issuer should perform all the checks for redeeming states.
        progressTracker.currentStep = COLLECT_SIGS
        val stx = subFlow(CollectSignaturesFlow(partialStx, listOf(issuerSession), ourSigningKeys))
        progressTracker.currentStep = FINALISING_TX
        return subFlow(ObserverAwareFinalityFlow(stx, observerSessions))
    }
}
