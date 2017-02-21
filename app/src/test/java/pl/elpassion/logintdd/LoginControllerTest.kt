package pl.elpassion.logintdd

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.CompletableSubject
import org.junit.Before
import org.junit.Test

class LoginControllerTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()
    private val apiSubject = CompletableSubject.create()
    private val loginController = LoginController(api, view)

    @Before
    fun setUp() {
        whenever(api.login(any(), any())).thenReturn(apiSubject)
    }

    @Test
    fun shouldNotCallApiWhenLoginIsBlank() {
        login(login = "")
        verify(api, never()).login(any(), any())
    }

    @Test
    fun shouldShowErrorIfLoginIsEmpty() {
        login(login = "")
        verify(view).showEmptyLoginError()
    }

    @Test
    fun shouldNotShowErrorWhenLoginDataAreCorrect() {
        login(login = "correctLogin")
        verify(view, never()).showEmptyLoginError()
    }

    @Test
    fun shouldNotCallApiWhenPasswordIsEmpty() {
        login(password = "")
        verify(api, never()).login(any(), any())
    }

    @Test
    fun shouldShowErrorIfPasswordIsEmpty() {
        login(password = "")
        verify(view).showEmptyPasswordError()
    }

    @Test
    fun shouldNotShowEmptyLoginErrorWhenOnlyPasswordIsEmpty() {
        login(password = "")
        verify(view, never()).showEmptyLoginError()
    }

    @Test
    fun shouldOpenNextScreenOnLoginSuccess() {
        login()
        apiSubject.onComplete()
        verify(view).openNextScreen()
    }

    @Test
    fun shouldNotOpenNextScreenUntilApiCompletes() {
        login()
        verify(view, never()).openNextScreen()
    }

    @Test
    fun shouldShowErrorWhenApiLoginFails() {
        whenever(api.login("wrongLogin", "wrongPassword")).thenReturn(Completable.error(RuntimeException()))
        login("wrongLogin", "wrongPassword")
        verify(view).showLoginFailed()
    }

    @Test
    fun shouldShowLoaderWhenCallingApi() {
        login()
        verify(view).showLoader()
    }

    @Test
    fun shouldHideLoaderWhenCallToApiEnds() {
        login()
        apiSubject.onComplete()
        verify(view).hideLoader()
    }

    @Test
    fun shouldNotHideLoaderBeforeApiCallEnd() {
        login()
        verify(view, never()).hideLoader()
    }

    @Test
    fun shouldHideLoaderWhenApiFails() {
        login()
        apiSubject.onError(RuntimeException())
        verify(view).hideLoader()
    }

    @Test
    fun shouldHideLoaderOnDestroy() {
        login()
        loginController.onDestroy()
        verify(view).hideLoader()
    }

    @Test
    fun shouldNotHideLoaderOnDestroyWhenApiNotStarted() {
        loginController.onDestroy()
        verify(view, never()).hideLoader()
    }

    private fun login(login: String = "correctLogin", password: String = "correctPassword") {
        loginController
                .onLogin(login = login, password = password)
    }
}

interface Login {
    interface Api {
        fun login(login: String, password: String): Completable
    }

    interface View {
        fun showEmptyLoginError()
        fun showEmptyPasswordError()
        fun openNextScreen()
        fun showLoginFailed()
        fun showLoader()
        fun hideLoader()
    }
}

class LoginController(val api: Login.Api, val view: Login.View) {

    var disposable: Disposable? = null

    fun onLogin(login: String, password: String) {
        when {
            login.isBlank() -> view.showEmptyLoginError()
            password.isBlank() -> view.showEmptyPasswordError()
            login.isNotBlank() -> login(login, password)
        }
    }

    private fun login(login: String, password: String) {
        disposable = api.login(login, password)
                .doOnSubscribe { view.showLoader() }
                .doFinally { view.hideLoader() }
                .subscribe({
                    view.openNextScreen()
                }, {
                    view.showLoginFailed()
                })
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}
