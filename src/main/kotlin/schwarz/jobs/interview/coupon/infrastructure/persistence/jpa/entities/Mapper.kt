package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities

import schwarz.jobs.interview.coupon.domain.model.Coupon

fun Coupon.toEntity(): CouponEntity =
    CouponEntity(code = code, discount = discount, minBasketValue = minBasketValue)
