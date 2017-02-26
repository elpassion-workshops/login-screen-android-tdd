package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.elpassion.android.view.show
import io.reactivex.Single
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginButton.setOnClickListener {
            LoginController(object : Login.Api {
                override fun login(login: String, password: String) = Single.error<User>(RuntimeException())
            }, this, object : Login.UserRepository {
                override fun saveUser(user: User) = Unit
            }).onLogin(loginInput.text.toString(), passwordInput.text.toString())
        }
    }

    override fun showEmptyLoginError() {
        loginEmptyError.show()
    }

    override fun showEmptyPasswordError() {
        passwordEmptyError.show()
    }

    override fun openNextScreen() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoginFailed() {
        apiError.show()
    }

    override fun showLoader() = Unit

    override fun hideLoader() = Unit
}