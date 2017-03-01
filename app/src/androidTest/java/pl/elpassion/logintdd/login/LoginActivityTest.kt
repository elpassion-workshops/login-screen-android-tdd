package pl.elpassion.logintdd.login

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.rule.ActivityTestRule
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import com.elpassion.android.commons.espresso.*
import com.nhaarman.mockito_kotlin.check
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.R

class LoginActivityTest {

    @JvmField @Rule
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun shouldHaveLoginInputHeader() {
        onText(R.string.email).isDisplayed()
    }

    @Test
    fun shouldHaveLoginInput() {
        onId(R.id.loginInput).typeText("login").hasText("login")
    }

    @Test
    fun shouldHavePasswordInputHeader() {
        onText(R.string.password).isDisplayed()
    }

    @Test
    fun shouldHavePasswordInput() {
        onId(R.id.passwordInput).typeText("password").hasText("password")
    }

    @Test
    fun shouldHaveSecurePassword() {
        onId(R.id.passwordInput).check(matches(withInputType(TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT)))
    }

    @Test
    fun shouldHaveLoginButton() {
        onId(R.id.button_login).hasText(R.string.login)
    }

    @Test
    fun shouldShowErrorWhenLoginIsEmpty() {
        onId(R.id.loginInput).typeText("")
        onId(R.id.button_login).click()
        onId(R.id.login_empty_error).isDisplayed()
    }


}