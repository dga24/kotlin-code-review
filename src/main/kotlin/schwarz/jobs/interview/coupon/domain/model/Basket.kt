package schwarz.jobs.interview.coupon.domain.model

import java.math.BigDecimal

class Basket private constructor(
    val id: BasketId,
    private var amount: BigDecimal,
    private var appliedCoupons: MutableList<Coupon> = mutableListOf(),
) {

    fun amount() = amount
    fun appliedCoupons() = appliedCoupons.toList()
    fun priceAfterCoupons() = amount - appliedCoupons.sumOf { it.discount }

    companion object {

        fun rehydrate(
            id: BasketId,
            amount: BigDecimal,
            appliedCoupons: List<Coupon>,
        ): Basket {
            Basket(
                id = id,
                amount = amount,
            ).apply {
                appliedCoupons.forEach {
                    applyDiscount(it)
                }
                return this
            }
        }
    }

    fun applyDiscount(coupon: Coupon) {
        require(coupon.code !in appliedCoupons().map { it.code }) { "Coupon ${coupon.code} already applied to this basket" }
        require(coupon.minBasketValue <= priceAfterCoupons()) { "Coupon ${coupon.code} cannot be applied to this basket" }
        appliedCoupons.add(coupon)
    }
}
