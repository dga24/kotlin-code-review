package schwarz.jobs.interview.coupon

import schwarz.jobs.interview.coupon.core.domain.Coupon
import java.math.BigDecimal

object BaseObject {

    val baseCoupon = Coupon(
        id = 100,
        code = "coupon1",
        discount = BigDecimal("1.0"),
        minBasketValue = BigDecimal("50.0")
    )
}