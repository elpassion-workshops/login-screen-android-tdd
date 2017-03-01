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
    private val apiManager = mock<Login.Api>()
    private val publishSubject = SingleSubject.create<Unit>()

    @Before
    fun setUp() {
        whenever(apiManager.login()).thenReturn(publishSubject)
    }

    @Test
    fun shouldShowErrorWhenLoginIsEmpty() {
        login(login = "")
        verify(view).showLoginEmptyError()
    }

    @Test
    fun shouldNotShowErrorWhenLoginIsNotEmpty() {
        login()
        verify(view, never()).showLoginEmptyError()
    }

    @Test
    fun shouldShowErrorWhenPasswordIsEmpty() {
        login(password = "")
        verify(view).showPasswordEmptyError()
    }

    @Test
    fun shouldNotShowErrorWhenPasswordIsNotEmpty() {
        login()
        verify(view, never()).showPasswordEmptyError()
    }

    @Test
    fun shouldCallApiWhenLoginAndPasswordAreNotEmpty() {
        login()
        verify(apiManager).login()
    }

    @Test
    fun shouldNotCallApiWhenLoginIsEmpty() {
        login(login = "")
        verify(apiManager, never()).login()
    }

    @Test
    fun shouldNotCallApiWhenPasswordIsEmpty() {
        login(password = "")
        verify(apiManager, never()).login()
    }

    @Test
    fun shouldShowProgressWhenApiCall() {
        login()
        verify(view).showProgress()
    }

    @Test
    fun shouldNotShowProgressWhenNoApiCall() {
        login(login = "", password = "")
        verify(view, never()).showProgress()
    }

    @Test
    fun shouldHideProgressWhenApiCallEnded() {
        login()
        publishSubject.onSuccess(Unit)
        verify(view).hideProgress()
    }

    @Test
    fun shouldNotHideProgressWhenApiCallNotEnded() {
        login()
        verify(view, never()).hideProgress()
    }

    @Test
    fun shouldHideProgressWhenApiCallError() {
        login()
        publishSubject.onError(RuntimeException())
        verify(view).hideProgress()
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
        fun hideProgress()
    }

    interface Api {
        fun login(): Single<Unit>
    }
}

class LoginController(private val view: Login.View,
                      private val apiManager: Login.Api) {
    fun login(login: String, password: String) {
        when {
            login.isEmpty() -> view.showLoginEmptyError()
            password.isEmpty() -> view.showPasswordEmptyError()
            else -> {
                apiManager.login().subscribe({
                    view.hideProgress()
                }, {
                    view.hideProgress()
                })
                view.showProgress()
            }
        }
    }
}
