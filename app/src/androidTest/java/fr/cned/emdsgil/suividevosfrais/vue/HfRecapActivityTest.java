package fr.cned.emdsgil.suividevosfrais.vue;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class HfRecapActivityTest {

    @Rule
    public ActivityTestRule<HfRecapActivity> hfRecapActivityActivityTestRule = new ActivityTestRule<HfRecapActivity>(HfRecapActivity.class);

    private HfRecapActivity hfRecapActivity = null;

    Instrumentation.ActivityMonitor monitorMenu = getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {
        hfRecapActivity = hfRecapActivityActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        ImageView imgHfRecapReturn = hfRecapActivity.findViewById(R.id.imgHfRecapReturn);
        TextView txtDateHfRecap = hfRecapActivity.findViewById(R.id.txtDateHfRecap);
        ListView lstHfRecap = hfRecapActivity.findViewById(R.id.lstHfRecap);

        assertNotNull(imgHfRecapReturn);
        assertNotNull(txtDateHfRecap);
        assertNotNull(lstHfRecap);
    }

    @Test
    public void testLaunchOfMenuActivityOnButtonClick() {
        assertNotNull(hfRecapActivity.findViewById(R.id.imgHfRecapReturn));

        onView(withId(R.id.imgHfRecapReturn)).perform(click());
        Activity menuActivity = getInstrumentation().waitForMonitorWithTimeout(monitorMenu, 5000);

        assertNotNull(menuActivity);
        menuActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        hfRecapActivity = null;
    }
}