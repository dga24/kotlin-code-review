package schwarz.jobs.interview.coupon.infrastructure.web

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import schwarz.jobs.interview.coupon.application.command.ApplyCouponCommand
import schwarz.jobs.interview.coupon.application.port.`in`.CouponUseCase
import schwarz.jobs.interview.coupon.infrastructure.web.dto.ApplyCouponRequest
import schwarz.jobs.interview.coupon.infrastructure.web.dto.BasketResponse
import schwarz.jobs.interview.coupon.infrastructure.web.mapper.Mapper.toResponse

@RestController
@RequestMapping("/api/v2/basket")
class BasketController(
    private val couponUseCase: CouponUseCase,
) {

    @PostMapping("/{basketId}/applyCoupon")
    fun apply(
        @PathVariable basketId: String,
        @RequestBody @Valid request: ApplyCouponRequest,
    ): ResponseEntity<BasketResponse> = couponUseCase.apply(
        command =
            ApplyCouponCommand(basketId = basketId , code = request.code)
    ).toResponse().run { ResponseEntity.ok(this) }
}
