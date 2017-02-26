package pl.elpassion.logintdd.login

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.rule.ActivityTestRule
import android.text.InputType
import com.elpassion.android.commons.espresso.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.R

class LoginActivityTest {

    private val apiSubject = SingleSubject.create<User>()

    @JvmField @Rule
    val rule = object : ActivityTestRule<LoginActivity>(LoginActivity::class.java) {
        override fun beforeActivityLaunched() {
            Login.Api.override = mock<Login.Api>().apply {
                whenever(login(any(), any())).thenReturn(apiSubject.observeOn(AndroidSchedulers.mainThread()))
            }
            super.beforeActivityLaunched()
        }
    }

    @JvmField @Rule
    val intentsRule = InitIntentsRule()

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
        apiSubject.onError(RuntimeException())
        onText("Login failed").isDisplayed()
    }

    @Test
    fun shouldNotShowApiErrorOnStart() {
        onText("Login failed").isNotDisplayed()
    }

    @Test
    fun shouldNotShowLoginErrorWhenSucceed() {
        login()
        onText("Login failed").isNotDisplayed()
    }

    @Test
    fun shouldOpenNextScreenAfterApiSucceed() {
        login()
        apiSubject.onSuccess(User(1))
        checkIntent(NextScreen::class.java)
    }

    @Test
    fun shouldShowLoaderAfterLogin() {
        login()
        onId(R.id.loader).isDisplayed()
    }

    @Test
    fun shouldHideLoaderAfterLoginFailure() {
        login()
        apiSubject.onError(RuntimeException())
        onId(R.id.loader).isNotDisplayed()
    }

    @Test
    fun shouldLoaderBeInvisibleOnStart() {
        onId(R.id.loader).isNotDisplayed()
    }

    private fun login(login: String = "email@test.com", password: String = "secret") {
        onId(R.id.loginInput).typeText(login)
        onId(R.id.passwordInput).typeText(password)
        onId(R.id.loginButton).click()
    }
}