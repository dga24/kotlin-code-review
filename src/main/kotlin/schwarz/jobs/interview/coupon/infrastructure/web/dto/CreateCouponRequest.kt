package schwarz.jobs.interview.coupon.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class CreateCouponRequest(

    @field:Schema(required = true)
    val discount: BigDecimal,
    @field:Schema(required = true)
    val code: String,
    @field:Schema(required = true)
    val minBasketValue: BigDecimal,
)
