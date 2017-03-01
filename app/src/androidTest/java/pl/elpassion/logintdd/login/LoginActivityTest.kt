package pl.elpassion.logintdd.login

import android.support.test.rule.ActivityTestRule
import com.elpassion.android.commons.espresso.*
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.R
import pl.elpassion.logintdd.common.Animations

class LoginActivityTest {

    @JvmField @Rule
    val rule = object : ActivityTestRule<LoginActivity>(LoginActivity::class.java){
        override fun beforeActivityLaunched() {
            Animations.areEnabled = false
        }
    }

    @Test
    fun shouldHaveLoginInputHeader() {
        onId(R.id.loginHeader).hasText("Login").isDisplayed()
    }

    @Test
    fun shouldHaveLoginInput() {
        onId(R.id.loginInput).typeText("login").hasText("login")
    }

    @Test
    fun shouldHavePasswordInputHeader() {
        onText("Password").isDisplayed()
    }

    @Test
    fun shouldHavePasswordInput() {
        onId(R.id.passwordInput).typeText("password").hasText("password")
    }

    @Test
    fun shouldHaveLoginButton() {
        onId(R.id.loginButton).isDisplayed().hasText("Login")
    }

    @Test
    fun shouldShowLoaderOnLoginClicked() {
        onId(R.id.loader).isDisplayed()
    }
}