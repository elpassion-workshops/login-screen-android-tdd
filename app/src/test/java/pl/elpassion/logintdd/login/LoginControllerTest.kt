package pl.elpassion.logintdd.login

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Test

class LoginControllerTest {

    private val view = mock<Login.View>()
    private val api = mock<Login.Api>()
    private val userRepository = mock<Login.UserRepository>()
    private val apiSubject = SingleSubject.create<User>()
    private val loginController = LoginController(api, view, userRepository)

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
        emitApiSuccess()
        verify(view).openNextScreen()
    }

    @Test
    fun shouldNotOpenNextScreenUntilApiCompletes() {
        login()
        verify(view, never()).openNextScreen()
    }

    @Test
    fun shouldShowErrorWhenApiLoginFails() {
        whenever(api.login("wrongLogin", "wrongPassword")).thenReturn(Single.error(RuntimeException()))
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
        emitApiSuccess()
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
        emitApiError()
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

    @Test
    fun shouldSaveReturnedUserFromApi() {
        val user = newUser(id = 2)
        login()
        emitApiSuccess(user)
        verify(userRepository).saveUser(user)
    }

    private fun login(login: String = "correctLogin", password: String = "correctPassword") {
        loginController
                .onLogin(login = login, password = password)
    }

    private fun emitApiSuccess(user: User = newUser()) {
        apiSubject.onSuccess(user)
    }

    private fun emitApiError() {
        apiSubject.onError(RuntimeException())
    }

    private fun newUser(id: Long = 1) = User(id)

}