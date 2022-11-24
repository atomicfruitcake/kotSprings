package kotsprings.controllers

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotsprings.exceptions.BookAlreadyLoanedException
import kotsprings.exceptions.BookNotFoundException
import kotsprings.exceptions.InvalidUserIdException
import kotsprings.models.BookModel
import kotsprings.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

/**
 * Book controller
 *
 * @property bookService
 * @constructor Create empty Book controller
 */
@RestController
class BookController(private val bookService: BookService) {

    /**
     * Get book details from the library
     *
     * @param bookId Long - UniqueId of the book to lookup
     * @return Book that has been found by bookId
     */
    @GetMapping("/book/{bookId}")
    fun getBook(@PathVariable bookId: Long): BookModel? {
        try {
            return bookService.getBook(bookId)
        } catch(ex: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        }
    }

    /**
     * Create book a new book for the library
     *
     * @param book Book object to add to library
     * @return Book that has been created
     */
    @PutMapping("/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createBook(@RequestBody book: BookModel): BookModel {
        return bookService.createBook(book)
    }

    /**
     * Update a book object that already exists in the library
     *
     * @param bookId Long - UniqueId of the book to update
     * @param book Book Object that will apply the updates
     * @return Book object that has been updated
     */
    @PostMapping("/book/{bookId}")
    fun updateBook(@PathVariable bookId: Long, @RequestBody book: BookModel): BookModel {
        try {
            return bookService.updateBook(bookId, book)
        } catch (ex: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        }
    }

    /**
     * Loan a book to a user
     *
     * @param bookId Long - UniqueId of the book to be loaned
     * @param userId Long - UniqueId of the user to load the book too
     * @return Book object that has been loaned
     */
    @PostMapping("/book/loan/{bookId}/{userId}")
    fun loanBookToUser(@PathVariable bookId: Long, @PathVariable userId: Long): BookModel {
        try {
            return bookService.loanBookToUser(bookId, userId)
        } catch (ex: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        } catch (ex: BookAlreadyLoanedException) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, ex.localizedMessage, ex)
        }
    }

    /**
     * Return a book for a user
     *
     * @param bookId Long - UniqueId of the book to be returned
     * @param userId Long - UniqueId of the user returning the book
     * @return Book object that has been returned
     */
    @PostMapping("/book/return/{bookId}/{userId}")
    fun returnBookForUser(@PathVariable bookId: Long, @PathVariable userId: Long): BookModel {
        try {
            return bookService.returnBookForUser(bookId, userId)
        } catch (ex: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        } catch (ex: InvalidUserIdException) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, ex.localizedMessage, ex)
        }
    }
}