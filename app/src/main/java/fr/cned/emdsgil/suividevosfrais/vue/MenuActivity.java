package fr.cned.emdsgil.suividevosfrais.vue;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.modele.FraisMois;
import fr.cned.emdsgil.suividevosfrais.modele.Global;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.outils.MesOutils;
import fr.cned.emdsgil.suividevosfrais.outils.Serializer;

public class MenuActivity extends AppCompatActivity {

    private Controle controle; // Contient l'unique instance du contrôleur

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("GSB : Suivi des frais");
        // récupération des informations sérialisées
        recupSerialize();
        // chargement des méthodes événementielles
        cmdMenu_clic(((ImageButton)findViewById(R.id.cmdKm)), KmActivity.class);
        cmdMenu_clic(((ImageButton)findViewById(R.id.cmdHf)), HfActivity.class);
        cmdMenu_clic(((ImageButton)findViewById(R.id.cmdHfRecap)), HfRecapActivity.class);
        cmdMenu_clic(((ImageButton)findViewById(R.id.cmdNuitee)), NuiteeActivity.class);
        cmdMenu_clic(((ImageButton)findViewById(R.id.cmdEtape)), EtapeActivity.class);
        cmdMenu_clic(((ImageButton)findViewById(R.id.cmdRepas)), RepasActivity.class);
        cmdTransfert_clic();
        // Récupération du contrôleur
        this.controle = Controle.getInstance(null);
        recupFrais();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Récupère la sérialisation si elle existe
     */
    private void recupSerialize() {
        /* Pour éviter le warning "Unchecked cast from Object to Hash" produit par un casting direct :
         * Global.listFraisMois = (Hashtable<Integer, FraisMois>) Serializer.deSerialize(Global.filename, MenuActivity.this);
         * On créé un Hashtable générique <?,?> dans lequel on récupère l'Object retourné par la méthode deSerialize, puis
         * on cast chaque valeur dans le type attendu.
         * Seulement ensuite on affecte cet Hastable à Global.listFraisMois.
        */
        Hashtable<?, ?> monHash = (Hashtable<?, ?>) Serializer.deSerialize(MenuActivity.this);
        if (monHash != null) {
            Hashtable<Integer, FraisMois> monHashCast = new Hashtable<>();
            for (Hashtable.Entry<?, ?> entry : monHash.entrySet()) {
                monHashCast.put((Integer) entry.getKey(), (FraisMois) entry.getValue());
            }
            Global.listFraisMois = monHashCast;
        }
        // si rien n'a été récupéré, il faut créer la liste
        if (Global.listFraisMois == null) {
            Global.listFraisMois = new Hashtable<>();
            /* Retrait du type de l'HashTable (Optimisation Android Studio)
			 * Original : Typage explicit =
			 * Global.listFraisMois = new Hashtable<Integer, FraisMois>();
			*/
        }
    }

    /**
     * Récupération des frais forfaits du visiteur
     */
    private void recupFrais(){
        // Récupération des frais forfaits et hors forfaits du visiteur
        controle.accesDonnees("getFraisHF", convertToJSONArray());
        controle.accesDonnees("getFraisForfait", convertToJSONArray());
    }

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante
     */
    private void cmdMenu_clic(ImageButton button, final Class classe) {
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // ouvre l'activité
                Intent intent = new Intent(MenuActivity.this, classe);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * Cas particulier du bouton pour le transfert d'informations vers le serveur
     */
    private void cmdTransfert_clic() {
        findViewById(R.id.cmdTransfert).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    /**
     * Conversion de l'identifiant et de la date au format JSONArray
     * @return l'identifiant et la date sous la forme aaaamm au format JSONArray
     */
    public JSONArray convertToJSONArray(){
        List list = new ArrayList();
        list.add(controle.getIdentifiant());
        // Création de l'identifiant 'mois' nécessaire pour effectuer la requête de récupération des frais dans la BDD
        String idMois = MesOutils.actualYear(new Date()) + MesOutils.actualMoisInNumeric(new Date());
        list.add(idMois);

        return new JSONArray(list);
    }
}
