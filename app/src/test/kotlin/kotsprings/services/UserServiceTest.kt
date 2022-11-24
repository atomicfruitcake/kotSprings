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

//    var testUserId1: Long = 0

    private val testUser1 = UserModel(
        id = 1,
        userName = "aliceFoo1",
        firstName = "Alice",
        lastName = "Foo",
        emailAddress = "alice@gmail.com",
        dateOfBirth = LocalDate.now(),
        lastModified = LocalDate.now(),
        created = LocalDate.now(),
    )

    private fun cleanup() {
        userService.deleteUser(testUser1.id)
    }

    @Test
    fun `test create and fetch of a user`() {
        val user1 = userService.getUser(userService.createUser(testUser1).id)
        assertEquals(user1, testUser1)
        userService.deleteUser(testUser1.id)
    }

    @Test
    fun `test user can update email if email is valid`() {
        val newEmailAddress = "new@email.com"
        val testUserId1 = userService.createUser(testUser1).id
        userService.updateUserEmail(testUserId1, newEmailAddress)
        val user1 = userService.getUser(testUserId1)
        assertEquals(user1?.emailAddress, newEmailAddress)
        userService.deleteUser(testUser1.id)
    }

    @Test
    fun `test user cannot update email if email is invalid`() {
        userService.deleteUser(testUser1.id)
        val testUserId1 = userService.createUser(testUser1).id
        val invalidEmailAddress = "NOT A VALID EMAIL"
        assertThrows<InvalidEmailException> {
            userService.updateUserEmail(testUserId1, invalidEmailAddress)
        }
        userService.deleteUser(testUser1.id)
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