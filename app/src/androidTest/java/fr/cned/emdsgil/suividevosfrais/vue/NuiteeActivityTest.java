package fr.cned.emdsgil.suividevosfrais.vue;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import fr.cned.emdsgil.suividevosfrais.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class NuiteeActivityTest {

    @Rule
    public ActivityTestRule<NuiteeActivity> nuiteeActivityActivityTestRule = new ActivityTestRule<NuiteeActivity>(NuiteeActivity.class);

    private NuiteeActivity nuiteeActivity = null;

    Instrumentation.ActivityMonitor monitorMenu = getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        nuiteeActivity = nuiteeActivityActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        ImageView imgNuiteeReturn = nuiteeActivity.findViewById(R.id.imgNuiteeReturn);
        Button cmdNuiteePlus = nuiteeActivity.findViewById(R.id.cmdNuiteePlus);
        Button cmdNuiteeMoins = nuiteeActivity.findViewById(R.id.cmdNuiteeMoins);
        Button cmdNuiteeValider = nuiteeActivity.findViewById(R.id.cmdNuiteeValider);

        assertNotNull(imgNuiteeReturn);
        assertNotNull(cmdNuiteePlus);
        assertNotNull(cmdNuiteeMoins);
        assertNotNull(cmdNuiteeValider);

        assertEquals("+", cmdNuiteePlus.getText());
        assertEquals("-", cmdNuiteeMoins.getText());
        assertEquals("Valider", cmdNuiteeValider.getText());
    }

    @Test
    public void testLaunchOfMenuActivityOnButtonClick() {
        assertNotNull(nuiteeActivity.findViewById(R.id.imgNuiteeReturn));

        onView(withId(R.id.imgNuiteeReturn)).perform(click());
        Activity menuActivity = getInstrumentation().waitForMonitorWithTimeout(monitorMenu, 5000);

        assertNotNull(menuActivity);
        menuActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        nuiteeActivity = null;
    }
}