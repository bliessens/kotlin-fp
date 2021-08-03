package pricing.dsl

//@DslMarker
//annotation class PricingDsl

enum class Country {
    ES, DE
}

fun pricing(country: Country, init: PricingConfig.() -> Unit): PricingConfig {
    return PricingConfig.forCountry(country).apply(init)
}

object add
object set

//@PricingDsl
abstract class PricingConfig {

    lateinit var basePrice: Price
    val taxes = mutableListOf<Tax>()

    abstract val vat: Tax
    open val tourism = Tax(0.0)

    fun total(): Price {
        return basePrice + taxes.map { it -> basePrice * it }
            .fold(Price.ZERO, { accumulatedSum, tax -> accumulatedSum + tax })
    }

    infix fun set.base(price: Int) = base(price.toDouble())
    infix fun set.base(price: Double) = Price(price).also { basePrice = it }

    infix fun add.tax(tax: Tax): Unit {
        taxes.add(tax)
    }

    companion object {
        fun forCountry(country: Country): PricingConfig {
            when (country) {
                Country.ES -> return Spain()
                Country.DE -> return Germany()
            }
        }
    }
}

data class Tax(val percentage: Double)
data class Price(val price: Double) {

    operator fun times(tax: Tax): Price {
        return Price(price * tax.percentage)
    }

    operator fun plus(price: Price): Price {
        return Price(this.price + price.price)
    }

    companion object {
        val ZERO = Price(0.0)
    }
}

class Spain : PricingConfig() {
    override val vat: Tax
        get() = Tax(0.21)
}

class Germany : PricingConfig() {
    override val vat: Tax
        get() = Tax(0.19)

    override val tourism: Tax
        get() = Tax(0.05)
}


fun main(args: Array<String>) {
    val spain = pricing(Country.ES) {
        set base 100
        add tax vat
        add tax tourism
    }

    println(spain.total())
    val germany = pricing(Country.DE) {
        set base 100
        add tax vat
        add tax tourism
    }

    println(germany.total())
}
