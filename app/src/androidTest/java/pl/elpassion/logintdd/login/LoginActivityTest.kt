package pl.elpassion.logintdd.login

import android.support.test.rule.ActivityTestRule
import com.elpassion.android.commons.espresso.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.MainActivity
import pl.elpassion.logintdd.R
import pl.elpassion.logintdd.common.Animations

class LoginActivityTest {

    private val loginSubject = SingleSubject.create<User>()
    @JvmField @Rule
    val rule = object : ActivityTestRule<LoginActivity>(LoginActivity::class.java) {
        override fun beforeActivityLaunched() {
            Animations.areEnabled = false
            Login.ApiProvider.override = mock<Login.Api>().apply {
                whenever(login(any(), any())).thenReturn(loginSubject)
            }
            Login.UserRepositoryProvider.override = mock()
        }
    }

    @JvmField @Rule
    val intentsRule = InitIntentsRule()

    @Test
    fun shouldHaveLoginInputHeader() {
        onId(R.id.loginHeader).hasText("Login").isDisplayed()
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
    fun shouldHaveLoginButton() {
        onId(R.id.loginButton).isDisplayed().hasText("Login")
    }

    @Test
    fun shouldShowLoaderOnLoginClicked() {
        onId(R.id.loginButton).click()
        onId(R.id.loader).isDisplayed()
    }

    @Test
    fun shouldNotShowProgressBarWhenNotClicked() {
        onId(R.id.loader).isNotDisplayed()
    }

    @Test
    fun shouldHideProgressBarWhenApiError() {
        onId(R.id.loginButton).click()
        loginSubject.onError(Throwable())
        onId(R.id.loader).isNotDisplayed()
    }

    @Test
    fun shouldShowErrorTextWhenApiError() {
        onId(R.id.loginButton).click()
        loginSubject.onError(Throwable())
        onId(R.id.textApiError).isDisplayed().hasText(R.string.api_error_text)
    }

    @Test
    fun shouldGoToNextScreenAfterOnSuccess() {
        onId(R.id.loginButton).click()
        loginSubject.onSuccess(User(1))
        checkIntent(MainActivity::class.java)
    }
}