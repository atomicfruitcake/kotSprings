package kotsprings.services

import kotsprings.exceptions.UserNotFoundException
import kotsprings.models.UserModel
import kotsprings.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val repository: UserRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun getUser(userId: Long): Optional<UserModel> {
        return repository.findById(userId)
    }

    fun createUser(user: UserModel): UserModel {
        logger.info("Creating new user with id=$user.id")
        return repository.save(user)
    }

    fun updateUser(id: Long, user: UserModel): UserModel {
        if (repository.existsById(id)) {
            val curUser: UserModel = repository.findById(id).orElseThrow {
                UserNotFoundException("No user with id=$id found")
            }
            curUser.userName = user.userName
            curUser.firstName = user.firstName
            curUser.lastName = user.lastName
            curUser.emailAddress = user.emailAddress
            curUser.dateOfBirth = user.dateOfBirth

            return repository.save(curUser)
        }
        throw UserNotFoundException("No user with id=$id found")
    }

    fun updateUserEmail(id: Long, newEmailAddress: String): UserModel {
        logger.info("Update user id=$id's email address to $newEmailAddress")
        val curUser: UserModel = repository.findById(id).orElseThrow {
            UserNotFoundException("No user with id=$id found")
        }
        curUser.emailAddress = newEmailAddress
        return repository.save(curUser)
    }
}