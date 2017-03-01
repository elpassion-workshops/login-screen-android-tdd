package pl.elpassion.logintdd.login

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
        login()
        verify(api).loginUser()
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
        fun loginUser()
    }
}

class LoginController(private val view: Login.View, private val api: Login.Api) {
    fun login(login: String, password: String) {
        if (login.isEmpty()) {
            view.showLoginEmptyError()
        }

        if (password.isEmpty()) {
            view.showPasswordEmptyError()
        }

        api.loginUser()
    }
}
