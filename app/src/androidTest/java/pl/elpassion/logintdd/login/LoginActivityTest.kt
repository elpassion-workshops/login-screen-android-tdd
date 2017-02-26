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
        login(login = "")
        onText("Login cannot be empty").isDisplayed()
    }

    @Test
    fun shouldNotShowEmptyLoginErrorOnStart() {
        onText("Login cannot be empty").isNotDisplayed()
    }

    @Test
    fun shouldNotShowLoginEmptyErrorWhenLoginIsNotEmpty() {
        login(login = "email@test.com")
        onText("Login cannot be empty").isNotDisplayed()
    }

    @Test
    fun shouldShowErrorWhenPasswordIsEmpty() {
        login(password = "")
        onText("Password cannot be empty").isDisplayed()
    }

    @Test
    fun shouldNotShowPasswordErrorOnStart() {
        onText("Password cannot be empty").isNotDisplayed()
    }

    @Test
    fun shouldNotShowPasswordEmptyErrorWhenLoginIsNotEmpty() {
        login()
        onText("Password cannot be empty").isNotDisplayed()
    }

    @Test
    fun shouldShowLoginCallError() {
        login()
        onText("Login failed").isDisplayed()
    }

    @Test
    fun shouldNotShowApiErrorOnStart() {
        onText("Login failed").isNotDisplayed()
    }

    private fun login(login: String = "email@test.com", password: String = "secret") {
        onId(R.id.loginInput).typeText(login)
        onId(R.id.passwordInput).typeText(password)
        onId(R.id.loginButton).click()
    }
}
