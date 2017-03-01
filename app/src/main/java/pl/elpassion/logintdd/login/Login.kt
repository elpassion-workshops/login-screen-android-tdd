package pl.elpassion.logintdd.login

import io.reactivex.Single

interface Login {
    interface Api {
        fun login(login: String, password: String): Single<User> = TODO()
    }
    object ApiProvider {
        var override : Login.Api? = null

        fun get() = override ?: TODO()
    }

    interface View {
        fun showEmptyLoginError()
        fun showEmptyPasswordError()
        fun openNextScreen()
        fun showLoginFailed()
        fun showLoader()
        fun hideLoader()
    }

    interface UserRepository {
        fun saveUser(user: User): Unit = TODO()
    }
}