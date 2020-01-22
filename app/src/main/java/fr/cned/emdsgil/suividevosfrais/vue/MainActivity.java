package fr.cned.emdsgil.suividevosfrais.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.cned.emdsgil.suividevosfrais.R;

public class MainActivity extends AppCompatActivity {

    // informations affichées dans l'activité
    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("GSB : Connexion");
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
                password = ((EditText)findViewById(R.id.txtPwdAuth)).getText().toString();
                /* TODO: dans le if, il faut appeler une méthode qui vérifie que le couple
                    identifiant/password existe dans la BDD et est correct
                */
                // Si l'identifiant et le mdp sont corrects, on affiche l'activité du menu
                if (login.equals("dandre")){
                    accesMenuActivity();
                } else {
                    // On indique à l'utilisateur que l'identifiant ou le mot de passe est incorrect
                    ((TextView)findViewById(R.id.txtErrorAuth)).setText(R.string.error_authentification);
                }
            }
        });
    }

    /**
     * Accès à l'activité principale (le menu) si l'authentification est réussie
     */
    private void accesMenuActivity() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
