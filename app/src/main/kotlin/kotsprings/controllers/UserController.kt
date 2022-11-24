package kotsprings.controllers

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotsprings.exceptions.InvalidEmailException
import kotsprings.exceptions.InvalidUserNameException
import kotsprings.exceptions.UserNotFoundException
import kotsprings.models.UserModel
import kotsprings.services.UserService
import kotsprings.utils.emailAddressIsValid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

/**
 * User controller
 *
 * @property userService
 * @constructor Create empty User controller
 */
@RestController
class UserController(private val userService: UserService) {

    /**
     * Get a user given a user's ID
     *
     * @param id Long - UserId of user to fetch from URL path
     * @return User's data if user exists
     */
    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: Long): UserModel? {
        try {
            return userService.getUser(id)
        } catch (ex: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        }
    }

    /**
     * Put a new user into the database
     *
     * @param user User to be created from request body
     * @return User that has been successfully created
     */
    @PutMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createUser(@RequestBody user: UserModel): UserModel {
        try {
            return userService.createUser(user)
        } catch (ex: InvalidUserNameException) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, ex.localizedMessage, ex)
        }
    }

    /**
     * Update a user that already exists in the database
     *
     * @param userId Long - UserId of user to fetch from URL path
     * @param user User object from request body
     * @return User that has been updated
     */
    @PostMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody user: UserModel): UserModel {
        try {
            return userService.updateUser(userId, user)
        } catch (ex: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        }
    }

    /**
     * Update the email of a given user
     *
     * @param userId Long - UserId of user to update email for
     * @param body String - Stringified JSON containing 'emailAddress' as key and new email as the value
     * @return User that has had their email address updated
     */
    @PostMapping("/user/email/{userId}")
    fun createUserEmail(@PathVariable userId: Long, @RequestBody body: String): UserModel {
        @Serializable
        class PostUserEmailBody(val emailAddress: String) {
            init {
                require(emailAddress.isNotEmpty()) { "emailAddress cannot be empty" }
            }
        }

        val emailAddress = Json.decodeFromString<PostUserEmailBody>(body).emailAddress

        try {
            return userService.updateUserEmail(userId, emailAddress)
        } catch (ex: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        } catch (ex: InvalidEmailException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.localizedMessage, ex)
        }
    }
}