package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()

    @Test
    fun shouldShowErrorWhenLoginIsEmpty() {
        login(login = "", password = "")
        verify(view).showLoginEmptyError()
    }

    @Test
    fun shouldNotShowErrorWhenLoginIsNotEmpty() {
        login(login = "myLogin", password = "")
        verify(view, never()).showLoginEmptyError()
    }

    @Test
    fun shouldShowErrorWhenPasswordIsEmpty() {
        login(password = "")
        verify(view).showPasswordEmptyError()
    }

    private fun login(login: String = "login", password: String) {
        LoginController(view).login(login = login)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
    }
}

class LoginController(private val view: Login.View) {
    fun login(login: String) {
        if (login.isEmpty()) {
            view.showLoginEmptyError()
        }
        view.showPasswordEmptyError()
    }
}
