package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    @Test
    fun shouldShowErrorWhenLoginIsEmpty() {
        val view = mock<Login.View>()
        LoginController(view).login(login = "")
        verify(view).showLoginEmptyError()
    }

    @Test
    fun shouldNotShowErrorWhenLoginIsNotEmpty() {
        val view = mock<Login.View>()
        LoginController(view).login(login = "myLogin")
        verify(view, never()).showLoginEmptyError()
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
    }
}

class LoginController(private val view: Login.View) {
    fun login(login: String) {
        if (login.isEmpty()) {
            view.showLoginEmptyError()
        }
    }
}
