package schwarz.jobs.interview.coupon.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class BasketTest {

    @Test
    fun `should apply coupon when basket value is above minimum`() {
        val basket = Basket.rehydrate(
            id = BasketId(1L),
            amount = BigDecimal("100.00"),
            appliedCoupons = emptyList()
        )

        val coupon = Coupon.rehydrate("SUMMER", BigDecimal("10.00"), BigDecimal("50.00"))
        basket.applyDiscount(coupon)

        assertEquals(1, basket.appliedCoupons().size)
        assertEquals(BigDecimal("90.00"), basket.priceAfterCoupons())
    }

    @Test
    fun `should fail to apply coupon when basket value is below minimum`() {
        val basket = Basket.rehydrate(
            id = BasketId(1L),
            amount = BigDecimal("30.00"),
            appliedCoupons = emptyList()
        )

        val coupon = Coupon.rehydrate("EXPENSIVE", BigDecimal("10.00"), BigDecimal("50.00"))

        val exception = assertThrows<IllegalArgumentException> {
            basket.applyDiscount(coupon)
        }

        assertEquals("Coupon EXPENSIVE cannot be applied to this basket", exception.message)
    }

    @Test
    fun `should apply multiple coupons and calculate final price`() {
        val basket = Basket.rehydrate(
            id = BasketId(1L),
            amount = BigDecimal("200.00"),
            appliedCoupons = emptyList()
        )

        val coupon1 = Coupon.rehydrate("FIRST", BigDecimal("20.00"), BigDecimal("100.00"))
        val coupon2 = Coupon.rehydrate("SECOND", BigDecimal("15.00"), BigDecimal("150.00"))

        basket.applyDiscount(coupon1)
        basket.applyDiscount(coupon2)

        assertEquals(2, basket.appliedCoupons().size)
        assertEquals(BigDecimal("165.00"), basket.priceAfterCoupons())
    }

    @Test
    fun `should rehydrate basket with applied coupons`() {
        val coupon1 = Coupon.rehydrate("COUPON1", BigDecimal("10.00"), BigDecimal("50.00"))
        val coupon2 = Coupon.rehydrate("COUPON2", BigDecimal("5.00"), BigDecimal("30.00"))

        val basket = Basket.rehydrate(
            id = BasketId(1L),
            amount = BigDecimal("100.00"),
            appliedCoupons = listOf(coupon1, coupon2)
        )

        assertEquals(2, basket.appliedCoupons().size)
        assertEquals(BigDecimal("85.00"), basket.priceAfterCoupons())
    }
}
