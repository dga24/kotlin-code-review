package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "coupon")
data class CouponEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "code")
    val code: String,

    @Column(name = "discount", precision = 10, scale = 2)
    val discount: BigDecimal,

    @Column(name = "min_basket_value", precision = 10, scale = 2)
    val minBasketValue: BigDecimal,
)
