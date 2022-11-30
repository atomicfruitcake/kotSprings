package kotsprings.models

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "books")
data class BookModel (
    val id: Long,
    val created: LocalDate,
    val lastModified: LocalDate,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "author", nullable = true)
    var author: String,

    @Column(name = "genre", nullable = true)
    var genre: String,

    @Column(name = "yearPublished", nullable = true)
    var yearPublished: Int?,

    @Column(name = "borrowerUserId", nullable = true)
    var borrowerUserId: Long?,

) : AbstractEntityModel(id, created, lastModified)