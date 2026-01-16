package schwarz.jobs.interview.coupon.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import schwarz.jobs.interview.coupon.core.domain.Coupon
import java.util.Optional

@Repository
interface CouponRepository : JpaRepository<Coupon, Long> {

    fun findByCode(code: String): Optional<Coupon>
}