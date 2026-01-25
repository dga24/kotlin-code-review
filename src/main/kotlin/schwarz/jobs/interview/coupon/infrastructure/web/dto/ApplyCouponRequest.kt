package schwarz.jobs.interview.coupon.infrastructure.web.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ApplyCouponRequest(

    @field:Schema(required = true)
    val code: String,
)
