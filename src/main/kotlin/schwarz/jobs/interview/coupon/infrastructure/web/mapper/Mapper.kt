package schwarz.jobs.interview.coupon.infrastructure.web.mapper

import schwarz.jobs.interview.coupon.application.command.CreateCouponCommand
import schwarz.jobs.interview.coupon.domain.model.Basket
import schwarz.jobs.interview.coupon.domain.model.Coupon
import schwarz.jobs.interview.coupon.infrastructure.web.dto.BasketResponse
import schwarz.jobs.interview.coupon.infrastructure.web.dto.CouponResponse
import schwarz.jobs.interview.coupon.infrastructure.web.dto.CreateCouponRequest

object Mapper {

    fun CreateCouponRequest.toCreateCouponCommand(): CreateCouponCommand =
        CreateCouponCommand(
            code = this.code,
            discount = this.discount,
            minBasketValue = this.minBasketValue,
        )

    fun Basket.toResponse(): BasketResponse =
        BasketResponse(
            id = this.id.value,
            amount = this.amount(),
            appliedCoupons = this.appliedCoupons().map { it.toResponse() },
            priceAfterCoupons = this.priceAfterCoupons(),
        )

    fun Coupon.toResponse(): CouponResponse =
        CouponResponse(
            code = this.code,
            discount = this.discount,
            minBasketValue = this.minBasketValue,
        )
}
