package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()

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
        login(password = "myPassword")
        verify(view, never()).showPasswordEmptyError()
    }

    private fun login(login: String= "myLogin", password: String = "myPassword") {
        LoginController(view).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
    }
}

class LoginController(private val view: Login.View) {
    fun login(login: String, password: String) {
        if (login.isEmpty()) {
            view.showLoginEmptyError()
        }

        if(password.isEmpty()) {
            view.showPasswordEmptyError()
        }
    }
}
