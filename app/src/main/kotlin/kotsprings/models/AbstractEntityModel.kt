package kotsprings.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate
import javax.persistence.*

@MappedSuperclass
abstract class AbstractEntityModel (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0L,

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