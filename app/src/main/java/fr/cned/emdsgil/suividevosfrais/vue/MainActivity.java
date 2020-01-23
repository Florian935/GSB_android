package fr.cned.emdsgil.suividevosfrais.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.modele.AccesDistant;
import fr.cned.emdsgil.suividevosfrais.modele.Global;
import fr.cned.emdsgil.suividevosfrais.outils.MesOutils;

public class MainActivity extends AppCompatActivity {

    // informations affichées dans l'activité
    public static String login; // identifiant entré par le visiteur
    public static String passwordEntre; // mot de passe entré par le visiteur
    public static AccesDistant accesDistant;
    private Controle controle;
    public static TextView txtErrorAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("GSB : Connexion");
        this.accesDistant = new AccesDistant();
        // Créé le contrôleur
        controle = Controle.getInstance(null);
        Controle.context = this;
        this.txtErrorAuth = (TextView)findViewById(R.id.txtErrorAuth);
        // chargement des méthodes événementielles
        cmdSeConnecter_clic();
    }

    /**
     * Sur le clic du bouton se connecter: on vérifie si le couple identifiant/mot de passe est
     * correct. Si c'est correct, le visiteur a accès à son espace personnel, sinon, retour à
     * l'activité de connexion.
     */
    private void cmdSeConnecter_clic() {
        findViewById(R.id.cmdSeConnecter).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Récupération de l'identifiant et du mot de passe entrés
                login = ((EditText)findViewById(R.id.txtLoginAuth)).getText().toString();
                passwordEntre = ((EditText)findViewById(R.id.txtPwdAuth)).getText().toString();
                accesDistant.envoi("recupInfos", convertToJSONArray());
            }
        });
    }

    /**
     * Conversion du login et du mdp entrés au format JSONArray
     * @return login et mdp au format JSONArray
     */
    public JSONArray convertToJSONArray(){
        List list = new ArrayList();
        list.add(login);
        list.add(MesOutils.hashString(passwordEntre));

        return new JSONArray(list);
    }
}
