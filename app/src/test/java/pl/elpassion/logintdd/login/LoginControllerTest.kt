package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()
    private val authStorage = mock<Login.AuthStorage>()
    private val loginSubject = SingleSubject.create<String>()

    @Before
    fun setUp() {
        whenever(api.login(any(), any())).thenReturn(loginSubject)
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
    fun `should show error when password is empty`() {
        login(password = "")
        verify(view).showPasswordEmptyError()
    }

    @Test
    fun `should not show error when password is not empty`() {
        login(password = "myPass")
        verify(view, never()).showPasswordEmptyError()
    }

    @Test
    fun `should not call login API when login is empty`() {
        login(login = "")
        verify(api, never()).login(any(), any())
    }

    @Test
    fun `should not call login API when password is empty`() {
        login(password = "")
        verify(api, never()).login(any(), any())
    }

    @Test
    fun `should show login and password errors when they are empty`() {
        login(login = "", password = "")
        verify(view).showPasswordEmptyError()
        verify(view).showLoginEmptyError()
    }

    @Test
    fun `should call login API with entered login and password`() {
        login(login = "myLogin", password = "myPassword")
        verify(api).login(login = "myLogin", password = "myPassword")
    }

    @Test
    fun `should call login API with entered login and password (case 2)`() {
        login(login = "login", password = "password")
        verify(api).login(login = "login", password = "password")
    }

    @Test
    fun `should open next screen on successful login`() {
        login()
        loginSubject.onSuccess("token")
        verify(view).openNextScreen()
    }

    @Test
    fun `should show error on unsuccessful login`() {
        login()
        loginSubject.onError(RuntimeException())
        verify(view).showLoginError()
    }

    @Test
    fun `should show loader while performing login API call`() {
        login()
        verify(view).showLoader()
    }

    @Test
    fun `should hide loader on unsuccessful login`() {
        login()
        loginSubject.onError(RuntimeException())
        verify(view).hideLoader()
    }

    @Test
    fun `should save auth credentials on successful login`() {
        login()
        loginSubject.onSuccess("token")
        verify(authStorage).saveCredentials("token")
    }

    private fun login(login: String = "login", password: String = "password") {
        LoginController(view, api, authStorage).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
        fun openNextScreen()
        fun showLoginError()
        fun showLoader()
        fun hideLoader()
    }

    interface Api {
        fun login(login: String, password: String): Single<String>
    }

    interface AuthStorage {
        fun saveCredentials(token: String)
    }
}

class LoginController(private val view: Login.View, private val api: Login.Api, private val authStorage: Login.AuthStorage) {
    fun login(login: String, password: String) {
        when {
            login.isEmpty() and password.isEmpty() -> {
                view.showLoginEmptyError()
                view.showPasswordEmptyError()
            }
            login.isEmpty() -> view.showLoginEmptyError()
            password.isEmpty() -> view.showPasswordEmptyError()
            else -> performApiLogin(login, password)
        }
    }

    private fun performApiLogin(login: String, password: String) {
        api
                .login(login, password)
                .doOnSubscribe { view.showLoader() }
                .doOnSuccess { token ->
                    authStorage.saveCredentials(token)
                    view.openNextScreen()
                }
                .doOnError {
                    view.showLoginError()
                    view.hideLoader()
                }
                .subscribe()
    }
}
