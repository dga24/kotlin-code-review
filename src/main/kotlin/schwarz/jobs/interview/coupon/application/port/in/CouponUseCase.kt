package schwarz.jobs.interview.coupon.application.port.`in`

import schwarz.jobs.interview.coupon.application.command.ApplyCouponCommand
import schwarz.jobs.interview.coupon.application.command.CreateCouponCommand
import schwarz.jobs.interview.coupon.domain.model.Basket
import schwarz.jobs.interview.coupon.domain.model.Coupon

interface CouponUseCase {

    fun get(code: String): Coupon?
    fun get(codes: List<String>): List<Coupon>

    fun create(command: CreateCouponCommand): String
    fun apply(command: ApplyCouponCommand): Basket
}
