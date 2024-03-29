@file:Suppress("ClassName")

package work.curioustools.core_jdk

import work.curioustools.core_jdk.extras.CommonStringRegex
import work.curioustools.core_jdk.extras.EmailValidationResponse
import work.curioustools.core_jdk.extras.PasswordValidationResponse

fun String?.isValidEmail(): Boolean {
    /**
     * Note: as per this thread https://stackoverflow.com/q/46155/7500651 ,
     * email validation via regex is not ideal and there is no complete pattern
     * that would handle all current and future
     * 'emails'(and whatever the definition of email covers) .
     * so i just used the pattern used in AOSP source and made it JUnit Test friendly .
     * Android pattern is accessible via : android.util.Patterns.EMAIL_ADDRESS */
    this ?: return false
    return this.matches(CommonStringRegex.VALID_EMAIL.toRegex())
}

fun String?.validateEmail(): EmailValidationResponse {
    this ?: return EmailValidationResponse.NULL_OR_BLANK
    return if (isValidEmail()) EmailValidationResponse.VALID
    else EmailValidationResponse.IMPROPER_EMAIL
}

fun String?.validatePasswordBasic(pwdMin: Int = 8, pwdMax: Int = 16): PasswordValidationResponse {
    // allowed characters       : a-z | A-Z | 0-9 |basic special chars (#@$%^^*.. etc)
    // not allowed characters   : spaces,breaking characters( \ "  ' etc)
    // other notes              : must have at least 1 number , 1 special and 1 upper case and 1 lowercase char

    if (isNullOrBlank()) return PasswordValidationResponse.NULL_OR_BLANK
    if (length < pwdMin || length > pwdMax)
        return PasswordValidationResponse.OUT_OF_BOUNDS_ERROR(pwdMin, pwdMax)

    var has1Digit = false
    var has1Special = false
    var has1Upper = false
    var has1Lower = false
    forEach {
        when {
            it.isStrictlyADigit() -> has1Digit = true
            it.isStrictlyAnUpperCase() -> has1Upper = true
            it.isStrictlyALowerCase() -> has1Lower = true
            it.isStrictlyAValidSpecialLetter() -> has1Special = true
            else -> return PasswordValidationResponse.ILLEGAL_CHARACTER(it)
        }
    }

    return when {
        !has1Digit -> PasswordValidationResponse.NO_MIN_DIGITS(1)
        !has1Upper -> PasswordValidationResponse.NO_MIN_UPPERCASE_LETTERS(1)
        !has1Lower -> PasswordValidationResponse.NO_LOWERCASE_LETTERS(1)
        !has1Special -> PasswordValidationResponse.NO_MIN_Special_LETTERS(1)
        else -> PasswordValidationResponse.VALID
    }


}

fun String.startWithUpperCase(): String {
    /*This will be searching for first letter of word/String and convert it to uppercase.if its an invalid string or already has uppercase character then there won't be any effect on the string*/
    if (this.isBlank()) return this

    val firstLower = indexOfFirst { it.isLetter() }
    return if (firstLower in this.indices) substring(0, firstLower) + get(firstLower).uppercaseChar() + substring(firstLower + 1, length)
    else this

}

fun String.startWithLowerCase(): String {
    /*This will be searching for first letter of word/String and convert it to uppercase.if its an invalid string or already has uppercase character then there won't be any effect on the string*/
    if (this.isBlank()) return this

    val firstLower = indexOfFirst { it.isLowerCase() || it.isUpperCase() }
    return if (firstLower in this.indices)
        substring(0, firstLower) + get(firstLower).lowercase() + substring(firstLower + 1, length)
    else this
}

fun String.capitalizeEachWord(separator: Char = ' '): String {
    if (this.isBlank()) return this

    return this.split(separator).joinToString(" ") { it.startWithUpperCase() }
}

fun String.ellipsize(maxLength: Int): String {
    return if (length < maxLength) this
    else "${substring(0, maxLength)}..."
}

fun String?.toIntSafe(default: Int = 0): Int {
    if (this == null) return default
    return kotlin.runCatching { this.toIntOrNull() }.getOrNull() ?: default
}


private class StringExtensionsTest {
//fun main() {
//    val s = StringExtensionsTest()
//    s.apply {
//        isValidEmail_TestSuccess()
//        println("========")
//        isValidEmail_TestFailure()
//        println("========")
//        startWithUpperCase_TestSuccess()
//        println("========")
//        capitalizeEachWord_TestSuccess()
//        println("========")
//        startWithLowerCase_TestSuccess()
//        println("========")
//        validateSimplePassword_Tests()
//        println("========")
//        isValidEmail_TestSuccess2()
//        println("========")
//
//    }
//
//}

    fun startWithUpperCase_TestSuccess() {
        println("".startWithUpperCase())
        println("   ".startWithUpperCase())
        println("   1  ".startWithUpperCase())
        println("   a.  ".startWithUpperCase())
        println("   a big bad wolf.  ".startWithUpperCase())
        println("   .  ".startWithUpperCase())
        println("   A BIg BAD WOLF  ".startWithUpperCase())
        println("   a big bad wolf ".startWithUpperCase())
        println("   .a big bad wolf ".startWithUpperCase())
        println("   . a big bad wolf ".startWithUpperCase())
        println("This".startWithUpperCase())
        println("this".startWithUpperCase())
        println("t".startWithUpperCase())
        println("T".startWithUpperCase())
        println("This is already correct ".startWithUpperCase())
        println("A string is already correct ".startWithUpperCase())

        println("this should be made capital.".startWithUpperCase())
        println("a string should be made capital.".startWithUpperCase())


    }

    fun startWithLowerCase_TestSuccess() {
        println("".startWithUpperCase().startWithLowerCase())
        println("   ".startWithUpperCase().startWithLowerCase())
        println("   1  ".startWithUpperCase().startWithLowerCase())
        println("   a.  ".startWithUpperCase().startWithLowerCase())
        println("   a big bad wolf.  ".startWithUpperCase().startWithLowerCase())
        println("   .  ".startWithUpperCase().startWithLowerCase())
        println("   A BIg BAD WOLF  ".startWithUpperCase().startWithLowerCase())
        println("   a big bad wolf ".startWithUpperCase().startWithLowerCase())
        println("   .a big bad wolf ".startWithUpperCase().startWithLowerCase())
        println("   . a big bad wolf ".startWithUpperCase().startWithLowerCase())
        println("This".startWithUpperCase().startWithLowerCase())
        println("this".startWithUpperCase().startWithLowerCase())
        println("t".startWithUpperCase().startWithLowerCase())
        println("T".startWithUpperCase().startWithLowerCase())
        println("This is already correct ".startWithUpperCase().startWithLowerCase())
        println("A string is already correct ".startWithUpperCase().startWithLowerCase())

        println("this should be made capital.".startWithUpperCase().startWithLowerCase())
        println("a string should be made capital.".startWithUpperCase().startWithLowerCase())


    }

    fun capitalizeEachWord_TestSuccess() {
        println("".capitalizeEachWord())
        println("   ".capitalizeEachWord())
        println("   1  ".capitalizeEachWord())
        println("   a.  ".capitalizeEachWord())
        println("   a big bad wolf.  ".capitalizeEachWord())
        println("   .  ".capitalizeEachWord())
        println("   A BIg BAD WOLF  ".capitalizeEachWord())
        println("   a big bad wolf ".capitalizeEachWord())
        println("   .a big bad wolf ".capitalizeEachWord())
        println("   . a big bad wolf ".capitalizeEachWord())
        println("This".capitalizeEachWord())
        println("this".capitalizeEachWord())
        println("t".capitalizeEachWord())
        println("T".capitalizeEachWord())
        println("This is already correct ".capitalizeEachWord())
        println("A string is already correct ".capitalizeEachWord())

        println("this should be made capital.".capitalizeEachWord())
        println("a string should be made capital.".capitalizeEachWord())


    }

    fun isValidEmail_TestSuccess() {
        println("prettyandsimple@example.com                            ${"prettyandsimple@example.com".isValidEmail()}")
        println("very.common@example.com                                ${"very.common@example.com".isValidEmail()}")
        println("disposable.style.email.with+symbol@example.com         ${"disposable.style.email.with+symbol@example.com".isValidEmail()}")
        println("other.email-with-dash@example.com                      ${"other.email-with-dash@example.com".isValidEmail()}")
    }

    fun isValidEmail_TestSuccess2() {
        println("prettyandsimple@example.com                            ${"prettyandsimple@example.com".validateEmail().msg}")
        println("very.common@example.com                                ${"very.common@example.com".validateEmail().msg}")
        println("disposable.style.email.with+symbol@example.com         ${"disposable.style.email.with+symbol@example.com".validateEmail().msg}")
        println("other.email-with-dash@example.com                      ${"other.email-with-dash@example.com".validateEmail().msg}")
    }

    fun isValidEmail_TestFailure() {

        //correct emails but not recognised by our pattern :(
        println("\" \"@example.org                                      ${"\" \"@example.org".isValidEmail()}")
        println("üñîçøðé@example.com                                    ${"üñîçøðé@example.com".isValidEmail()}")
        println("üñîçøðé@üñîçøðé.com                                    ${"üñîçøðé@üñîçøðé.com".isValidEmail()}")
        println("δοκιμή@παράδειγμα.δοκιμή                               ${"δοκιμή@παράδειγμα.δοκιμή".isValidEmail()}")
        println("我買@屋企.香港                                           ${"我買@屋企.香港".isValidEmail()}")
        println("甲斐@黒川.日本                                           ${"甲斐@黒川.日本".isValidEmail()}")
        println("чебурашка@ящик-с-апельсинами.рф                        ${"чебурашка@ящик-с-апельсинами.рф".isValidEmail()}")


    }

    fun validateSimplePassword_Tests() {
        //pass cases |

        arrayOf(
            //pass
            "John1234##",

            //fail
            "john1234",
            "johnJOHN1234",
            "JOHN1234",
            "johndowsmith",
            "JOHNDOWSMITH",
            "12345678",
            "johnJOHN",
            "john",
            "john12345678JOHN12",
            "john dowsmith",
            "älien1234AL#"

        ).forEach { println("$it\t\t\t\t${it.validatePasswordBasic().msg}") }

    }

}

