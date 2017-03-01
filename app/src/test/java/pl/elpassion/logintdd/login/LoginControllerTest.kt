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
        verify(api, never()).performApiCall()
    }

    private fun login(login: String = "myLogin", password: String = "password") {
        LoginController(view).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
        fun showLoader()
    }

    interface Api {
        fun performApiCall()
    }
}

class LoginController(private val view: Login.View) {
    fun login(login: String, password: String) {
        view.showLoader()
        if (login.isEmpty()) {
            view.showLoginEmptyError()
        }
        if (password.isEmpty()) {
            view.showPasswordEmptyError()
        }
    }
}
