package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import java.util.*

class LoginControllerTest {

	private val view = mock<Login.View>()
	private val api = mock<Login.Api>()
	private val database = mock<Login.Database>()
	private val apiSubject = SingleSubject.create<String>()

	@Before
	fun setUp() {
		whenever(api.login()).thenReturn(apiSubject)
	}

	@Test
	fun shouldShowErrorWhenLoginIsEmpty() {
		login(login = "")
		verify(view).showLoginEmptyError()
	}

	@Test
	fun shouldNotShowErrorWhenLoginIsNotEmpty() {
		login(login = "myLogin")
		verify(view, never()).showLoginEmptyError()
	}

	@Test
	fun shouldShowErrorWhenPasswordIsEmpty() {
		login(password = "")
		verify(view).showPasswordEmptyError()
	}

	@Test
	fun shouldNotShowErrorWhenPasswordIsNotEmpty() {
		login(password = "password")
		verify(view, never()).showPasswordEmptyError()
	}

	@Test
	fun shouldCallApiWhenCredentialsAreValid() {
		login()
		verify(api).login()
	}

	@Test
	fun shouldNotCallApiWhenPasswordIsEmpty() {
		login(password = "")
		verify(api, never()).login()
	}

	@Test
	fun shouldNotCallApiWhenLoginIsEmpty() {
		login(login = "")
		verify(api, never()).login()
	}

	@Test
	fun shouldNotCallApiWhenLoginAndPasswordAreEmpty() {
		login(login = "", password = "")
		verify(api, never()).login()
	}

	@Test
	fun shouldPersistAccessTokenOnSuccessfulLogin() {
		login()
		val accessToken = UUID.randomUUID().toString()

		apiSubject.onSuccess(accessToken)

		verify(database).saveAccessToken(accessToken)
	}

	@Test
	fun shouldShowLoaderWhenApiCallStarted() {
		login()
		verify(view).showLoader()
	}

	@Test
	fun shouldHideLoaderWhenApiCallFinished() {
		login()
		apiSubject.onSuccess("tokenAccess")
		verify(view).hideLoader()
	}

	@Test
	fun shouldNotHideLoaderUntilApiCallNotFinished() {
		login()
		verify(view, never()).hideLoader()
	}

	@Test
	fun shouldHideLoaderOnApiCallError() {
		login()
		apiSubject.onError(Throwable())
		verify(view).hideLoader()
	}

	private fun login(login: String = "login", password: String = "password") {
		LoginController(view, api, database).login(login = login, password = password)
	}
}

interface Login {
	interface View {
		fun showLoginEmptyError()
		fun showPasswordEmptyError()
		fun showLoader()
		fun hideLoader()
	}

	interface Api {
		fun login(): Single<String>
	}

	interface Database {
		fun saveAccessToken(token: String)
	}
}

class LoginController(private val view: Login.View, private val api: Login.Api, private val database: Login.Database) {

	fun login(login: String, password: String) {
		when {
			login.isEmpty() -> view.showLoginEmptyError()
			password.isEmpty() -> view.showPasswordEmptyError()
			else -> {
				view.showLoader()
				api.login()
						.doFinally { view.hideLoader() }
						.subscribe({ database.saveAccessToken(it) }, { })
			}
		}
	}
}
