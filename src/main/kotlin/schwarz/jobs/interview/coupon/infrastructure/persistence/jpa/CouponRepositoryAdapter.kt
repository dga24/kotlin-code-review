package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa

import org.springframework.stereotype.Repository
import schwarz.jobs.interview.coupon.application.port.out.CouponRepository
import schwarz.jobs.interview.coupon.domain.model.Coupon
import schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities.toEntity

@Repository
class CouponRepositoryAdapter(
    private val couponRepositoryJpa: CouponRepositoryJpa,
) : CouponRepository {

    override fun findByCode(code: String) = couponRepositoryJpa.findByCode(code)?.let { entity ->
        Coupon.rehydrate(
            code = entity.code,
            discount = entity.discount,
            minBasketValue = entity.minBasketValue,
        )
    }

    override fun findByCodes(codes: List<String>) = couponRepositoryJpa.findByCodeIn(codes = codes).map { entity ->
        Coupon.rehydrate(
            code = entity.code,
            discount = entity.discount,
            minBasketValue = entity.minBasketValue,
        )
    }

    override fun save(coupon: Coupon): Coupon = couponRepositoryJpa.save(coupon.toEntity()).let { entity ->
        Coupon.rehydrate(
            code = entity.code,
            discount = entity.discount,
            minBasketValue = entity.minBasketValue,
        )
    }
}
