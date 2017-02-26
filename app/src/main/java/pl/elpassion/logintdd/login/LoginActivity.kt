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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginButton.setOnClickListener {
            LoginController(object : Login.Api {
                override fun login(login: String, password: String) = Single.never<User>()
            }, this, object : Login.UserRepository {
                override fun saveUser(user: User) = Unit
            }, Schedulers.io(), AndroidSchedulers.mainThread()).onLogin(loginInput.text.toString(), "")
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
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoader() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoader() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}