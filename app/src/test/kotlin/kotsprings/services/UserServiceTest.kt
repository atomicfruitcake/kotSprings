package kotsprings.services

import java.time.LocalDate
import kotsprings.exceptions.InvalidEmailException
import kotsprings.models.UserModel
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    private val testUser = UserModel(
        id = 11,
        userName = "aliceFoo1",
        firstName = "Alice",
        lastName = "Foo",
        emailAddress = "alice@gmail.com",
        dateOfBirth = LocalDate.now(),
        lastModified = LocalDate.now(),
        created = LocalDate.now(),
    )

    private fun cleanup() {
        userService.deleteUser(testUser.id)
    }

    @Test
    fun `test create and fetch of a user`() {
        val user = userService.getUser(userService.createUser(testUser).id)
        assertEquals(user, testUser)
    }

    @Test
    fun `test fetch a user`() {
        val targetUserId = 1L
        val user1 = userService.getUser(targetUserId)
        assertEquals(user1?.id ?: -1, targetUserId)
    }

    @Test
    fun `test user can update email if email is valid`() {
        val newEmailAddress = "new@email.com"
        val targetUserId = 1L
        userService.updateUserEmail(targetUserId, newEmailAddress)
        val user1 = userService.getUser(targetUserId)
        assertEquals(user1?.emailAddress, newEmailAddress)
        userService.deleteUser(testUser.id)
    }

    @Test
    fun `test user cannot update email if email is invalid`() {
        userService.deleteUser(testUser.id)
        val testUserId1 = userService.createUser(testUser).id
        val invalidEmailAddress = "NOT A VALID EMAIL"
        assertThrows<InvalidEmailException> {
            userService.updateUserEmail(testUserId1, invalidEmailAddress)
        }
        userService.deleteUser(testUser.id)
    }
    @BeforeEach
    fun beforeEach() {
        cleanup()
    }

    @AfterEach
    fun afterEach() {
        cleanup()
    }
}