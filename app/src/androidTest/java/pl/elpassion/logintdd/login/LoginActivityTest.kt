package pl.elpassion.logintdd.login

import android.support.test.espresso.ViewInteraction
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

    @JvmField @Rule
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun shouldHaveLoginInputHeader() {
        onText("Login").isDisplayed()
    }

    @Test
    fun shouldHaveLoginInput() {
        onId(R.id.loginInput).typeText("login").hasText("login")
    }

    @Test
    fun shouldHavePasswordInputHeader() {
        onText("Password").isDisplayed()
    }

    @Test
    fun shouldHavePasswordInput() {
        onId(R.id.passwordInput).typeText("password").hasText("password")
    }

    @Test
    fun shouldPasswordInputBeSecure() {
        onId(R.id.passwordInput).isSecure()
    }

    @Test
    fun shouldHaveLoginButton() {
        onId(R.id.loginButton).isDisplayed()
    }

    @Test
    fun shouldShowLoginEmptyErrorWhenLoginIsEmptyOnButtonClick() {
        onId(R.id.loginButton).click()
        onId(R.id.loginError).isDisplayed().hasText("Login cannot be empty")
    }

    @Test
    fun shouldNotShowLoginEmptyErrorWhenButtonIsNotClicked() {
        onId(R.id.loginError).isNotDisplayed()
    }
}

private fun ViewInteraction.isSecure() {
    check(matches(withInputType(TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT)))
}