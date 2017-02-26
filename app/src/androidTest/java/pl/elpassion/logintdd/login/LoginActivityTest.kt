package pl.elpassion.logintdd.login

import android.support.test.rule.ActivityTestRule
import com.elpassion.android.commons.espresso.*
import org.junit.Rule
import org.junit.Test
import pl.elpassion.logintdd.R

class LoginActivityTest {

    @JvmField @Rule
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun shouldShowLoginHeader() {
        onText("Login").isDisplayed()
    }

    @Test
    fun shouldShowLoginInput() {
        onId(R.id.loginInput).typeText("email@test.com").hasText("email@test.com")
    }

    @Test
    fun shouldShowPassword() {
        onText("Password").isDisplayed()
    }
}
