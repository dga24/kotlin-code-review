package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities.BasketCouponEntity
import java.math.BigDecimal

interface BasketCouponRepositoryJpa : JpaRepository<BasketCouponEntity, Long> {

    @Modifying
    @Query(
        value = """
            INSERT INTO basket_coupon (basket_id, coupon_id, discount_applied)
            SELECT :basketId, c.id, :discount
            FROM coupon c
            WHERE c.code = :couponCode
        """,
        nativeQuery = true
    )
    fun insertByCouponCode(basketId: Long, couponCode: String, discount: BigDecimal)
}
