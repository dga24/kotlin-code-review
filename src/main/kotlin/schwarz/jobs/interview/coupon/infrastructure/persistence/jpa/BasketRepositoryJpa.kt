package schwarz.jobs.interview.coupon.infrastructure.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import schwarz.jobs.interview.coupon.infrastructure.persistence.jpa.entities.BasketEntity

interface BasketRepositoryJpa : JpaRepository<BasketEntity, Long>
