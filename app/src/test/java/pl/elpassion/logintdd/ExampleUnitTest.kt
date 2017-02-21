package pl.elpassion.logintdd

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ExampleUnitTest {

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

    private fun login(login: String) {
        LoginController(api).onLogin(login = login)
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
