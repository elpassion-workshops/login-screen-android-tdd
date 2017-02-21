package pl.elpassion.logintdd

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun shouldCallApiOnLogin() {
        val api = mock<Login.Api>()
        LoginController(api).onLogin()

        verify(api).login()
    }

}

interface Login {
    interface Api {
        fun login()
    }

}

class LoginController(val api: Login.Api) {
    fun onLogin() {
        api.login()
    }

}
