package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()
    private val apiManager = mock<Login.Api>()

    @Test
    fun shouldShowErrorWhenLoginIsEmpty() {
        login(login = "")
        verify(view).showLoginEmptyError()
    }

    @Test
    fun shouldNotShowErrorWhenLoginIsNotEmpty() {
        login(login = "login")
        verify(view, never()).showLoginEmptyError()
    }

    @Test
    fun shouldShowErrorWhenPasswordIsEmpty() {
        login(password = "")
        verify(view).showPasswordEmptyError()
    }

    @Test
    fun shouldNotShowErrorWhenPasswordIsNotEmpty() {
        login(password = "MargaretTatcherIs100%Sexy")
        verify(view, never()).showPasswordEmptyError()
    }

    @Test
    fun shouldCallApiWhenLoginAndPasswordAreNotEmpty() {
        login(login = "login", password = "MargaretTatcherIs100%Sexy")
        verify(apiManager).login()
    }

    @Test
    fun shouldNotCallApiWhenLoginIsEmpty() {
        login(login = "", password = "MargaretTatcherIs100%Sexy")
        verify(apiManager, never()).login()
    }

    @Test
    fun shouldNotCallApiWhenPasswordIsEmpty() {
        login(login = "login", password = "")
        verify(apiManager, never()).login()
    }

    @Test
    fun shouldShowProgressWhenApiCall() {
        login(login = "login", password = "MargaretTatcherIs100%Sexy")
        verify(view).showProgress()
    }

    @Test
    fun shouldNotShowProgressWhenNoApiCall() {
        login(login = "", password = "")
        verify(view, never()).showProgress()
    }

    private fun login(login: String = "login", password: String = "MargaretTatcherIs100%Sexy") {
        LoginController(view, apiManager).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
        fun showProgress()
    }

    interface Api {
        fun login()
    }
}

class LoginController(private val view: Login.View,
                      private val apiManager: Login.Api) {
    fun login(login: String, password: String) {
        when {
            login.isEmpty() -> view.showLoginEmptyError()
            password.isEmpty() -> view.showPasswordEmptyError()
            else -> {
                apiManager.login()
                view.showProgress()
            }
        }

    }
}
