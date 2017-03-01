package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

	private val view = mock<Login.View>()
	private val api = mock<Login.Api>()
	private val database = mock<Login.Database>()

	@Before
	fun setUp() {
		whenever(api.login()).thenReturn(Single.never())
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
		verify(database).saveAccessToken()
	}

	@Test
	fun shouldShowLoaderWhenApiCallStarted() {
		login()
		verify(view).showLoader()
	}

	@Test
	fun shouldHideLoaderWhenApiCallFinished() {
		whenever(api.login()).thenReturn(Single.just(Unit))
		login()
		verify(view).hideLoader()
	}

	@Test
	fun shouldNotHideLoaderWhenApiCallNotFinished() {
		whenever(api.login()).thenReturn(Single.never())
		login()
		verify(view, never()).hideLoader()
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
		fun login(): Single<Unit>
	}

	interface Database {
		fun saveAccessToken()
	}
}

class LoginController(private val view: Login.View, private val api: Login.Api, private val database: Login.Database) {

	fun login(login: String, password: String) {
		when {
			login.isEmpty() -> view.showLoginEmptyError()
			password.isEmpty() -> view.showPasswordEmptyError()
			else -> {
				view.showLoader()
				api.login().subscribe({ view.hideLoader() }, {})
			}
		}
		database.saveAccessToken()
	}
}
