package kotsprings.services

import kotsprings.enums.LoanEventType
import kotsprings.models.BookLoanEventModel
import kotsprings.repositories.BookLoanEventRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BookLoanEventService(private val repository: BookLoanEventRepository) {


    private val logger = LoggerFactory.getLogger("BookLoanEventService::Recorder")

    fun recordLoanEvent(eventType: LoanEventType, userId: Long, bookId: Long) {
        logger.debug("Recording loan event :: User $userId has $eventType book $bookId")
        repository.save(
            BookLoanEventModel(eventType = eventType, borrowerUserId = userId, bookId = bookId)
        )

    }
}