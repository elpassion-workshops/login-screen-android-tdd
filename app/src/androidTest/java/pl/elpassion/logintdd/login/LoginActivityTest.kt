package pl.elpassion.logintdd.login

import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.rule.ActivityTestRule
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import com.elpassion.android.commons.espresso.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.MainActivity
import pl.elpassion.logintdd.R

class LoginActivityTest {

    private val userSubject = SingleSubject.create<User>()
    @JvmField @Rule
    val rule = object : ActivityTestRule<LoginActivity>(LoginActivity::class.java){
        override fun beforeActivityLaunched() {
            Login.ApiProvider.override = mock<Login.Api>().apply {
                whenever(login(any(), any())).thenReturn(userSubject)
            }
        }
    }

    @JvmField @Rule
    val intentRule = InitIntentsRule()

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
        onId(R.id.loginEmptyError).isDisplayed().hasText("Login cannot be empty")
    }

    @Test
    fun shouldNotShowLoginEmptyErrorOnStart() {
        onId(R.id.loginEmptyError).isNotDisplayed()
    }

    @Test
    fun shouldShowPasswordEmptyErrorWhenPasswordIsEmptyOnButtonClick() {
        onId(R.id.loginInput).typeText("sdsdg")
        onId(R.id.loginButton).click()
        onId(R.id.passwordEmptyError).isDisplayed().hasText("Password cannot be empty")
    }

    @Test
    fun shouldNotShowPasswordEmptyErrorOnStart() {
        onId(R.id.passwordEmptyError).isNotDisplayed()
    }

    @Test
    fun shouldShowLoginErrorOnLoginError() {
        login()
        mockApiError()
        onId(R.id.loginError).isDisplayed().hasText("Login error")
    }

    @Test
    fun shouldOpenNextScreenOnLoginSuccess () {
        login()
        mockApiSuccess()
        checkIntent(MainActivity::class.java)
    }

    private fun mockApiSuccess() = userSubject.onSuccess(User(4))

    private fun mockApiError() = userSubject.onError(RuntimeException())

    private fun login() {
        onId(R.id.loginInput).typeText("login")
        onId(R.id.passwordInput).typeText("password")
        onId(R.id.loginButton).click()
    }
}

private fun ViewInteraction.isSecure() = check(matches(withInputType(TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT)))