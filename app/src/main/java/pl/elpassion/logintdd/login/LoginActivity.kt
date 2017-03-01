package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.VISIBLE
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {

    private val loginApi = object : Login.Api {
        override fun login(login: String, password: String): Single<User> = Single.just(User(0))
    }

    private val repository = object : Login.UserRepository {
        override fun saveUser(user: User) {}
    }

    private val scheduler = Schedulers.trampoline()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val controller = LoginController(api = loginApi, view = this, userRepository = repository,
                observeOnScheduler = scheduler, subscribeOnScheduler = scheduler)

        loginButton.setOnClickListener {
            controller.onLogin(loginInput.text.toString(), passwordInput.text.toString())
        }
    }

    override fun showEmptyLoginError() {
        loginError.visibility = VISIBLE
    }

    override fun showEmptyPasswordError() {
        passwordError.visibility = VISIBLE
    }

    override fun openNextScreen() {
    }

    override fun showLoginFailed() {
    }

    override fun showLoader() {
        progress.visibility = VISIBLE
    }

    override fun hideLoader() {
    }
}