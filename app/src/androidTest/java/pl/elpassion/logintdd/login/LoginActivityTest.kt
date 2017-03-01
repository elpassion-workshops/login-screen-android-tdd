package pl.elpassion.logintdd.login

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.rule.ActivityTestRule
import android.text.InputType
import android.text.InputType.*
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
        onText(R.string.login_label).isDisplayed()
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
        onText(R.string.password_label).isDisplayed()
    }

    @Test
    fun shouldHaveLoginButton() {
        onText(R.string.login_button_text).isDisplayed()
    }

    @Test
    fun shouldPasswordBePunctuated() {
        onId(R.id.passwordInput)
                .check(matches(withInputType(TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT)))
    }
}