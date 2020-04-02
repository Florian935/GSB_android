package fr.cned.emdsgil.suividevosfrais.vue;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import fr.cned.emdsgil.suividevosfrais.R;

import static org.junit.Assert.*;

public class MainActivityTest {

    // Activity qui sera lancée pour chaque tests
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    /**
     * Méthode appelée avant chaque test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    /**
     * Teste si à la création de l'activity menu, tous les objets graphiques sont bien créés
     */
    @Test
    public void onCreate() {
        TextView lblLoginAuth = mainActivity.findViewById(R.id.lblLoginAuth);
        TextView lblPasswordAuth = mainActivity.findViewById(R.id.lblPasswordAuth);
        TextView txtErrorAuth = mainActivity.findViewById(R.id.txtErrorAuth);
        EditText txtLoginAuth = mainActivity.findViewById(R.id.txtLoginAuth);
        EditText txtPwdAuth = mainActivity.findViewById(R.id.txtPwdAuth);
        Button cmdSeConnecter = mainActivity.findViewById(R.id.cmdSeConnecter);

        assertNotNull(lblLoginAuth);
        assertNotNull(lblPasswordAuth);
        assertNotNull(txtErrorAuth);
        assertNotNull(txtLoginAuth);
        assertNotNull(txtPwdAuth);
        assertNotNull(cmdSeConnecter);
        assertEquals("Identifiant", lblLoginAuth.getText());
        assertEquals("Mot de passe", lblPasswordAuth.getText());
        assertEquals("Se connecter", cmdSeConnecter.getText());
    }

    /**
     * Méthode appelée après chaque test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}