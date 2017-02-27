package pl.elpassion.logintdd.login

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class LoginController(
        private val api: Login.Api,
        private val view: Login.View,
        private val userRepository: Login.UserRepository,
        private val subscribeOnScheduler: Scheduler,
        private val observeOnScheduler: Scheduler) {

    private var disposable: Disposable? = null

    fun onLogin(login: String, password: String) {
        when {
            login.isBlank() -> view.showEmptyLoginError()
            password.isBlank() -> view.showEmptyPasswordError()
            else -> login(login, password)
        }
    }

    private fun login(login: String, password: String) {
        disposable = api.login(login, password)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .doOnSubscribe { view.showLoader() }
                .doFinally { view.hideLoader() }
                .doOnSuccess { userRepository.saveUser(it) }
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