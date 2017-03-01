package pl.elpassion.logintdd.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.elpassion.android.view.show
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.MainActivity
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(), Login.View {

	private val userRepository = object : Login.UserRepository {
		override fun saveUser(user: User) = Unit
	}

	val loginController = LoginController(Login.ApiProvider.get(), this, userRepository, Schedulers.io(), AndroidSchedulers.mainThread())

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)

		loginButton.setOnClickListener { onSignInClicked() }
	}

	override fun showEmptyLoginError() = emptyLoginErrorTextView.show()

	override fun showEmptyPasswordError() = TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

	override fun openNextScreen() = startActivity(Intent(this, MainActivity::class.java))

	override fun showLoginFailed() = apiErrorTextView.show()

	override fun showLoader() = Unit

	override fun hideLoader() = Unit

	private fun onSignInClicked() = loginController.onLogin(loginInput.stringText(), passwordInput.stringText())

	private fun TextView.stringText() = text.toString()
}