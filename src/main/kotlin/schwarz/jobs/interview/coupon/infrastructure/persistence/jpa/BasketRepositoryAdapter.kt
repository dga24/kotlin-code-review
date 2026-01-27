package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa

import org.springframework.stereotype.Repository
import schwarz.jobs.interview.coupon.application.port.out.BasketRepository
import schwarz.jobs.interview.coupon.domain.model.Basket
import schwarz.jobs.interview.coupon.domain.model.BasketId
import schwarz.jobs.interview.coupon.domain.model.Coupon

@Repository
class BasketRepositoryAdapter(
    private val basketRepositoryJpa: BasketRepositoryJpa,
) : BasketRepository {

    override fun findById(id: String): Basket? {
        val entity = basketRepositoryJpa.findById(id.toLong()).orElse(null) ?: return null

        return Basket.rehydrate(
            id = BasketId(value = entity.id!!),
            amount = entity.amount,
            appliedCoupons = entity.appliedCoupons.map { basketCoupon ->
                Coupon.rehydrate(
                    code = basketCoupon.coupon.code,
                    discount = basketCoupon.discountApplied,
                    minBasketValue = basketCoupon.coupon.minBasketValue,
                )
            },
        )
    }
}
