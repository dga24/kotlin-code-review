package schwarz.jobs.interview.coupon.web.dto

import java.math.BigDecimal

data class CouponDto(

    val discount: BigDecimal,
    val code: String,
    val minBasketValue: BigDecimal,
)
