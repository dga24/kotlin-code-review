package schwarz.jobs.interview.coupon.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class CouponTest {

    @Test
    fun `should create coupon with valid values`() {
        val coupon = Coupon.create(
            code = "SUMMER2024",
            discount = BigDecimal("10.00"),
            minBasketValue = BigDecimal("50.00")
        )

        assertEquals("SUMMER2024", coupon.code)
        assertEquals(BigDecimal("10.00"), coupon.discount)
        assertEquals(BigDecimal("50.00"), coupon.minBasketValue)
    }

    @Test
    fun `should fail when discount is negative`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coupon.create(
                code = "INVALID",
                discount = BigDecimal("-10.00"),
                minBasketValue = BigDecimal("50.00")
            )
        }

        assertEquals("Discount must be positive", exception.message)
    }

    @Test
    fun `should fail when minBasketValue is less than discount`() {
        val exception = assertThrows<IllegalArgumentException> {
            Coupon.create(
                code = "INVALID",
                discount = BigDecimal("100.00"),
                minBasketValue = BigDecimal("50.00")
            )
        }

        assertEquals("MinBasketValue must be greater than discount", exception.message)
    }

    @Test
    fun `should rehydrate coupon without validation`() {
        val coupon = Coupon.rehydrate(
            code = "REHYDRATED",
            discount = BigDecimal("10.00"),
            minBasketValue = BigDecimal("50.00")
        )

        assertEquals("REHYDRATED", coupon.code)
    }
}
