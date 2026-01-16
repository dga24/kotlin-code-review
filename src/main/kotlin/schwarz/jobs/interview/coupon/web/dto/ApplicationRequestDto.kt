package schwarz.jobs.interview.coupon.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import schwarz.jobs.interview.coupon.core.services.model.Basket

data class ApplicationRequestDto(

    @field:Schema(
        required = true,
    )
    val code: String,
    @field:Schema(
        required = true,
    )
    val basket: Basket,
)
