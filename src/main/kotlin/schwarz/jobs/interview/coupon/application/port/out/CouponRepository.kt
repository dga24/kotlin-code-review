package schwarz.jobs.interview.coupon.application.port.out

import schwarz.jobs.interview.coupon.domain.model.Coupon

interface CouponRepository {
    fun findByCode(code: String): Coupon?
    fun findByCodes(codes: List<String>): List<Coupon>

    fun save(coupon: Coupon): Coupon
}
