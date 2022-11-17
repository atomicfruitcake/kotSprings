package kotsprings.repositories

import kotsprings.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<UserModel, Long>

class UserRepository {
}