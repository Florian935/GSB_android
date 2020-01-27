package fr.cned.emdsgil.suividevosfrais.vue;

import android.app.Activity;
import android.app.Instrumentation;

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

public class MenuActivityTest {

    @Rule
    public ActivityTestRule<MenuActivity> menuActivityActivityTestRule = new ActivityTestRule<MenuActivity>(MenuActivity.class);

    private MenuActivity menuActivity = null;

    Instrumentation.ActivityMonitor monitorEtape = getInstrumentation().addMonitor(EtapeActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorNuitee = getInstrumentation().addMonitor(NuiteeActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorKm = getInstrumentation().addMonitor(KmActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorRepas = getInstrumentation().addMonitor(RepasActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorFraisHf = getInstrumentation().addMonitor(HfActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorFraisHfRecap = getInstrumentation().addMonitor(HfRecapActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        menuActivity = menuActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfEtapeActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdEtape));

        onView(withId(R.id.cmdEtape)).perform(click());
        Activity etapeActivity = getInstrumentation().waitForMonitorWithTimeout(monitorEtape, 5000);

        assertNotNull(etapeActivity);

        etapeActivity.finish();
    }

    @Test
    public void testLaunchOfNuiteeeActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdNuitee));

        onView(withId(R.id.cmdNuitee)).perform(click());
        Activity nuiteeActivity = getInstrumentation().waitForMonitorWithTimeout(monitorNuitee, 5000);

        assertNotNull(nuiteeActivity);

        nuiteeActivity.finish();
    }

    @Test
    public void testLaunchOfKmActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdKm));

        onView(withId(R.id.cmdKm)).perform(click());
        Activity kmActivity = getInstrumentation().waitForMonitorWithTimeout(monitorKm, 5000);

        assertNotNull(kmActivity);

        kmActivity.finish();
    }

    @Test
    public void testLaunchOfRepasActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdRepas));

        onView(withId(R.id.cmdRepas)).perform(click());
        Activity repasActivity = getInstrumentation().waitForMonitorWithTimeout(monitorRepas, 5000);

        assertNotNull(repasActivity);

        repasActivity.finish();
    }

    @Test
    public void testLaunchOfHfActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdHf));

        onView(withId(R.id.cmdHf)).perform(click());
        Activity hfActivity = getInstrumentation().waitForMonitorWithTimeout(monitorFraisHf, 5000);

        assertNotNull(hfActivity);

        hfActivity.finish();
    }

    @Test
    public void testLaunchOfHfRecapActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdHfRecap));

        onView(withId(R.id.cmdHfRecap)).perform(click());
        Activity hfRecapActivity = getInstrumentation().waitForMonitorWithTimeout(monitorFraisHfRecap, 5000);

        assertNotNull(hfRecapActivity);

        hfRecapActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        menuActivity = null;
    }

    @Test
    public void onCreate() {
    }
}