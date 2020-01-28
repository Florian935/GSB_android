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

    // Activity qui sera lancée pour chaque tests
    @Rule
    public ActivityTestRule<HfRecapActivity> hfRecapActivityActivityTestRule = new ActivityTestRule<HfRecapActivity>(HfRecapActivity.class);

    private HfRecapActivity hfRecapActivity = null;

    // Valorisation du monitor permettant de réaliser la simulation de l'ouverture de l'Activity souhaitée
    Instrumentation.ActivityMonitor monitorMenu = getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);


    /**
     * Méthode appelée avant chaque test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        hfRecapActivity = hfRecapActivityActivityTestRule.getActivity();
    }

    /**
     * Teste si à la création de l'activity menu, tous les objets graphiques sont bien créés
     */
    @Test
    public void onCreate() {
        ImageView imgHfRecapReturn = hfRecapActivity.findViewById(R.id.imgHfRecapReturn);
        TextView txtDateHfRecap = hfRecapActivity.findViewById(R.id.txtDateHfRecap);
        ListView lstHfRecap = hfRecapActivity.findViewById(R.id.lstHfRecap);

        assertNotNull(imgHfRecapReturn);
        assertNotNull(txtDateHfRecap);
        assertNotNull(lstHfRecap);
    }

    /**
     * Teste si au clic sur le bouton permettant de retourner à la vue du menu, l'activity
     * correspondante s'ouvre
     */
    @Test
    public void testLaunchOfMenuActivityOnButtonClick() {
        assertNotNull(hfRecapActivity.findViewById(R.id.imgHfRecapReturn));

        onView(withId(R.id.imgHfRecapReturn)).perform(click());
        Activity menuActivity = getInstrumentation().waitForMonitorWithTimeout(monitorMenu, 5000);

        assertNotNull(menuActivity);
        menuActivity.finish();
    }

    /**
     * Méthode appelée après chaque test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        hfRecapActivity = null;
    }
}