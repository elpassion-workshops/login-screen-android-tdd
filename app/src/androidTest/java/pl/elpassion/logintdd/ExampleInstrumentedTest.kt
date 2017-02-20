package pl.elpassion.logintdd

import android.support.test.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleInstrumentedTest {

    @Test
    fun shouldUseAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("pl.elpassion.logintdd", appContext.packageName)
    }
}