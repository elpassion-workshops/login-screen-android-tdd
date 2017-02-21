package pl.elpassion.logintdd

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ExampleUnitTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()

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

    private fun login(login: String = "correctLogin", password: String = "correctPassword") {
        LoginController(api, view)
                .onLogin(login = login, password = password)
    }
}

interface Login {
    interface Api {
        fun login()
    }

    interface View {
        fun showEmptyLoginError()
        fun showEmptyPasswordError()
    }
}

class LoginController(val api: Login.Api, val view: Login.View) {
    fun onLogin(login: String, password: String) {
        if (login.isNotBlank() && password.isNotBlank()) {
            api.login()
        } else {
            view.showEmptyLoginError()
            view.showEmptyPasswordError()
        }
    }
}
