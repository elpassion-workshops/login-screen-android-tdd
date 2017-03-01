package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {

    val loginController: LoginController = LoginController(api = object: Login.Api
    {
        override fun login(login: String, password: String): Single<User> {
            return SingleSubject.create<User>()
        }
    }, view = this, userRepository = object: Login.UserRepository {
        override fun saveUser(user: User) {
        }
    }, subscribeOnScheduler = Schedulers.io(), observeOnScheduler = AndroidSchedulers.mainThread())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginButton.setOnClickListener { loginController.onLogin(loginInput.text.toString(), passwordInput.text.toString()) }
    }

    override fun showEmptyLoginError() {
        error.visibility = View.VISIBLE
        error.text = getText(R.string.login_error)
    }

    override fun showEmptyPasswordError() {
        error.visibility = View.VISIBLE
    }

    override fun openNextScreen() {
    }

    override fun showLoginFailed() {
    }

    override fun showLoader() {
    }

    override fun hideLoader() {
    }
}