package schwarz.jobs.interview.coupon.application.port.out

import schwarz.jobs.interview.coupon.domain.model.Basket

interface BasketRepository {

    fun findById(id: String): Basket?

    fun update(basket: Basket): Basket
}
