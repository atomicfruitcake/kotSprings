package kotsprings.models

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserModel (

    val id: Long = 0L,
    val created: LocalDate = LocalDate.now(),
    val lastModified: LocalDate = LocalDate.now(),

    @Column(name = "userName", unique = true, nullable = false)
    var userName: String,

    @Column(name = "firstName", nullable = true)
    var firstName: String,

    @Column(name = "lastName", nullable = true)
    var lastName: String,

    @Column(name = "emailAddress", nullable = true)
    var emailAddress: String,

    @Column(name = "dateOfBirth", nullable = true)
    var dateOfBirth: LocalDate,

) : AbstractEntityModel(id, created, lastModified)

