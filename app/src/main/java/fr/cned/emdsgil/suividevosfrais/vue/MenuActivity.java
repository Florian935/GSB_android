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
import java.util.Hashtable;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.modele.AccesDistant;
import fr.cned.emdsgil.suividevosfrais.modele.FraisMois;
import fr.cned.emdsgil.suividevosfrais.modele.Global;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.outils.Serializer;

public class MenuActivity extends AppCompatActivity {

    private Controle controle;
    public static AccesDistant accesDistant;

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
        this.accesDistant = new AccesDistant();
        // Récupération du contrôleur
        this.controle = Controle.getInstance(this);
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
        accesDistant.envoi("fraisForfait", convertToJSONArray());
        Log.d("fraisForfait", "****************" + convertToJSONArray());
        accesDistant.envoi("fraisHorsForfait", convertToJSONArray());
        Log.d("fraisHorsForfait", "****************" + convertToJSONArray());
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
                // envoi les informations sérialisées vers le serveur
                // en construction
            }
        });
    }

    /**
     * Conversion de l'identifiant au format JSONArray
     * @return l'identifiant au format JSONArray
     */
    public JSONArray convertToJSONArray(){
        List list = new ArrayList();
        list.add(controle.getIdentifiant());

        return new JSONArray(list);
    }
}
