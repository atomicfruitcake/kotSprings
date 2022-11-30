package kotsprings.services

import kotsprings.exceptions.BookAlreadyLoanedException
import kotsprings.exceptions.BookNotFoundException
import kotsprings.exceptions.InvalidUserIdException
import kotsprings.models.BookModel
import kotsprings.repositories.BookRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Book service for managing the book library
 *
 * @property repository
 * @constructor Create empty Book service
 */
@Service
class BookService(private val repository: BookRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Get book from the DB given a bookId
     *
     * @param bookId Long - UniqueId of book
     * @throws BookNotFoundException - Thrown when the bookId does not match any known book
     * @return BookModel object
     */
    fun getBook(bookId: Long): BookModel? {
        logger.info("Getting new book with id=${bookId}")
        return repository.findById(bookId).orElseThrow {
            BookNotFoundException("No book with id=$bookId found")
        }
    }

    /**
     * Get all books in the library
     *
     * @return Mutable<BookModel> object
     */
    fun getAllBooks(): MutableList<BookModel> {
        logger.info("Getting all books")
        return repository.findAll()
    }

    /**
     * Create book
     *
     * @param book Book object to be added to library
     * @return Book Object that has been created
     */
    fun createBook(book: BookModel): BookModel {
        logger.info("Creating new book with title=${book.title}")
        return repository.save(book)
    }

    /**
     * Update book
     *
     * @param bookId Long - UniqueId of book
     * @param book Book Object containing updates to be applied
     * @throws BookNotFoundException - Thrown when the bookId does not match any known book
     * @return
     */
    fun updateBook(bookId: Long, book: BookModel): BookModel {
        val curBook: BookModel = repository.findById(bookId).orElseThrow {
            BookNotFoundException("No book wiht bookId=$bookId found")
        }
        curBook.title = book.title
        curBook.author = book.author
        curBook.genre = book.genre
        curBook.yearPublished = book.yearPublished
        curBook.borrowerUserId = book.borrowerUserId

        return repository.save(curBook)
    }

    /**
     * Loan book out to a user
     *
     * @param bookId Long - UniqueId of book
     * @param userId Long - UniqueId of user
     * @throws BookNotFoundException - Thrown when the bookId does not match any known book
     * @return Book Object that has been loaned to a user
     */
    fun loanBookToUser(bookId: Long, userId: Long): BookModel {
        val bookToLoan: BookModel = repository.findById(bookId).orElseThrow {
            BookNotFoundException("No book with bookId=$bookId found")
        }
        if (bookToLoan.borrowerUserId != null) {
            throw BookAlreadyLoanedException(
                "Book ${bookToLoan.title} is already lent to user ${bookToLoan.borrowerUserId}"
            )
        }
        bookToLoan.borrowerUserId = userId
        return repository.save(bookToLoan)
    }

    /**
     * Return book for user
     *
     * @param bookId Long - UniqueId of book
     * @param userId Long - UniqueId of user
     * @throws BookNotFoundException - Thrown when the bookId does not match any known book
     * @throws InvalidUserIdException - Thrown when the user returning is not the user that borrowed the book
     * @return Book object that has been returned by User
     */
    fun returnBookForUser(bookId: Long, userId: Long): BookModel {
        val bookToReturn: BookModel = repository.findById(bookId).orElseThrow {
            BookNotFoundException("No book with bookId=$bookId found")
        }
        if (bookToReturn.borrowerUserId != userId) {
            throw InvalidUserIdException(
                "Cannot return book $bookId for user $userId as they are not the registered borrower"
            )
        }
        bookToReturn.borrowerUserId = null;
        return repository.save(bookToReturn)
    }
}