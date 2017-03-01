package pl.elpassion.logintdd.login

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.rule.ActivityTestRule
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import com.elpassion.android.commons.espresso.*
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.R

class LoginActivityTest {

    private val login = "login"

    @JvmField @Rule
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun shouldHaveLoginInputHeader() {
        onText(R.string.loginLabel).isDisplayed()
    }

    @Test
    fun shouldHaveLoginInput() {
        onId(R.id.loginInput).typeText(login).hasText(login)
    }

    @Test
    fun shouldHavePasswordInput() {
        onId(R.id.passwordInput).typeText(login).hasText(login)
    }

    @Test
    fun shouldHavePasswordInputHeader() {
        onText(R.string.passwordLabel).isDisplayed()
    }

    @Test
    fun shouldPasswordBePunctuated() {
        onId(R.id.passwordInput)
                .check(matches(withInputType(TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT)))
    }

    @Test
    fun shouldHaveLoginButton() {
        onText(R.string.loginButtonText).isDisplayed()
    }

    @Test
    fun shouldNotHaveVisibleLoaderInitially() {
        onId(R.id.progressBar).isNotDisplayed()
    }
}