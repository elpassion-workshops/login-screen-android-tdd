package pl.elpassion.logintdd;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleInstrumentedTest {

    @Test
    public void shouldUseAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("pl.elpassion.logintdd", appContext.getPackageName());
    }
}
