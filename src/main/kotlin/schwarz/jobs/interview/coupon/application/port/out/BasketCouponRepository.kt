package schwarz.jobs.interview.coupon.application.port.out

import java.math.BigDecimal

interface BasketCouponRepository {

    fun save(basketId: String, couponCode: String, discount: BigDecimal)
}
