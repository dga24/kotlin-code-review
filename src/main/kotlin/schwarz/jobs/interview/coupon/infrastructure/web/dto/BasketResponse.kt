package schwarz.jobs.interview.coupon.infrastructure.web.dto

import java.math.BigDecimal

data class BasketResponse(
    val id: Long,
    val amount: BigDecimal,
    val appliedCoupons: List<CouponResponse>,
    val priceAfterCoupons: BigDecimal,
)
