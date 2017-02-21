package pl.elpassion.logintdd

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun shouldCallApiOnLogin() {
        val api = mock<Login.Api>()
        LoginController(api).onLogin(login = "login")

        verify(api).login()
    }

    @Test
    fun shouldNotCallApiWhenLoginIsBlank() {
        val api = mock<Login.Api>()
        LoginController(api).onLogin(login = "")

        verify(api, never()).login()
    }

}

interface Login {
    interface Api {
        fun login()
    }

}

class LoginController(val api: Login.Api) {
    fun onLogin(login: String) {
        if (login.isNotBlank()) {
            api.login()
        }
    }

}
