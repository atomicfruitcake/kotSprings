package kotsprings.controllers

import com.google.gson.Gson
import kotsprings.models.UserModel
import kotsprings.services.UserService

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: Long): Optional<UserModel> {
        return userService.getUser(id)
    }

    @PutMapping("/user")
    fun putUser(@RequestBody user: UserModel): UserModel {
        println(user)
        return userService.createUser(user)
    }

    @PostMapping("/user/{id}")
    fun postUser(@PathVariable id: Long, @RequestBody user: UserModel): UserModel {
        return userService.updateUser(id, user)
    }

    @PostMapping("/user/email/{id}")
    fun postUserEmail(@PathVariable id: Long, @RequestBody body: String): UserModel {
        data class PostUserEmailBody(val emailAddress: String)

        val postUserEmailBody = Gson().fromJson(body, PostUserEmailBody::class.java)

        return userService.updateUserEmail(id, newEmailAddress = postUserEmailBody.emailAddress)
    }
}