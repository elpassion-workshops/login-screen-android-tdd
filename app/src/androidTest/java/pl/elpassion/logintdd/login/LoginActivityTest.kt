package pl.elpassion.logintdd.login

import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.text.InputType
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
    fun shouldHavePasswordLabel() {
        onText("Password").isDisplayed()
    }

    @Test
    fun shouldHavePasswordInput() {
        onId(R.id.passwordInput).typeText("password").hasText("password")
    }

    @Test
    fun shouldPasswordBeSecure() {
        onId(R.id.passwordInput).isSecure()
    }

    fun ViewInteraction.isSecure() {
        check(ViewAssertions.matches(ViewMatchers.withInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)))
    }
}