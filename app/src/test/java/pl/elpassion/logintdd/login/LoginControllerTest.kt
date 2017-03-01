package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()

    @Test
    fun shouldShowLoginErrorWhenLoginIsEmpty() {
        login(login = "")
        verify(view).showLoginEmptyError()
    }

    @Test
    fun shouldNotShowLoginErrorWhenLoginIsNotEmpty() {
        login(login = "myLogin")
        verify(view, never()).showLoginEmptyError()
    }

    @Test
    fun shouldShowPasswordErrorWhenPasswordIsEmpty() {
        login(password = "")
        verify(view).showPasswordEmptyError()
    }

    @Test
    fun shouldNotShowPasswordErrorWhenPasswordNotEmpty() {
        login(password = "myPassword")
        verify(view, never()).showPasswordEmptyError()
    }

    @Test
    fun shouldCallApiWhenCredentialsAreCorrect() {
        login("login1", "password1")
        verify(api).loginUser("login1", "password1")
    }

    @Test
    fun shouldNotCallApiWhenLoginIsEmpty() {
        login(login = "")
        verify(api, never()).loginUser(any(), any())
    }

    @Test
    fun shouldNotCallApiWhenPasswordIsEmpty() {
        login(password = "")
        verify(api, never()).loginUser(any(), any())
    }

    @Test
    fun shouldReallyCallApiWhenCredentialsAreCorrect() {
        login("password_3", "password_3")
        verify(api).loginUser("password_3", "password_3")
    }


    private fun login(login: String = "login", password: String = "password") {
        LoginController(view, api).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
    }

    interface Api {
        fun loginUser(login: String, password: String)
    }
}

class LoginController(private val view: Login.View, private val api: Login.Api) {
    fun login(login: String, password: String) {
        when {
            login.isEmpty() -> view.showLoginEmptyError()
            password.isEmpty() -> view.showPasswordEmptyError()
            else -> api.loginUser(login, password)
        }
    }
}
