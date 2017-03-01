package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class LoginControllerTest {
    private val view = mock<Login.View>()
    private val validator = mock<Login.Validator>()
    private val apiTokenStore = mock<Login.ApiTokenStore>()
    private val loginSubject = SingleSubject.create<Unit>()

    @Before
    fun setUp() {
        whenever(validator.validate()).thenReturn(loginSubject)
    }

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
        login()
        loginSubject.onError(RuntimeException())
        verify(view).showCredentialsError()
    }

    @Test
    fun `should not show credentials error when validation passes`() {
        login()
        loginSubject.onSuccess(Unit)
        verify(view, never()).showCredentialsError()
    }

    @Test
    fun `should hide progress bar when validation passes`() {
        login()
        loginSubject.onSuccess(Unit)
        verify(view).hideProgressBar()
    }

    @Test
    fun `should not hide progress bar until validation passes`() {
        login()
        verify(view, never()).hideProgressBar()
    }

    @Test
    fun `should save authentication token when validation passes`() {
        login()
        verify(apiTokenStore).saveToken()
    }

    private fun login(login: String = "myLogin", password: String = "myPassword") {
        LoginController(view, validator, apiTokenStore).login(login = login, password = password)
    }
}

interface Login {
    interface View {
        fun showLoginEmptyError()
        fun showPasswordEmptyError()
        fun showProgressBar()
        fun showCredentialsError()
        fun hideProgressBar()
    }

    interface Validator {
        fun validate(): Single<Unit>
    }

    interface ApiTokenStore {
        fun saveToken()
    }
}

class LoginController(private val view: Login.View, private val validator: Login.Validator, private val apiTokenStore: Login.ApiTokenStore) {
    fun login(login: String, password: String) {
        when {
            login.isEmpty() -> view.showLoginEmptyError()
            password.isEmpty() -> view.showPasswordEmptyError()
            else -> {
                view.showProgressBar()
                validator
                        .validate()
                        .doOnError { view.showCredentialsError() }
                        .doOnSuccess { view.hideProgressBar() }
                        .subscribe({}, {})
            }
        }

        apiTokenStore.saveToken()
    }
}
