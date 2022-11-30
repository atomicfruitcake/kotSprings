package kotsprings.models

import kotsprings.enums.LoanEventType
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "bookLoanEvent")
data class BookLoanEventModel(

    val id: Long = 0L,
    val created: LocalDate = LocalDate.now(),
    val lastModified: LocalDate = LocalDate.now(),


    @Column(name = "eventType", nullable = false)
    @Enumerated
    var eventType: LoanEventType,

    @Column(name = "borrowerUserId", nullable = true)
    var borrowerUserId: Long,

    @Column(name = "bookId", nullable = false)
    var bookId: Long

) : AbstractEntityModel(id, created, lastModified)