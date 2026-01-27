package schwarz.jobs.interview.coupon.infrastructure.web

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import schwarz.jobs.interview.coupon.application.port.`in`.CouponUseCase
import schwarz.jobs.interview.coupon.domain.model.Coupon
import schwarz.jobs.interview.coupon.infrastructure.web.dto.CreateCouponRequest
import schwarz.jobs.interview.coupon.infrastructure.web.mapper.Mapper.toCreateCouponCommand

@RestController
@RequestMapping("/api/v2/coupon")
class CouponController(
    private val couponUseCase: CouponUseCase,
) {

    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateCouponRequest,
    ): ResponseEntity<String> =
        couponUseCase.create(command = request.toCreateCouponCommand()).run {
            ResponseEntity.ok(this)
        }


    @GetMapping
    fun getCoupons(@RequestParam(required = true) @Valid codes: List<String>): ResponseEntity<List<Coupon>> =
        couponUseCase.get(codes = codes).run {
            ResponseEntity.ok(this)
        }
}

