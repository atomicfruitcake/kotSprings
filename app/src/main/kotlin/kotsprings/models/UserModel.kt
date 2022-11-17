package kotsprings.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserModel (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(name = "userName", unique = true, nullable = false)
    val userName: String,
    @Column(name = "firstName", nullable = false)
    val firstName: String,
    @Column(name = "lastName", nullable = false)
    val lastName: String,
    @Column(name = "emailAddress", nullable = false)
    val emailAddress: String,
)
