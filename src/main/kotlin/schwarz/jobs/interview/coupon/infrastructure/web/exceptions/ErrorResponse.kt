package schwarz.jobs.interview.coupon.infrastructure.web.exceptions

data class ErrorResponse(
    val message: String,
    val errors: List<String>? = null,
)
