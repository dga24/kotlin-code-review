package schwarz.jobs.interview.coupon.infrastructure.web.dto

import java.math.BigDecimal

data class CouponResponse(
    val code: String,
    val discount: BigDecimal,
    val minBasketValue: BigDecimal,
)
