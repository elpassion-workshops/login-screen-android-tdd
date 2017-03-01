package pl.elpassion.logintdd.login

import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {

	val loginController = LoginController(object : Login.Api {
		override fun login(login: String, password: String) = Single.never<User>()

	}, this, object : Login.UserRepository {
		override fun saveUser(user: User) = Unit
	}, Schedulers.io(), AndroidSchedulers.mainThread())

	override fun showEmptyLoginError() {
		emptyLoginErrorTextView.visibility = View.VISIBLE
	}

	override fun showEmptyPasswordError() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun openNextScreen() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun showLoginFailed() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun showLoader() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun hideLoader() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)

		loginButton.setOnClickListener { onSignInClicked() }
	}

	private fun TextView.stringText() = text.toString()

	private fun onSignInClicked() = loginController.onLogin(loginInput.stringText(), passwordInput.stringText())
}