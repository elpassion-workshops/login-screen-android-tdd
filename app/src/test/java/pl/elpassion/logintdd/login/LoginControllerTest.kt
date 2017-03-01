package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()

    @Test
    fun shouldShowEmptyLoginError() {
        login(login = "")
        verify(view).showLoginEmptyError()
    }

    @Test
    fun shouldNotShowEmptyLoginError() {
        login(login = "myLogin")
        verify(view, never()).showLoginEmptyError()
    }

    @Test
    fun shouldShowEmptyPasswordError() {
        login(password = "")
        verify(view).showPasswordEmptyError()
    }

    @Test
    fun shouldNotShowEmptyPasswordError() {
        login(password = "password")
        verify(view, never()).showPasswordEmptyError()
    }

    @Test
    fun shouldShowLoaderWhenLoggingIn() {
        login()
        verify(view).showLoader()
    }

    @Test
    fun shouldNotPerformApiCallIfLoginEmpty() {
        login(login = "")
        verify(api, never()).performCall()
    }

    @Test
    fun shouldNotPerformApiCallIfPasswordEmpty() {
        login(password = "")
        verify(api, never()).performCall()
    }

    @Test
    fun shouldPerformApiCallIfLoginDataIsNotEmpty() {
        login(login = "login", password = "password")
        verify(api).performCall()
    }

    private fun login(login: String = "myLogin", password: String = "password") {
        LoginController(view, api).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
        fun showLoader()
    }

    interface Api {
        fun performCall()
    }
}

class LoginController(private val view: Login.View, private val api: Login.Api) {
    fun login(login: String, password: String) {
        view.showLoader()
        if (login.isEmpty()) {
            view.showLoginEmptyError()
        }
        if (password.isEmpty()) {
            view.showPasswordEmptyError()
        }
        if(login.isNotEmpty() && password.isNotEmpty()) {
            api.performCall()
        }
    }
}
