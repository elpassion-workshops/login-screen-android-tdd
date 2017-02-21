package pl.elpassion.logintdd

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()
    private val apiSubject = CompletableSubject.create()

    @Before
    fun setUp() {
        whenever(api.login()).thenReturn(apiSubject)
    }

    @Test
    fun shouldCallApiOnLogin() {
        login(login = "login")
        verify(api).login()
    }

    @Test
    fun shouldNotCallApiWhenLoginIsBlank() {
        login(login = "")
        verify(api, never()).login()
    }

    @Test
    fun shouldShowErrorIfLoginIsEmpty() {
        login(login = "")
        verify(view).showEmptyLoginError()
    }

    @Test
    fun shouldNotShowErrorWhenLoginDataAreCorrect() {
        login(login = "login")
        verify(view, never()).showEmptyLoginError()
    }

    @Test
    fun shouldNotCallApiWhenPasswordIsEmpty() {
        login(password = "")
        verify(api, never()).login()
    }

    @Test
    fun shouldShowErrorIfPasswordIsEmpty() {
        login(password = "")
        verify(view).showEmptyPasswordError()
    }

    @Test
    fun shouldNotShowEmptyLoginErrorWhenOnlyPasswordIsEmpty() {
        login(password = "")
        verify(view, never()).showEmptyLoginError()
    }

    @Test
    fun shouldOpenNextScreenOnLoginSuccess() {
        login()
        apiSubject.onComplete()
        verify(view).openNextScreen()
    }

    @Test
    fun shouldNotOpenNextScreenUntilApiCompletes() {
        login()
        verify(view, never()).openNextScreen()
    }

    private fun login(login: String = "correctLogin", password: String = "correctPassword") {
        LoginController(api, view)
                .onLogin(login = login, password = password)
    }
}

interface Login {
    interface Api {
        fun login(): Completable
    }

    interface View {
        fun showEmptyLoginError()
        fun showEmptyPasswordError()
        fun openNextScreen()
    }
}

class LoginController(val api: Login.Api, val view: Login.View) {
    fun onLogin(login: String, password: String) {
        when {
            login.isBlank() -> view.showEmptyLoginError()
            password.isBlank() -> view.showEmptyPasswordError()
            else -> login()
        }
    }

    private fun login() {
        api.login().subscribe {
            view.openNextScreen()
        }
    }
}
