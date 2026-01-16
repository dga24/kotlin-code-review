package schwarz.jobs.interview.coupon.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {

    @Bean
    fun customOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info()
                    .title("Coupon")
                    .summary("")
                    .description("")
                    .contact(Contact().name("Schwarz"))
                    .version("1.0")
            )
    }
}