package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.elpassion.android.view.show
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {
    val loginController = LoginController(
            api = Login.ApiProvider.get(),
            view = this,
            userRepository = object : Login.UserRepository {
                override fun saveUser(user: User) = Unit

            },
            observeOnScheduler = AndroidSchedulers.mainThread(),
            subscribeOnScheduler = Schedulers.trampoline())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginButton.setOnClickListener {
            loginController.onLogin(loginInput.text.toString(), passwordInput.text.toString())
        }
    }

    override fun showEmptyLoginError() {
        errorMessage.show()
    }

    override fun showEmptyPasswordError() {
        emptyPasswordError.show()
    }

    override fun openNextScreen() {
    }

    override fun showLoginFailed() {
        loginError.show()
    }

    override fun showLoader() {
        progressBar.show()
    }

    override fun hideLoader() {
    }

}