@file:JvmName("CheckUtilities")
package com.r3.corda.lib.tokens.workflows.internal

import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.lib.tokens.contracts.states.AbstractToken
import net.corda.core.contracts.StateAndRef
import net.corda.core.identity.Party
import net.corda.core.node.services.IdentityService

// Check that all states share the same notary.
@Suspendable
internal fun checkSameNotary(stateAndRefs: List<StateAndRef<AbstractToken>>) {
    val notary = stateAndRefs.first().state.notary
    check(stateAndRefs.all { it.state.notary == notary }) {
        "All states should have the same notary. Automatic notary change isn't supported for now."
    }
}

// Checks if all states have the same issuer. If the issuer is provided as a parameter then it checks if all states
// were issued by this issuer.
@Suspendable
internal fun checkSameIssuer(
        stateAndRefs: List<StateAndRef<AbstractToken>>,
        issuer: Party? = null
) {
    val issuerToCheck = issuer ?: stateAndRefs.first().state.data.issuer
    check(stateAndRefs.all { it.state.data.issuer == issuerToCheck }) {
        "Tokens with different issuers."
    }
}

// Check if owner of the states is well known. Check if states come from the same owner.
// Should be called after synchronising identities step.
@Suspendable
internal fun checkOwner(
        identityService: IdentityService,
        stateAndRefs: List<StateAndRef<AbstractToken>>,
        counterparty: Party
) {
    val owners = stateAndRefs.map { identityService.wellKnownPartyFromAnonymous(it.state.data.holder) }
    check(owners.all { it != null }) {
        "Received states with owner that we don't know about."
    }
    check(owners.all { it == counterparty }) {
        "Received states that don't come from counterparty that initiated the flow."
    }
}
