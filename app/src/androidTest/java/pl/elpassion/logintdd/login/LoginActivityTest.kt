package pl.elpassion.logintdd.login

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withInputType
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
    fun shouldShowLoginHeader() {
        onText("Login").isDisplayed()
    }

    @Test
    fun shouldShowLoginInput() {
        onId(R.id.loginInput).typeText("email@test.com").hasText("email@test.com")
    }

    @Test
    fun shouldShowPasswordHeader() {
        onText("Password").isDisplayed()
    }

    @Test
    fun shouldShowPasswordInput() {
        onId(R.id.passwordInput).typeText("secret").hasText("secret")
    }

    @Test
    fun shouldShowPasswordInSecretWay() {
        onId(R.id.passwordInput).check(matches(withInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT)))
    }

    @Test
    fun shouldShowLoginButton() {
        onId(R.id.loginButton).isDisplayed()
    }

    @Test
    fun shouldShowErrorWhenLoginEmpty() {
        onId(R.id.loginButton).click()
        onText("Login cannot be empty").isDisplayed()
    }
}
