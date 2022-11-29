package kotsprings.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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

    @LastModifiedDate
    @Column(name = "lastModified", nullable = true, updatable = false)
    private var lastModified: LocalDate,

    @CreatedDate
    @Column(name = "created", nullable = true)
    private var created: LocalDate,
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
