package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {
    private val view = mock<Login.View>()
    private val validator = mock<Login.Validator>()

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

    @Test
    fun `should not show progress bar when password is empty`() {
        login(password = "")
        verify(view, never()).showProgressBar()
    }

    @Test
    fun `should validate login credentials when not empty`() {
        login()
        verify(validator).validate()
    }

    @Test
    fun `should not validate credentials when login is empty`() {
        login(login = "")
        verify(validator, never()).validate()
    }

    @Test
    fun `should not validate credentials when password is empty`() {
        login(password = "")
        verify(validator, never()).validate()
    }

    @Test
    fun `should show credentials error when validation fails`() {
        whenever(validator.validate()).thenReturn(Observable.just(Unit))
        login()
        verify(view).showCredentialsError()
    }

    private fun login(login: String = "myLogin", password: String = "myPassword") {
        LoginController(view, validator).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
        fun showProgressBar()
        fun showCredentialsError()
    }

    interface Validator {
        fun validate() : Observable<Unit>
    }
}

class LoginController(private val view: Login.View, private val validator: Login.Validator) {
    fun login(login: String, password: String) {
        when {
            login.isEmpty() -> view.showLoginEmptyError()
            password.isEmpty() -> view.showPasswordEmptyError()
            else -> {
                view.showProgressBar()
                validator.validate()
            }
        }

        view.showCredentialsError()
    }
}
