package schwarz.jobs.interview.coupon.application.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import schwarz.jobs.interview.coupon.application.command.ApplyCouponCommand
import schwarz.jobs.interview.coupon.application.command.CreateCouponCommand
import schwarz.jobs.interview.coupon.application.port.out.BasketRepository
import schwarz.jobs.interview.coupon.application.port.out.CouponRepository
import schwarz.jobs.interview.coupon.domain.model.Basket
import schwarz.jobs.interview.coupon.domain.model.BasketId
import schwarz.jobs.interview.coupon.domain.model.Coupon
import java.math.BigDecimal

class CouponServiceTest {

    private val couponRepository = mockk<CouponRepository>()
    private val basketRepository = mockk<BasketRepository>()
    private val couponService = CouponService(couponRepository, basketRepository)

    @Test
    fun `should get coupon by code`() {
        val expectedCoupon = Coupon.rehydrate("TEST1", BigDecimal("10.00"), BigDecimal("50.00"))

        every { couponRepository.findByCode("TEST1") } returns expectedCoupon

        val result = couponService.get("TEST1")

        assertEquals("TEST1", result.code)
        verify { couponRepository.findByCode("TEST1") }
    }

    @Test
    fun `should throw exception when coupon not found by code`() {
        every { couponRepository.findByCode("INVALID") } returns null

        val exception = assertThrows<NoSuchElementException> {
            couponService.get("INVALID")
        }

        assertEquals("Coupon code INVALID not found", exception.message)
    }

    @Test
    fun `should get multiple coupons by codes`() {
        val coupon1 = Coupon.rehydrate("TEST1", BigDecimal("10.00"), BigDecimal("50.00"))
        val coupon2 = Coupon.rehydrate("TEST2", BigDecimal("15.00"), BigDecimal("100.00"))

        every { couponRepository.findByCodes(listOf("TEST1", "TEST2")) } returns listOf(coupon1, coupon2)

        val result = couponService.get(listOf("TEST1", "TEST2"))

        assertEquals(2, result.size)
        verify { couponRepository.findByCodes(listOf("TEST1", "TEST2")) }
    }

    @Test
    fun `should throw exception when no coupons found by codes`() {
        every { couponRepository.findByCodes(listOf("INVALID1", "INVALID2")) } returns emptyList()

        val exception = assertThrows<NoSuchElementException> {
            couponService.get(listOf("INVALID1", "INVALID2"))
        }

        assertEquals("Coupon codes [INVALID1, INVALID2] not found", exception.message)
    }

    @Test
    fun `should create coupon`() {
        val command = CreateCouponCommand(
            code = "NEWCOUPON",
            discount = BigDecimal("25.00"),
            minBasketValue = BigDecimal("100.00")
        )
        val savedCoupon = Coupon.rehydrate("NEWCOUPON", BigDecimal("25.00"), BigDecimal("100.00"))

        every { couponRepository.save(any()) } returns savedCoupon

        val result = couponService.create(command)

        assertEquals("NEWCOUPON", result)
        verify { couponRepository.save(any()) }
    }

    @Test
    fun `should apply coupon to basket successfully`() {
        val basket = Basket.rehydrate(BasketId(1L), BigDecimal("100.00"), emptyList())
        val coupon = Coupon.rehydrate("TEST1", BigDecimal("10.00"), BigDecimal("50.00"))
        val command = ApplyCouponCommand(basketId = "1", code = "TEST1")

        every { basketRepository.findById("1") } returns basket
        every { couponRepository.findByCode("TEST1") } returns coupon
        every { basketRepository.update(any()) } returns basket

        val result = couponService.apply(command)

        assertEquals(1, result.appliedCoupons().size)
        assertEquals("TEST1", result.appliedCoupons()[0].code)
        verify { basketRepository.findById("1") }
        verify { couponRepository.findByCode("TEST1") }
        verify { basketRepository.update(basket) }
    }

    @Test
    fun `should throw exception when basket not found`() {
        val command = ApplyCouponCommand(basketId = "999", code = "TEST1")

        every { basketRepository.findById("999") } returns null

        val exception = assertThrows<NoSuchElementException> {
            couponService.apply(command)
        }

        assertEquals("Basket 999 not found", exception.message)
    }

    @Test
    fun `should throw exception when coupon cannot be applied to basket`() {
        val basket = Basket.rehydrate(BasketId(1L), BigDecimal("30.00"), emptyList())
        val coupon = Coupon.rehydrate("EXPENSIVE", BigDecimal("10.00"), BigDecimal("50.00"))
        val command = ApplyCouponCommand(basketId = "1", code = "EXPENSIVE")

        every { basketRepository.findById("1") } returns basket
        every { couponRepository.findByCode("EXPENSIVE") } returns coupon

        val exception = assertThrows<IllegalArgumentException> {
            couponService.apply(command)
        }

        assertEquals("Coupon EXPENSIVE cannot be applied to this basket", exception.message)
        verify(exactly = 0) { basketRepository.update(any()) }
    }
}
