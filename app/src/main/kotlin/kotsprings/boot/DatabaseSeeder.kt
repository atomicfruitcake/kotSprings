package kotsprings.boot

import kotlinx.coroutines.*
import io.github.serpro69.kfaker.Faker
import kotsprings.models.BookModel
import kotsprings.models.UserModel
import kotsprings.repositories.BookRepository
import kotsprings.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DatabaseSeeder {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val faker = Faker()

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @EventListener
    fun seedDatabase(event: ContextRefreshedEvent) {
        logger.info("Seeding database")
        runBlocking { seedTables() }
    }

    private suspend fun seedTables() = coroutineScope {
        awaitAll(
            async { seedUsers() },
            async { seedBooks() }
        )
    }

    private fun seedUsers(numUsers: Long = 10) {
        logger.info("Seeding database with $numUsers users")
        val users = mutableListOf<UserModel>()
        for (i in 1..numUsers) {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            users.add(
                UserModel(
                    id = i,
                    userName = "$firstName$lastName${(0..99).random()}",
                    firstName = firstName,
                    lastName = lastName,
                    emailAddress = faker.internet.safeEmail(),
                    dateOfBirth = LocalDate.now(),
                    lastModified = LocalDate.now(),
                    created = LocalDate.now(),
                )
            )
        }
        userRepository.saveAll(users)
    }

    private fun seedBooks(numBooks: Long = 10) {
        logger.info("Seeding database with $numBooks books")
        val books = mutableListOf<BookModel>()
        for (i in 1..numBooks) {
            books.add(
                BookModel(
                    id = i,
                    title = faker.book.title(),
                    author = faker.book.author(),
                    genre = faker.book.genre(),
                    yearPublished = (1900..2020).random(),
                    borrowerUserId = null,
                    lastModified = LocalDate.now(),
                    created = LocalDate.now()
                )
            )
        }
        bookRepository.saveAll(books)
    }
}