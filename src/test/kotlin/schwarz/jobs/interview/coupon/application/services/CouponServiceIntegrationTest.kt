package schwarz.jobs.interview.coupon.application.services

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import schwarz.jobs.interview.coupon.application.command.ApplyCouponCommand
import schwarz.jobs.interview.coupon.application.command.CreateCouponCommand
import schwarz.jobs.interview.coupon.application.port.out.BasketRepository
import java.math.BigDecimal

@SpringBootTest
@Transactional
class CouponServiceIntegrationTest {

    @Autowired
    private lateinit var couponService: CouponService

    @Autowired
    private lateinit var basketRepository: BasketRepository

    @Test
    fun `should get existing coupon by code`() {
        val coupon = couponService.get("TEST1")

        assertNotNull(coupon)
        assertEquals("TEST1", coupon.code)
        assertEquals(BigDecimal("10.00"), coupon.discount)
        assertEquals(BigDecimal("50.00"), coupon.minBasketValue)
    }

    @Test
    fun `should throw exception when coupon not found`() {
        val exception = assertThrows<NoSuchElementException> {
            couponService.get("NONEXISTENT")
        }

        assertEquals("Coupon code NONEXISTENT not found", exception.message)
    }

    @Test
    fun `should get multiple coupons by codes`() {
        val coupons = couponService.get(listOf("TEST1", "TEST2"))

        assertEquals(2, coupons.size)
        assertEquals("TEST1", coupons[0].code)
        assertEquals("TEST2", coupons[1].code)
    }

    @Test
    fun `should create new coupon`() {
        val command = CreateCouponCommand(
            code = "NEWCOUPON",
            discount = BigDecimal("25.00"),
            minBasketValue = BigDecimal("100.00")
        )

        val code = couponService.create(command)

        assertEquals("NEWCOUPON", code)

        val savedCoupon = couponService.get("NEWCOUPON")
        assertEquals(BigDecimal("25.00"), savedCoupon.discount)
        assertEquals(BigDecimal("100.00"), savedCoupon.minBasketValue)
    }

    @Test
    fun `should apply coupon to basket`() {
        val command = ApplyCouponCommand(
            basketId = "1",
            code = "TEST1"
        )

        val basket = couponService.apply(command)

        assertNotNull(basket)
        assertEquals(1, basket.appliedCoupons().size)
        assertEquals("TEST1", basket.appliedCoupons()[0].code)
        assertEquals(BigDecimal("65.00"), basket.priceAfterCoupons())
    }

    @Test
    fun `should apply multiple coupons to basket`() {
        val command = ApplyCouponCommand(
            basketId = "1",
            code = "TEST1"
        )

        val basket = couponService.apply(command)

        assertNotNull(basket)
        assertEquals(1, basket.appliedCoupons().size)
        assertEquals("TEST1", basket.appliedCoupons()[0].code)
        assertEquals(BigDecimal("65.00"), basket.priceAfterCoupons())
        assertEquals(BigDecimal("65.00"), basketRepository.findById("1")!!.priceAfterCoupons())

        val command2 = ApplyCouponCommand(
            basketId = "1",
            code = "TEST4"
        )

        val basketAfterSecondCoupon = couponService.apply(command2)
        assertEquals(2, basketAfterSecondCoupon.appliedCoupons().size)
        assertEquals("TEST4", basketAfterSecondCoupon.appliedCoupons()[1].code)
        assertEquals(BigDecimal("60.00"), basketAfterSecondCoupon.priceAfterCoupons())
        assertEquals(BigDecimal("60.00"), basketRepository.findById("1")!!.priceAfterCoupons())
    }
}
