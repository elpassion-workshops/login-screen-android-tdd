package pl.elpassion.logintdd.login

import android.support.test.rule.ActivityTestRule
import com.elpassion.android.commons.espresso.isDisplayed
import com.elpassion.android.commons.espresso.onText
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @JvmField @Rule
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun shouldStartLoginActivity() {

    }

    @Test
    fun shouldHaveLoginInputHeader() {
        onText("Login").isDisplayed()
    }
}