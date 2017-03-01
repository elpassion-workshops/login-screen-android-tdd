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

    private fun login(login: String) {
        LoginController(view).login(login = login)
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
