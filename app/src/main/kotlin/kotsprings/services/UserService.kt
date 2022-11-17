package kotsprings.services
import kotsprings.models.UserModel
import kotsprings.repositories.UserRepository
import org.apache.catalina.User
import org.springframework.stereotype.Service
import java.util.*
@Service
class UserService(private val repository: UserRepository) {

    fun getUser(userId: Long): Optional<UserModel> {
        return repository.findById(userId)
    }

    fun createUser(user: UserModel): UserModel {
        return user
    }

    fun updateUser(user: UserModel): UserModel {
        return user
    }
}