package schwarz.jobs.interview.coupon.core.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import schwarz.jobs.interview.coupon.application.port.out.BasketCouponRepository
import schwarz.jobs.interview.coupon.application.port.out.BasketRepository
import schwarz.jobs.interview.coupon.application.port.out.CouponRepository
import schwarz.jobs.interview.coupon.application.services.CouponService
import schwarz.jobs.interview.coupon.domain.model.Coupon
import java.math.BigDecimal

class CouponEntityServiceTest {

    private val couponRepository = mockk<CouponRepository>()
    private val basketRepository = mockk<BasketRepository>()
    private val basketCouponRepository = mockk<BasketCouponRepository>()
    private val couponService = CouponService(couponRepository, basketRepository, basketCouponRepository)

    @Test
    fun `Should get a coupon`() {
        val expectedCoupon = Coupon.rehydrate(
            code = "coupon1",
            discount = BigDecimal("10.00"),
            minBasketValue = BigDecimal("50.00")
        )

        every {
            couponRepository.findByCode("coupon1")
        } returns expectedCoupon

        val coupon = couponService.get("coupon1")

        verify { couponRepository.findByCode("coupon1") }
        assertEquals("coupon1", coupon.code)
        assertEquals(BigDecimal("10.00"), coupon.discount)
    }
}
