package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import schwarz.jobs.interview.coupon.application.port.out.BasketCouponRepository
import java.math.BigDecimal

@Repository
class BasketCouponRepositoryAdapter(
    private val basketCouponRepositoryJpa: BasketCouponRepositoryJpa,
    private val entityManager: EntityManager,
) : BasketCouponRepository {

    @Transactional
    override fun save(basketId: String, couponCode: String, discount: BigDecimal) {
        basketCouponRepositoryJpa.insertByCouponCode(
            basketId = basketId.toLong(),
            couponCode = couponCode,
            discount = discount
        )
        entityManager.flush()
        entityManager.clear()
    }
}
