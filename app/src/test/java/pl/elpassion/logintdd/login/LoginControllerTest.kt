package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()

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
    fun `should call login API on login`() {
        login()
        verify(api).login()
    }

    @Test
    fun `should not call login API when login is empty`() {
        login(login = "")
        verify(api, never()).login()
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
        fun login()
    }
}

class LoginController(private val view: Login.View, private val api: Login.Api) {
    fun login(login: String, password: String) {
        if (login.isEmpty()) view.showLoginEmptyError()
        else api.login()
        if (password.isEmpty()) view.showPasswordEmptyError()
    }
}
