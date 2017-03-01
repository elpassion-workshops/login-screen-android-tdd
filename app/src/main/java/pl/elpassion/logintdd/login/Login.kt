package pl.elpassion.logintdd.login

import io.reactivex.Single

interface Login {
    interface Api {
        fun login(login: String, password: String): Single<User>
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
        fun saveUser(user: User)
    }

    object ApiProvider {
        var override: Login.Api? = null
        fun get() = override ?: TODO("Not implemented")
    }

    object UserRepositoryProvider {
        var override: Login.UserRepository? = null
        fun get() = override ?: TODO("Not implemented")
    }
}