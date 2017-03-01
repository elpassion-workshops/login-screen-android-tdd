package pl.elpassion.logintdd.login

import android.support.test.rule.ActivityTestRule
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
    fun shouldHavePasswordInputHeader() {
        onText(R.string.password_label).isDisplayed()
    }
}