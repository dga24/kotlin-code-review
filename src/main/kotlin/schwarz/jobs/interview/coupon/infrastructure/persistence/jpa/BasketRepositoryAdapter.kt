package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import schwarz.jobs.interview.coupon.application.port.out.BasketRepository
import schwarz.jobs.interview.coupon.domain.model.Basket
import schwarz.jobs.interview.coupon.domain.model.BasketId
import schwarz.jobs.interview.coupon.domain.model.Coupon
import schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities.BasketCouponEntity

@Repository
class BasketRepositoryAdapter(
    private val basketRepositoryJpa: BasketRepositoryJpa,
    private val couponRepositoryJpa: CouponRepositoryJpa,
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

    override fun save(basket: Basket): Basket {
        val basketEntity = basketRepositoryJpa.findById(basket.id.value)
            .orElseThrow { NoSuchElementException("Basket ${basket.id.value} not found") }

        val couponCodes = basket.appliedCoupons().map { it.code }

        val couponEntitiesMap = couponRepositoryJpa.findByCodeIn(couponCodes)
            .associateBy { it.code }

        // Remove coupons that are no longer in the basket
        basketEntity.appliedCoupons.removeIf { existing ->
            basket.appliedCoupons().none { it.code == existing.coupon.code }
        }

        // Add new coupons that aren't already in the basket
        basket.appliedCoupons().forEach { coupon ->
            val alreadyExists = basketEntity.appliedCoupons.any { it.coupon.code == coupon.code }
            if (!alreadyExists) {
                val couponEntity = couponEntitiesMap[coupon.code]
                    ?: throw NoSuchElementException("Coupon ${coupon.code} not found")

                val basketCouponEntity = BasketCouponEntity(
                    basket = basketEntity,
                    coupon = couponEntity,
                    discountApplied = coupon.discount
                )
                basketEntity.appliedCoupons.add(basketCouponEntity)
            }
        }

        val savedEntity = basketRepositoryJpa.save(basketEntity)

        return Basket.rehydrate(
            id = BasketId(value = savedEntity.id!!),
            amount = savedEntity.amount,
            appliedCoupons = savedEntity.appliedCoupons.map { basketCoupon ->
                Coupon.rehydrate(
                    code = basketCoupon.coupon.code,
                    discount = basketCoupon.discountApplied,
                    minBasketValue = basketCoupon.coupon.minBasketValue,
                )
            },
        )
    }
}
