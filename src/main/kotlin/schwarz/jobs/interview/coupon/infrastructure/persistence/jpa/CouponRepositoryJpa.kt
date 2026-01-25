package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities.CouponEntity

interface CouponRepositoryJpa : JpaRepository<CouponEntity, Long> {

    fun findByCode(code: String): CouponEntity?

    fun findByCodeIn(codes: List<String>): List<CouponEntity>

}
