package schwarz.jobs.interview.coupon.infrastructure.web.mapper

import schwarz.jobs.interview.coupon.application.command.CreateCouponCommand
import schwarz.jobs.interview.coupon.infrastructure.web.dto.CreateCouponRequest

object Mapper {

    fun CreateCouponRequest.toCreateCouponCommand(): CreateCouponCommand =
        CreateCouponCommand(
            code = this.code,
            discount = this.discount,
            minBasketValue = this.minBasketValue,
        )
}
