package fr.cned.emdsgil.suividevosfrais.vue;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Date;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.modele.FraisHf;
import fr.cned.emdsgil.suividevosfrais.outils.MesOutils;

public class HfRecapActivity extends AppCompatActivity {

	// informations affichées dans l'activité
	private String moisMMM; // mois au format MMM
	private String annee; // annee au format aaaa
	private Controle controle; // Contient l'unique instance du contrôleur

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf_recap);
        setTitle("GSB : Récap Frais HF");
		this.moisMMM = MesOutils.actualMonth(new Date());
		this.annee = MesOutils.actualYear(new Date());
		// Récupération du contrôleur
		this.controle = Controle.getInstance(this);
		// Affichage de la date actuelle
		((TextView)findViewById(R.id.txtDateHfRecap)).setText(moisMMM + " " + annee);
		// valorisation des propriétés
		afficheListe();
        // chargement des méthodes événementielles
		imgReturn_clic();
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
	 * Affiche la liste des frais hors forfaits de la date sélectionnée
	 */
	private void afficheListe() {
		ListView listView = (ListView)findViewById(R.id.lstHfRecap);
		Collections.sort(controle.getLesFraisHF(), Collections.<FraisHf>reverseOrder());
		FraisHfAdapter adapter = new FraisHfAdapter(HfRecapActivity.this, controle.getLesFraisHF());
		listView.setAdapter(adapter);
	}
	
	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	findViewById(R.id.imgHfRecapReturn).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale();
    		}
    	}) ;
    }

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfRecapActivity.this, MenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
