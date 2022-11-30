package kotsprings.models

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "bookLoanEvent")
data class BookLoanEventModel (
    val id: Long,
    val created: LocalDate,
    val lastModified: LocalDate,

    @Column(name = "eventType", nullable = false)
    var eventType: String,

    @Column(name = "borrowerUserId", nullable = true)
    var borrowerUserId: Long

) : AbstractEntityModel(id, created, lastModified)