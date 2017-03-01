package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.elpassion.android.view.show
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View{
    lateinit var loginController: LoginController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginController = LoginController(Login.ApiProvider.get(), this, object: Login.UserRepository{}, Schedulers.io(), AndroidSchedulers.mainThread())
        setOnClickListener()
    }

    private fun setOnClickListener() {
        loginButton.setOnClickListener {
            loginController.onLogin(login = loginInput.text.toString(), password = passwordInput.text.toString())
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
        loginError.show()
    }

    override fun showLoader() {}

    override fun hideLoader() {}
}