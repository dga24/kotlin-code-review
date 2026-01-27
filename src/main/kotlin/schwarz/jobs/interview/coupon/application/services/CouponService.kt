package schwarz.jobs.interview.coupon.application.services

import org.springframework.stereotype.Service
import schwarz.jobs.interview.coupon.application.command.ApplyCouponCommand
import schwarz.jobs.interview.coupon.application.command.CreateCouponCommand
import schwarz.jobs.interview.coupon.application.port.`in`.CouponUseCase
import schwarz.jobs.interview.coupon.application.port.out.BasketCouponRepository
import schwarz.jobs.interview.coupon.application.port.out.BasketRepository
import schwarz.jobs.interview.coupon.application.port.out.CouponRepository
import schwarz.jobs.interview.coupon.domain.model.Basket
import schwarz.jobs.interview.coupon.domain.model.Coupon

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val basketRepository: BasketRepository,
    private val basketCouponRepository: BasketCouponRepository,
) : CouponUseCase {

    override fun get(code: String) = couponRepository.findByCode(code = code) ?: throw NoSuchElementException("Coupon code $code not found")

    override fun get(codes: List<String>) = couponRepository.findByCodes(codes = codes).ifEmpty { throw NoSuchElementException("Coupon codes $codes not found") }

    override fun create(command: CreateCouponCommand): String {
        val coupon = Coupon.create(
            code = command.code,
            discount = command.discount,
            minBasketValue = command.minBasketValue,
        )
        return couponRepository.save(coupon).code
    }

    override fun apply(command: ApplyCouponCommand): Basket {
        val basket = basketRepository.findById(id = command.basketId)
            ?: throw NoSuchElementException("Basket ${command.basketId} not found")

        val coupon = get(code = command.code)

        basket.applyDiscount(coupon)

        basketCouponRepository.save(
            basketId = command.basketId,
            couponCode = command.code,
            discount = coupon.discount
        )

        return basket
    }

}
