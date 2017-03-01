package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginButton.setOnClickListener {
            if ((loginInput as EditText).text.isNotEmpty() || (passwordInput as EditText).text.isNotEmpty()) {
                loginEmptyError.visibility = View.GONE
            } else {
                loginEmptyError.visibility = View.VISIBLE
            }
        }
    }
}