package pl.elpassion.logintdd.login

import io.reactivex.disposables.Disposable

class LoginController(val api: Login.Api, val view: Login.View) {

    var disposable: Disposable? = null

    fun onLogin(login: String, password: String) {
        when {
            login.isBlank() -> view.showEmptyLoginError()
            password.isBlank() -> view.showEmptyPasswordError()
            else -> login(login, password)
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