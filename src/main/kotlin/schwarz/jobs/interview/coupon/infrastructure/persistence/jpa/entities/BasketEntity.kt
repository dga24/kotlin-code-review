package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "basket")
data class BasketEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(precision = 10, scale = 2)
    val amount: BigDecimal,

    @OneToMany(mappedBy = "basketEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val appliedCoupons: MutableList<BasketCouponEntity> = mutableListOf()
)
