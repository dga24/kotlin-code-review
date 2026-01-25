package schwarz.jobs.interview.coupon.domain.model

import java.math.BigDecimal

class Coupon private constructor(
    val code: String,
    val discount: BigDecimal,
    val minBasketValue: BigDecimal,
) {

    companion object {
        fun create(
            code: String,
            discount: BigDecimal,
            minBasketValue: BigDecimal,
        ): Coupon {
            require(discount >= BigDecimal.ZERO) { "Discount must be positive" }
            require(minBasketValue >= BigDecimal.ZERO) { "MinBasketValue must be positive" }
            require(minBasketValue > discount) { "MinBasketValue must be greater than discount" }

            return Coupon(
                code = code,
                discount = discount,
                minBasketValue = minBasketValue,
            )
        }

        fun rehydrate(

            code: String,
            discount: BigDecimal,
            minBasketValue: BigDecimal,
        ): Coupon {
            return Coupon(
                code = code,
                discount = discount,
                minBasketValue = minBasketValue,
            )
        }
    }
}

