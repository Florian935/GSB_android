package fr.cned.emdsgil.suividevosfrais.vue;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.modele.FraisHf;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.outils.MesOutils;

public class HfActivity extends AppCompatActivity {


	// informations affichées dans l'activité
	private String libelle;
	private Double montant;
	private String jour;
	private FraisHf unFraisHF; // Frais ajouté
	private Controle controle; // Contient l'unique instance du contrôleur

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf);
        setTitle("GSB : Frais HF");
		// Récupération du contrôleur
		this.controle = Controle.getInstance(null);
        // modification de l'affichage du DatePicker
        controle.changeAfficheDate((DatePicker) findViewById(R.id.datHf), true, false, false) ;
		// mise à 0 du montant
		((EditText)findViewById(R.id.txtHf)).setText("0") ;
        // chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdAjouter_clic() ;
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
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	findViewById(R.id.imgHfReturn).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale();
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton ajouter : enregistrement dans la liste des fraisHf di frais HF ajouté
	 * et ajout du frais HF dans la BDD
     */
    private void cmdAjouter_clic() {
    	findViewById(R.id.cmdHfAjouter).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			enregListe();
    			controle.accesDonnees("ajoutFraisHF", convertToJSONArray());
    			retourActivityPrincipale();
    		}
    	}) ;    	
    }
    
	/**
	 * Enregistrement dans la liste
	 */
	private void enregListe() {
		// récupération des informations saisies
		Integer jourSelectionne = ((DatePicker)findViewById(R.id.datHf)).getDayOfMonth();
		this.jour = MesOutils.convertIntDayToStringDay(jourSelectionne);
		this.libelle = ((EditText)findViewById(R.id.txtHfMotif)).getText().toString();
		// Conversion au format double du montant saisi
		String montantSaisie = ((EditText)findViewById(R.id.txtHf)).getText().toString();
		this.montant = Double.valueOf(montantSaisie);
		this.unFraisHF = new FraisHf(this.montant, this.libelle, this.jour);
	}

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfActivity.this, MenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	/**
	 * Conversion de l'identifiant, de la date et de l'id du frais forfait et de la quantité au format JSONArray
	 * @return l'identifiant, la date sous la forme aaaamm, l'id du frais et la quantité au format JSONArray
	 */
	public JSONArray convertToJSONArray(){
		List list = new ArrayList();
		String annee = MesOutils.actualYear(new Date());
		String mois = MesOutils.actualMoisInNumeric(new Date());
		// Création de l'identifiant 'mois' nécessaire pour effectuer la requête de récupération des
		// frais dans la BDD (format: aaaamm)
		String idMois = annee + mois;
		// Création de la valeur date à ajouter dans la colonne date (format: aaaa-mm-jj)
		String date = annee + "-" + mois + "-" + this.jour;
		list.add(controle.getIdentifiant());
		list.add(idMois);
		list.add(this.libelle);
		list.add(date);
		list.add(this.montant);

		return new JSONArray(list);
	}
}
