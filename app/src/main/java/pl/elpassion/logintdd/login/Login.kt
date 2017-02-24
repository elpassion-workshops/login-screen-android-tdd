package pl.elpassion.logintdd.login

import io.reactivex.Completable

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