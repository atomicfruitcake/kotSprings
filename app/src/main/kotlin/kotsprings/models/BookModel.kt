package kotsprings.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "books")
data class BookModel (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "author", nullable = true)
    var author: String,

    @Column(name = "yearPublished", nullable = true)
    var yearPublished: Int?,

    @Column(name = "borrowerUserId", nullable = true)
    var borrowerUserId: Long?,

    @LastModifiedDate
    @Column(name = "lastModified", nullable = true, updatable = false)
    var lastModified: LocalDate,

    @CreatedDate
    @Column(name = "created", nullable = true)
    var created: LocalDate,
) {

    @PrePersist
    fun onCreate() {
        this.created = LocalDate.now()
    }

    @PreUpdate
    fun onUpdate() {
        this.lastModified = LocalDate.now()
    }
}