package schwarz.jobs.interview.coupon.application.command

import java.math.BigDecimal

data class CreateCouponCommand(
    val code: String,
    val discount: BigDecimal,
    val minBasketValue: BigDecimal
)
