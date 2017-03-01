package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {

    private val controller: LoginController = LoginController(Login.ApiProvider.get(), this, Login.UserRepositoryProvider.get(), Schedulers.newThread(), AndroidSchedulers.mainThread())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginButton.setOnClickListener { controller.onLogin("login", "password") }
    }

    override fun showEmptyLoginError() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyPasswordError() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openNextScreen() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoginFailed() {
    }

    override fun showLoader() {
        loader.visibility = VISIBLE
    }

    override fun hideLoader() {
        loader.visibility = INVISIBLE
    }
}