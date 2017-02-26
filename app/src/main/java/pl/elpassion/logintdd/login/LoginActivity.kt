package pl.elpassion.logintdd.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.elpassion.android.view.show
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginButton.setOnClickListener {
            LoginController(Login.Api.get(), this, object : Login.UserRepository {
                override fun saveUser(user: User) = Unit
            }, Schedulers.io(), AndroidSchedulers.mainThread())
                    .onLogin(loginInput.text.toString(), passwordInput.text.toString())
        }
    }

    override fun showEmptyLoginError() {
        loginEmptyError.show()
    }

    override fun showEmptyPasswordError() {
        passwordEmptyError.show()
    }

    override fun openNextScreen() {
        startActivity(Intent(this, NextScreen::class.java))
    }

    override fun showLoginFailed() {
        apiError.show()
    }

    override fun showLoader() = Unit

    override fun hideLoader() = Unit
}