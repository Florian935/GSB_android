package fr.cned.emdsgil.suividevosfrais.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.outils.MesOutils;

public class NuiteeActivity extends AppCompatActivity {

    // informations affichées dans l'activité
    private String moisMMM; // mois au format MMM
    private String moisMM; // mois au format MM
    private String annee; // annee au format aaaa
    private Integer qte;
    private TextView txtDateNuitee;
    private Controle controle;
    private final String idFrais = "NUI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuitee);
        setTitle("GSB : Frais Nuitées");
        this.txtDateNuitee = findViewById(R.id.txtDateNuitee);
        this.moisMMM = MesOutils.actualMonth(new Date());
        this.moisMM = MesOutils.actualMoisInNumeric(new Date());
        this.annee = MesOutils.actualYear(new Date());
        controle = Controle.getInstance(null);
        // valorisation des propriétés
        valoriseProprietes();
        // chargement des méthodes événementielles
        imgReturn_clic();
        cmdValider_clic();
        cmdPlus_clic();
        cmdMoins_clic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.retour_accueil))) {
            retourActivityPrincipale();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Valorisation des propriétés
     */
    private void valoriseProprietes() {
        // Affichage de la date actuelle
        String date = moisMMM + " " + annee;
        this.txtDateNuitee.setText(date);
        // récupération de la quantité pour ce frais
        qte = controle.getUnFraisForfait(idFrais);
        ((TextView)findViewById(R.id.txtNuitee)).setText(String.format(Locale.FRANCE, "%d", qte));
    }

    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgReturn_clic() {
        findViewById(R.id.imgNuiteeReturn).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton valider : enregistrement du frais forfait dans la BDD
     */
    private void cmdValider_clic() {
        findViewById(R.id.cmdNuiteeValider).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                controle.accesDonnees("updateFraisForfait", convertToJSONArray());
                retourActivityPrincipale();
            }
        });
    }

    /**
     * Sur le clic du bouton plus : ajout de 1 dans la quantité
     */
    private void cmdPlus_clic() {
        findViewById(R.id.cmdNuiteePlus).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte+=1;
                enregNewQte();
            }
        });
    }

    /**
     * Sur le clic du bouton moins : enlève 1 dans la quantité si c'est possible
     */
    private void cmdMoins_clic() {
        findViewById(R.id.cmdNuiteeMoins).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte = Math.max(0, qte-1) ; // suppression de 1 si possible
                enregNewQte();
            }
        }) ;
    }

    /**
     * Enregistrement dans la zone de texte de la nouvelle qte
     */
    private void enregNewQte() {
        // enregistrement dans la zone de texte
        ((TextView)findViewById(R.id.txtNuitee)).setText(String.format(Locale.FRANCE, "%d", qte));
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(NuiteeActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Conversion de l'identifiant, de la date et de l'id du frais forfait et de la quantité au format JSONArray
     * @return l'identifiant, la date sous la forme aaaamm, l'id du frais et la quantité au format JSONArray
     */
    public JSONArray convertToJSONArray(){
        List list = new ArrayList();
        // Création de l'identifiant 'mois' nécessaire pour effectuer la requête de récupération des frais dans la BDD
        String idMois = annee + moisMM;
        list.add(controle.getIdentifiantVisiteur());
        list.add(idMois);
        list.add(idFrais);
        list.add(qte);

        return new JSONArray(list);
    }
}
