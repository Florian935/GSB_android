package fr.cned.emdsgil.suividevosfrais.vue;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.ImageButton;

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
import static org.junit.Assert.assertEquals;

public class MenuActivityTest {

    // Activity qui sera lancée pour chaque tests
    @Rule
    public ActivityTestRule<MenuActivity> menuActivityActivityTestRule = new ActivityTestRule<MenuActivity>(MenuActivity.class);

    private MenuActivity menuActivity = null;

    // Valorisation des monitor permettant de réaliser la simulation des ouvertures des Activity souhaitées
    Instrumentation.ActivityMonitor monitorEtape = getInstrumentation().addMonitor(EtapeActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorNuitee = getInstrumentation().addMonitor(NuiteeActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorKm = getInstrumentation().addMonitor(KmActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorRepas = getInstrumentation().addMonitor(RepasActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorFraisHf = getInstrumentation().addMonitor(HfActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorFraisHfRecap = getInstrumentation().addMonitor(HfRecapActivity.class.getName(), null, false);

    /**
     * Méthode appelée avant chaque test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        menuActivity = menuActivityActivityTestRule.getActivity();
    }

    /**
     * Teste si à la création de l'activity menu, tous les objets graphiques sont bien créés
     */
    @Test
    public void onCreate() {
        ImageButton cmdKm = menuActivity.findViewById(R.id.cmdKm);
        ImageButton cmdRepas = menuActivity.findViewById(R.id.cmdRepas);
        ImageButton cmdNuitee = menuActivity.findViewById(R.id.cmdNuitee);
        ImageButton cmdEtape = menuActivity.findViewById(R.id.cmdEtape);
        ImageButton cmdHf = menuActivity.findViewById(R.id.cmdHf);
        ImageButton cmdHfRecap = menuActivity.findViewById(R.id.cmdHfRecap);

        assertNotNull(cmdKm);
        assertNotNull(cmdRepas);
        assertNotNull(cmdNuitee);
        assertNotNull(cmdEtape);
        assertNotNull(cmdHf);
        assertNotNull(cmdHfRecap);
    }

    /**
     * Teste si au clic sur le bouton permettant d'accéder à la vue des frais d'étapes, l'activity
     * correspondante s'ouvre
     */
    @Test
    public void testLaunchOfEtapeActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdEtape));

        onView(withId(R.id.cmdEtape)).perform(click());
        Activity etapeActivity = getInstrumentation().waitForMonitorWithTimeout(monitorEtape, 5000);

        assertNotNull(etapeActivity);

        etapeActivity.finish();
    }

    /**
     * Teste si au clic sur le bouton permettant d'accéder à la vue des frais de nuitées, l'activity
     * correspondante s'ouvre
     */
    @Test
    public void testLaunchOfNuiteeeActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdNuitee));

        onView(withId(R.id.cmdNuitee)).perform(click());
        Activity nuiteeActivity = getInstrumentation().waitForMonitorWithTimeout(monitorNuitee, 5000);

        assertNotNull(nuiteeActivity);

        nuiteeActivity.finish();
    }

    /**
     * Teste si au clic sur le bouton permettant d'accéder à la vue des frais kilométriques, l'activity
     * correspondante s'ouvre
     */
    @Test
    public void testLaunchOfKmActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdKm));

        onView(withId(R.id.cmdKm)).perform(click());
        Activity kmActivity = getInstrumentation().waitForMonitorWithTimeout(monitorKm, 5000);

        assertNotNull(kmActivity);

        kmActivity.finish();
    }

    /**
     * Teste si au clic sur le bouton permettant d'accéder à la vue des frais de repas, l'activity
     * correspondante s'ouvre
     */
    @Test
    public void testLaunchOfRepasActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdRepas));

        onView(withId(R.id.cmdRepas)).perform(click());
        Activity repasActivity = getInstrumentation().waitForMonitorWithTimeout(monitorRepas, 5000);

        assertNotNull(repasActivity);

        repasActivity.finish();
    }

    /**
     * Teste si au clic sur le bouton permettant d'accéder à la vue des frais HF, l'activity
     * correspondante s'ouvre
     */
    @Test
    public void testLaunchOfHfActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdHf));

        onView(withId(R.id.cmdHf)).perform(click());
        Activity hfActivity = getInstrumentation().waitForMonitorWithTimeout(monitorFraisHf, 5000);

        assertNotNull(hfActivity);

        hfActivity.finish();
    }

    /**
     * Teste si au clic sur le bouton permettant d'accéder à la vue du récapitulatif des frais HF,
     * l'activity correspondante s'ouvre
     */
    @Test
    public void testLaunchOfHfRecapActivityOnButtonClick() {
        assertNotNull(menuActivity.findViewById(R.id.cmdHfRecap));

        onView(withId(R.id.cmdHfRecap)).perform(click());
        Activity hfRecapActivity = getInstrumentation().waitForMonitorWithTimeout(monitorFraisHfRecap, 5000);

        assertNotNull(hfRecapActivity);

        hfRecapActivity.finish();
    }

    /**
     * Méthode appelée après chaque test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        menuActivity = null;
    }
}