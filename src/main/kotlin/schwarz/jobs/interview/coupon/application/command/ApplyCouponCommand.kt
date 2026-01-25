package schwarz.jobs.interview.coupon.application.command

data class ApplyCouponCommand (
    val basketId: String,
    val code: String,
)
