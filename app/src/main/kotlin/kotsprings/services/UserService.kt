package kotsprings.services

import kotsprings.exceptions.InvalidEmailException
import kotsprings.exceptions.InvalidUserNameException
import kotsprings.exceptions.UserNotFoundException
import kotsprings.models.UserModel
import kotsprings.repositories.UserRepository
import kotsprings.utils.emailAddressIsValid
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

/**
 * User service
 *
 * @property repository
 * @constructor Create empty User service
 */
@Service
class UserService(private val repository: UserRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Get a user
     *
     * @param userId: Long - Unique ID of user
     * @return User from DB if found
     * @throws UserNotFoundException - Thrown when the userId does not return a match
     */
    fun getUser(userId: Long): UserModel? {
        logger.info("Finding user with userId $userId")
        return repository.findById(userId).orElseThrow {
            UserNotFoundException("No user with id=$userId found")
        }
    }

    /**
     * Get all users currently stored
     *
     * @param sortKey String? - Optional sort key to sort the users by. Default is 'id'
     * @return All users currently stored
     */
    fun getAllUsers(
        sortKey: String? = "id"
    ): MutableIterable<UserModel> {
        logger.info("Getting all users")
        return repository.findAll(Sort.by(sortKey ?: "id"))
    }

    /**
     * Create a new user
     *
     * @param user User Object from JSON body to be inserted
     * @return User Object that has been inserted
     * @throws InvalidUserNameException Thrown when the selected username is already taken
     * @throws InvalidEmailException Thrown when the user's email address is not a valid email
     */
    fun createUser(user: UserModel): UserModel {
        logger.info("Creating new user with username ${user.userName}")
        if (!emailAddressIsValid(user.emailAddress)) {
            throw InvalidEmailException("Email Address ${user.emailAddress} is not a valid email")
        }
        val matchingUsernames = repository.findByUserName(user.userName)
        if (matchingUsernames != null) {
            if (matchingUsernames.isNotEmpty()) {
                throw InvalidUserNameException("User with username ${user.userName} already exists")
            }
        }

        return repository.save(user)
    }

    /**
     * Update a user that already exists
     *
     * @param userId: Long - Unique ID of user
     * @param user: User Object with updated to be applied
     * @return User that has been updated
     * @throws InvalidUserNameException Thrown when the selected username is already taken
     * @throws InvalidEmailException Thrown when the user's email address is not a valid email
     */
    fun updateUser(userId: Long, user: UserModel): UserModel {

        if (!emailAddressIsValid(user.emailAddress)) {
            throw InvalidEmailException("Email Address ${user.emailAddress} is not a valid email")
        }

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

    /**
     * Update a user's email address
     *
     * @param userId: Long - Unique ID of user
     * @param newEmailAddress: String - newEmailAddress to be set for user
     * @return User object of user with updated email address
     */
    fun updateUserEmail(userId: Long, newEmailAddress: String): UserModel {
        logger.info("Update user userId=$userId's email address to $newEmailAddress")
        if (!emailAddressIsValid(newEmailAddress)) {
            throw InvalidEmailException("Email Address $newEmailAddress is not a valid email")
        }
        val curUser: UserModel = repository.findById(userId).orElseThrow {
            UserNotFoundException("No user with userId=$userId found")
        }
        curUser.emailAddress = newEmailAddress
        return repository.save(curUser)
    }

    /**
     * Delete a user from the system
     *
     * @param userId: userId Long - Unique ID of user
     */
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