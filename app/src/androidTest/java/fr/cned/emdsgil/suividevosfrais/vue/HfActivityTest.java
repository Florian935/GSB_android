package fr.cned.emdsgil.suividevosfrais.vue;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

public class HfActivityTest {

    // Activity qui sera lancée pour chaque tests
    @Rule
    public ActivityTestRule<HfActivity> hfActivityActivityTestRule = new ActivityTestRule<HfActivity>(HfActivity.class);

    private HfActivity hfActivity = null;

    // Valorisation du monitor permettant de réaliser la simulation de l'ouverture de l'Activity souhaitée
    Instrumentation.ActivityMonitor monitorMenu = getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);


    /**
     * Méthode appelée avant chaque test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        hfActivity = hfActivityActivityTestRule.getActivity();
    }

    /**
     * Teste si à la création de l'activity menu, tous les objets graphiques sont bien créés
     */
    @Test
    public void onCreate() {
        ImageView imgHfReturn = hfActivity.findViewById(R.id.imgHfReturn);
        DatePicker datHf = hfActivity.findViewById(R.id.datHf);
        TextView txtDateHf = hfActivity.findViewById(R.id.txtDateHf);
        TextView txtHf = hfActivity.findViewById(R.id.txtHf);
        EditText txtHfMotif = hfActivity.findViewById(R.id.txtHfMotif);
        Button cmdHfAjouter = hfActivity.findViewById(R.id.cmdHfAjouter);

        assertNotNull(imgHfReturn);
        assertNotNull(datHf);
        assertNotNull(txtDateHf);
        assertNotNull(txtHf);
        assertNotNull(txtHfMotif);
        assertNotNull(cmdHfAjouter);

        assertEquals("Ajouter", cmdHfAjouter.getText());
    }

    /**
     * Teste si au clic sur le bouton permettant de retourner à la vue du menu, l'activity
     * correspondante s'ouvre
     */
    @Test
    public void testLaunchOfMenuActivityOnButtonClick() {
        assertNotNull(hfActivity.findViewById(R.id.imgHfReturn));

        onView(withId(R.id.imgHfReturn)).perform(click());
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
        hfActivity = null;
    }
}