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

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void onCreate() {
        TextView lblLoginAuth = mainActivity.findViewById(R.id.lblLoginAuth);
        TextView lblPasswordAuth = mainActivity.findViewById(R.id.lblPasswordAuth);
        TextView txtErrorAuth = mainActivity.findViewById(R.id.txtErrorAuth);
        EditText txtLoginAuth = mainActivity.findViewById(R.id.txtLoginAuth);
        EditText txtPwdAuth = mainActivity.findViewById(R.id.txtPwdAuth);
        Button cmdSeConnecter = mainActivity.findViewById(R.id.cmdSeConnecter);

        assertNotNull(lblLoginAuth.getText());
        assertNotNull(lblPasswordAuth.getText());
        assertNotNull(txtErrorAuth.getText());
        assertNotNull(txtLoginAuth);
        assertNotNull(txtPwdAuth);
        assertNotNull(cmdSeConnecter);
        assertEquals("Identifiant", lblLoginAuth.getText());
        assertEquals("Mot de passe", lblPasswordAuth.getText());
        assertEquals("Se connecter", cmdSeConnecter.getText());
    }

    @Test
    public void convertToJSONArray() {
    }
}