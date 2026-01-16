package schwarz.jobs.interview.coupon.core.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "coupon")
@SequenceGenerator(
    name = "CouponSequenceGenerator",
    sequenceName = "coupon_seq",
    allocationSize = 1000,
)
data class Coupon(

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "CouponSequenceGenerator"
    )
    val id: Long? = null,

    @Column(name = "code")
    val code: String,

    @Column(name = "discount", precision = 10, scale = 2)
    val discount: BigDecimal,

    @Column(name = "minBasketValue", precision = 10, scale = 2)
    val minBasketValue: BigDecimal,
)
