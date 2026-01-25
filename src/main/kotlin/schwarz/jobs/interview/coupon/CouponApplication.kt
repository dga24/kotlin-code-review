package schwarz.jobs.interview.coupon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
class CouponApplication

fun main(args: Array<String>) {
	runApplication<CouponApplication>(*args)
}
