package kotsprings.services

import kotsprings.exceptions.InvalidEmailException
import kotsprings.exceptions.InvalidUserNameException
import kotsprings.exceptions.UserNotFoundException
import kotsprings.models.UserModel
import kotsprings.repositories.UserRepository
import kotsprings.utils.emailAddressIsValid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun getUser(userId: Long): UserModel? {
        return repository.findById(userId).orElseThrow {
            UserNotFoundException("No user with id=$userId found")
        }
    }

    fun createUser(user: UserModel): UserModel {
        logger.info("Creating new user with username ${user.userName}")
        val matchingUsernames = repository.findByUserName(user.userName)
        if (matchingUsernames != null) {
            if (matchingUsernames.isNotEmpty()) {
                throw InvalidUserNameException("User with username ${user.userName} already exists")
            }
        }
        return repository.save(user)
    }

    fun updateUser(userId: Long, user: UserModel): UserModel {
        val curUser: UserModel = repository.findById(userId).orElseThrow {
            UserNotFoundException("No user with userId=$userId found")
        }
        val matchingUsernames = repository.findByUserName(user.userName)
        if (matchingUsernames != null) {
            if (matchingUsernames.isNotEmpty()) {
                throw InvalidUserNameException("User with username ${user.userName} already exists")
            }
        }

        curUser.userName = user.userName
        curUser.firstName = user.firstName
        curUser.lastName = user.lastName
        curUser.emailAddress = user.emailAddress
        curUser.dateOfBirth = user.dateOfBirth

        return repository.save(curUser)
    }

    fun updateUserEmail(userId: Long, newEmailAddress: String): UserModel {
        logger.info("Update user userId=$userId's email address to $newEmailAddress")
        val curUser: UserModel = repository.findById(userId).orElseThrow {
            UserNotFoundException("No user with userId=$userId found")
        }
        if (emailAddressIsValid(newEmailAddress)) {
            curUser.emailAddress = newEmailAddress
            return repository.save(curUser)
        }
        throw InvalidEmailException("Email Address $newEmailAddress is not a valid email")
    }

    fun deleteUser(userId: Long) {
        logger.info("Deleting user userId=$userId")
        try {
            repository.deleteById(userId)
            logger.info("Successfully deleted user $userId")
        } catch (ex: Exception) {
            logger.warn("Cannot delete user $userId as no user exists with that id")
        }

    }
}