package pl.elpassion.logintdd.login

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.text.InputType
import com.elpassion.android.commons.espresso.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.R
import javax.security.auth.Subject

class LoginActivityTest {

    private val loginSubject = SingleSubject.create<User>()

    @JvmField @Rule
    val rule = object : ActivityTestRule<LoginActivity>(LoginActivity::class.java){
        override fun beforeActivityLaunched() {
            Login.ApiProvider.override = mock<Login.Api>().apply {
                whenever(login(any(), any())).thenReturn(loginSubject)
            }
        }
    }

    @Test
    fun shouldHaveLoginInputHeader() {
        onText("Login").isDisplayed()
    }

    @Test
    fun shouldHaveLoginInput() {
        onId(R.id.loginInput).typeText("login").hasText("login")
    }

    @Test
    fun shouldHavePasswordInput() {
        onId(R.id.passwordInput).typeText("password").hasText("password")
    }

    @Test
    fun shouldHavePasswordHeader() {
        onText("Password").isDisplayed()
    }

    @Test
    fun shouldPasswordBeSecure() {
        onId(R.id.passwordInput).check(matches(withInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)))
    }

    @Test
    fun shouldHaveLoginButton() {
        onId(R.id.loginButton).isDisplayed()
    }

    @Test
    fun shouldHaveHiddenErrorMessage() {
        onId(R.id.errorMessage).isNotDisplayed()
    }

    @Test
    fun shouldShowErrorMessageWhenEmptyLogin() {
        logIn(login = "")
        onId(R.id.errorMessage).isDisplayed()
    }

    @Test
    fun shouldShowErrorWhenEmptyPassword() {
        logIn(password = "")
        onId(R.id.emptyPasswordError).isDisplayed()
    }

    @Test
    fun shouldHaveHiddenEmptyPasswordMessage() {
        onId(R.id.emptyPasswordError).isNotDisplayed()
    }

    @Test
    fun shouldShowProgressBar() {
        logIn()
        onId(R.id.progressBar).isDisplayed()
    }

    @Test
    fun shouldStartWithHiddenProgressBar() {
        onId(R.id.progressBar).isNotDisplayed()
    }

    @Test
    fun shouldShowLoginErrorWhenApiCallFails() {
        logIn(login = "invalidLogin", password = "invalidPassword")
        loginSubject.onError(RuntimeException())
        onId(R.id.loginError).isDisplayed()
    }

    private fun logIn(login: String = "login", password: String = "password") {
        onId(R.id.loginInput).typeText(login)
        onId(R.id.passwordInput).typeText(password)
        onId(R.id.loginButton).click()
    }
}