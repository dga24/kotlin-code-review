package schwarz.jobs.interview.coupon

import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
//@SpringBootApplication
class CouponApplication

fun main(args: Array<String>) {
	runApplication<CouponApplication>(*args)
}
