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

    private fun login(login: String) {
        LoginController(api, view).onLogin(login = login)
    }
}

interface Login {
    interface Api {
        fun login()
    }

    interface View {
        fun showEmptyLoginError()
    }

}

class LoginController(val api: Login.Api, val view: Login.View) {
    fun onLogin(login: String) {
        if (login.isNotBlank()) {
            api.login()
        }
        view.showEmptyLoginError()
    }

}
