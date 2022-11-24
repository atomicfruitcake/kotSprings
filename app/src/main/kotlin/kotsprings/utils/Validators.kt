package kotsprings.utils

import java.util.regex.Pattern


/**
 * Validate that an email address is a valid email
 *
 * @param emailAddress String - Email Address to check for validity
 * @return Boolean - True if the Email Address is valid, False otherwise
 */
fun emailAddressIsValid(emailAddress: String) : Boolean {
    return  Pattern
        .compile("^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE)
        .matcher(emailAddress)
        .matches()
}
