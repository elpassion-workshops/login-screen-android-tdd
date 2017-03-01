package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {

    private val view = mock<Login.View>()

    @Test
    fun `should show login error when login is empty`() {
        login(login = "")
        verify(view).showLoginEmptyError()
    }

    @Test
    fun `should not show login error when login is not empty`() {
        login(login = "myLogin")
        verify(view, never()).showLoginEmptyError()
    }

    @Test
    fun `should show password error when password is empty`() {
        login(password = "")
        verify(view).showPasswordEmptyError()
    }

    @Test
    fun `should not show password error when password is not empty`() {
        login(password = "myPassword")
        verify(view, never()).showPasswordEmptyError()
    }

    @Test
    fun `should show progress bar when login initiated`() {
        login()
        verify(view).showProgressBar()
    }

    @Test
    fun `should not show progress bar when login is empty`() {
        login(login = "")
        verify(view, never()).showProgressBar()
    }

    private fun login(login: String= "myLogin", password: String = "myPassword") {
        LoginController(view).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
        fun showProgressBar()
    }
}

class LoginController(private val view: Login.View) {
    fun login(login: String, password: String) {
        if (login.isEmpty()) {
            view.showLoginEmptyError()
        } else {
            view.showProgressBar()
        }

        if(password.isEmpty()) {
            view.showPasswordEmptyError()
        }
    }
}
