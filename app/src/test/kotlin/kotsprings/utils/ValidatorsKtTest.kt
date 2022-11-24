package kotsprings.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ValidatorsKtTest {

    @Test
    fun testEmailAddressIsValid() {
        val validEmailAddress = "foo@bar.com"
        assertTrue(emailAddressIsValid(validEmailAddress))
    }

    @Test
    fun testEmailAddressIsInvalid() {
        val invalidEmailAddress = "Not a Valid Email"
        assertFalse(emailAddressIsValid(invalidEmailAddress))
    }
}