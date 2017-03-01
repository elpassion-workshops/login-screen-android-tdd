package pl.elpassion.logintdd.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.login_activity.*
import pl.elpassion.logintdd.R

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginButton.setOnClickListener { loginError.visibility = VISIBLE }
    }
}