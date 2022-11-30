package kotsprings.services

import kotsprings.enums.LoanEventType
import kotsprings.repositories.BookLoanEventRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BookLoanEventService(private val repository: BookLoanEventRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun recordLoadEvent(eventType: LoanEventType, userId: Long, bookId: Long) {
        return
    }
}