package com.r3.corda.sdk.token.money

import com.r3.corda.sdk.token.contracts.utilities.amount
import net.corda.core.contracts.Amount

/** Helpers for creating amounts of fixed token definitions. */

// Sterling.
val GBP = FiatCurrency.getInstance("GBP")

fun GBP(amount: Int): Amount<FiatCurrency> = amount(amount, GBP)
fun GBP(amount: Long): Amount<FiatCurrency> = amount(amount, GBP)
fun GBP(amount: Double): Amount<FiatCurrency> = amount(amount, GBP)
val Int.GBP: Amount<FiatCurrency> get() = GBP(this)
val Long.GBP: Amount<FiatCurrency> get() = GBP(this)
val Double.GBP: Amount<FiatCurrency> get() = GBP(this)

// US Dollar.
val USD = FiatCurrency.getInstance("USD")

fun USD(amount: Int): Amount<FiatCurrency> = amount(amount, USD)
fun USD(amount: Long): Amount<FiatCurrency> = amount(amount, USD)
fun USD(amount: Double): Amount<FiatCurrency> = amount(amount, USD)
val Int.USD: Amount<FiatCurrency> get() = USD(this)
val Long.USD: Amount<FiatCurrency> get() = USD(this)
val Double.USD: Amount<FiatCurrency> get() = USD(this)

// Euro.
val EUR = FiatCurrency.getInstance("EUR")

fun EUR(amount: Int): Amount<FiatCurrency> = amount(amount, EUR)
fun EUR(amount: Long): Amount<FiatCurrency> = amount(amount, EUR)
fun EUR(amount: Double): Amount<FiatCurrency> = amount(amount, EUR)
val Int.EUR: Amount<FiatCurrency> get() = EUR(this)
val Long.EUR: Amount<FiatCurrency> get() = EUR(this)
val Double.EUR: Amount<FiatCurrency> get() = EUR(this)

// Swissie.
val CHF = FiatCurrency.getInstance("CHF")

fun CHF(amount: Int): Amount<FiatCurrency> = amount(amount, CHF)
fun CHF(amount: Long): Amount<FiatCurrency> = amount(amount, CHF)
fun CHF(amount: Double): Amount<FiatCurrency> = amount(amount, CHF)
val Int.CHF: Amount<FiatCurrency> get() = CHF(this)
val Long.CHF: Amount<FiatCurrency> get() = CHF(this)
val Double.CHF: Amount<FiatCurrency> get() = CHF(this)

// Japanese Yen.
val JPY = FiatCurrency.getInstance("JPY")

fun JPY(amount: Int): Amount<FiatCurrency> = amount(amount, JPY)
fun JPY(amount: Long): Amount<FiatCurrency> = amount(amount, JPY)
fun JPY(amount: Double): Amount<FiatCurrency> = amount(amount, JPY)
val Int.JPY: Amount<FiatCurrency> get() = JPY(this)
val Long.JPY: Amount<FiatCurrency> get() = JPY(this)
val Double.JPY: Amount<FiatCurrency> get() = JPY(this)

// Canadian Dollar.
val CAD = FiatCurrency.getInstance("CAD")

fun CAD(amount: Int): Amount<FiatCurrency> = amount(amount, CAD)
fun CAD(amount: Long): Amount<FiatCurrency> = amount(amount, CAD)
fun CAD(amount: Double): Amount<FiatCurrency> = amount(amount, CAD)
val Int.CAD: Amount<FiatCurrency> get() = CAD(this)
val Long.CAD: Amount<FiatCurrency> get() = CAD(this)
val Double.CAD: Amount<FiatCurrency> get() = CAD(this)

// Australian Dollar.
val AUD = FiatCurrency.getInstance("AUD")

fun AUD(amount: Int): Amount<FiatCurrency> = amount(amount, AUD)
fun AUD(amount: Long): Amount<FiatCurrency> = amount(amount, AUD)
fun AUD(amount: Double): Amount<FiatCurrency> = amount(amount, AUD)
val Int.AUD: Amount<FiatCurrency> get() = AUD(this)
val Long.AUD: Amount<FiatCurrency> get() = AUD(this)
val Double.AUD: Amount<FiatCurrency> get() = AUD(this)

// New Zealand Dollar.
val NZD = FiatCurrency.getInstance("NZD")

fun NZD(amount: Int): Amount<FiatCurrency> = amount(amount, NZD)
fun NZD(amount: Long): Amount<FiatCurrency> = amount(amount, NZD)
fun NZD(amount: Double): Amount<FiatCurrency> = amount(amount, NZD)
val Int.NZD: Amount<FiatCurrency> get() = NZD(this)
val Long.NZD: Amount<FiatCurrency> get() = NZD(this)
val Double.NZD: Amount<FiatCurrency> get() = NZD(this)

// Bitcoin.
val BTC = DigitalCurrency.getInstance("BTC")

fun BTC(amount: Int): Amount<DigitalCurrency> = amount(amount, BTC)
fun BTC(amount: Long): Amount<DigitalCurrency> = amount(amount, BTC)
fun BTC(amount: Double): Amount<DigitalCurrency> = amount(amount, BTC)
val Int.BTC: Amount<DigitalCurrency> get() = BTC(this)
val Long.BTC: Amount<DigitalCurrency> get() = BTC(this)
val Double.BTC: Amount<DigitalCurrency> get() = BTC(this)

val XRP = DigitalCurrency.getInstance("XRP")

fun XRP(amount: Int): Amount<DigitalCurrency> = amount(amount, XRP)
fun XRP(amount: Long): Amount<DigitalCurrency> = amount(amount, XRP)
fun XRP(amount: Double): Amount<DigitalCurrency> = amount(amount, XRP)
val Int.XRP: Amount<DigitalCurrency> get() = XRP(this)
val Long.XRP: Amount<DigitalCurrency> get() = XRP(this)
val Double.XRP: Amount<DigitalCurrency> get() = XRP(this)