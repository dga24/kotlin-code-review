package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal

@Entity
@Table(
    name = "basket_coupon",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["basket_id", "coupon_id"])
    ]
)
class BasketCouponEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    val basket: BasketEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    val coupon: CouponEntity,

    @Column(name = "discount_applied", precision = 10, scale = 2, nullable = false)
    val discountApplied: BigDecimal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasketCouponEntity) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    override fun toString(): String = "BasketCouponEntity(id=$id, discountApplied=$discountApplied)"
}

