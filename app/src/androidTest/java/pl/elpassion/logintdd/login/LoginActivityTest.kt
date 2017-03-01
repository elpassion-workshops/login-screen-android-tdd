package pl.elpassion.logintdd.login

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.rule.ActivityTestRule
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import com.elpassion.android.commons.espresso.*
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.anyString
import pl.elpassion.logintdd.MainActivity
import pl.elpassion.logintdd.R

class LoginActivityTest {

	private val login = "login"
	private val apiSubject = SingleSubject.create<User>()

	@JvmField @Rule
	val rule = object : ActivityTestRule<LoginActivity>(LoginActivity::class.java) {
		override fun beforeActivityLaunched() {
			Login.ApiProvider.override = mock<Login.Api>().apply {
				whenever(login(anyString(), anyString())).thenReturn(apiSubject)
			}
		}
	}
	@JvmField @Rule
	val intentsRule = InitIntentsRule()

	@Test
	fun shouldHaveLoginInputHeader() {
		onText(R.string.loginLabel).isDisplayed()
	}

	@Test
	fun shouldHaveLoginInput() {
		onId(R.id.loginInput).typeText(login).hasText(login)
	}

	@Test
	fun shouldHavePasswordInput() {
		onId(R.id.passwordInput).typeText(login).hasText(login)
	}

	@Test
	fun shouldHavePasswordInputHeader() {
		onText(R.string.passwordLabel).isDisplayed()
	}

	@Test
	fun shouldPasswordBePunctuated() {
		onId(R.id.passwordInput)
				.check(matches(withInputType(TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT)))
	}

	@Test
	fun shouldHaveLoginButton() {
		onText(R.string.loginButtonText).isDisplayed()
	}

	@Test
	fun shouldNotHaveVisibleLoaderInitially() {
		onId(R.id.progressBar).isNotDisplayed()
	}

	@Test
	fun shouldShowEmptyLoginError() {
		onText(R.string.loginButtonText).click()
		onText(R.string.emptyLogin).isDisplayed()
	}

	@Test
	fun shouldNotShowEmptyLoginErrorInitially() {
		onText(R.string.emptyLogin).isNotDisplayed()
	}

	@Test
	fun shouldOpenNextScreenAfterSuccessfulLogin() {
		onId(R.id.loginInput).typeText("login")
		onId(R.id.passwordInput).typeText("password")
		apiSubject.onSuccess(User(1))
		onText(R.string.loginButtonText).click()
		checkIntent(MainActivity::class.java)
	}

	@Test
	fun shouldDisplayMessageOnApiError() {
		onId(R.id.loginInput).typeText("login")
		onId(R.id.passwordInput).typeText("password")
		apiSubject.onError(Throwable())
		onText(R.string.loginButtonText).click()
		onText(R.string.apiError).isDisplayed()
	}
}