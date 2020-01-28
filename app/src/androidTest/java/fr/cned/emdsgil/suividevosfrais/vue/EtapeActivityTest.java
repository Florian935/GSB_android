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

public class EtapeActivityTest {

    @Rule
    public ActivityTestRule<EtapeActivity> etapeActivityActivityTestRule = new ActivityTestRule<EtapeActivity>(EtapeActivity.class);

    private EtapeActivity etapeActivity = null;

    Instrumentation.ActivityMonitor monitorMenu = getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        etapeActivity = etapeActivityActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        ImageView imgEtapeReturn = etapeActivity.findViewById(R.id.imgEtapeReturn);
        Button cmdEtapePlus = etapeActivity.findViewById(R.id.cmdEtapePlus);
        Button cmdEtapeMoins = etapeActivity.findViewById(R.id.cmdEtapeMoins);
        Button cmdEtapeValider = etapeActivity.findViewById(R.id.cmdEtapeValider);

        assertNotNull(imgEtapeReturn);
        assertNotNull(cmdEtapePlus);
        assertNotNull(cmdEtapeMoins);
        assertNotNull(cmdEtapeValider);

        assertEquals("+", cmdEtapePlus.getText());
        assertEquals("-", cmdEtapeMoins.getText());
        assertEquals("Valider", cmdEtapeValider.getText());
    }

    @Test
    public void testLaunchOfMenuActivityOnButtonClick() {
        assertNotNull(etapeActivity.findViewById(R.id.imgEtapeReturn));

        onView(withId(R.id.imgEtapeReturn)).perform(click());
        Activity menuActivity = getInstrumentation().waitForMonitorWithTimeout(monitorMenu, 5000);

        assertNotNull(menuActivity);

        menuActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        etapeActivity = null;
    }
}