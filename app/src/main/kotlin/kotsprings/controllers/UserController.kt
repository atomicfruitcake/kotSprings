package kotsprings.controllers

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
    fun putUser(@RequestBody body: UserModel): UserModel {
        println(body)
        return userService.createUser(body)
    }

    @PostMapping("/user/{id}")
    fun postUser(@RequestBody body: UserModel): UserModel {
        return userService.updateUser(body)
    }
}