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

public class KmActivityTest {

    @Rule
    public ActivityTestRule<KmActivity> kmActivityActivityTestRule = new ActivityTestRule<KmActivity>(KmActivity.class);

    private KmActivity kmActivity = null;

    Instrumentation.ActivityMonitor monitorMenu = getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        kmActivity = kmActivityActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        ImageView imgKmReturn = kmActivity.findViewById(R.id.imgKmReturn);
        Button cmdKmPlus = kmActivity.findViewById(R.id.cmdKmPlus);
        Button cmdKmMoins = kmActivity.findViewById(R.id.cmdKmMoins);
        Button cmdKmValider = kmActivity.findViewById(R.id.cmdKmValider);

        assertNotNull(imgKmReturn);
        assertNotNull(cmdKmPlus);
        assertNotNull(cmdKmMoins);
        assertNotNull(cmdKmValider);

        assertEquals("+", cmdKmPlus.getText());
        assertEquals("-", cmdKmMoins.getText());
        assertEquals("Valider", cmdKmValider.getText());
    }

    @Test
    public void testLaunchOfMenuActivityOnButtonClick() {
        assertNotNull(kmActivity.findViewById(R.id.imgKmReturn));

        onView(withId(R.id.imgKmReturn)).perform(click());
        Activity menuActivity = getInstrumentation().waitForMonitorWithTimeout(monitorMenu, 5000);

        assertNotNull(menuActivity);
        menuActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        kmActivity = null;
    }
}