package com.r3.corda.sdk.token.workflow.types

import com.r3.corda.sdk.token.contracts.types.EmbeddableToken
import net.corda.core.contracts.Amount
import net.corda.core.identity.AbstractParty

/** A simple holder for a (possibly anonymous) [AbstractParty] and a quantity of tokens */
data class PartyAndAmount<T : EmbeddableToken>(val party: AbstractParty, val amount: Amount<T>)