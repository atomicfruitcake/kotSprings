package kotsprings.repositories

import kotsprings.models.BookLoanEventModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookLoanEventRepository : JpaRepository<BookLoanEventModel, Long>