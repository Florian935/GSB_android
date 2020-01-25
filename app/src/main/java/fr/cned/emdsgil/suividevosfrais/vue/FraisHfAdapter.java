package fr.cned.emdsgil.suividevosfrais.vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.modele.FraisHf;
import fr.cned.emdsgil.suividevosfrais.R;

public class FraisHfAdapter extends BaseAdapter {

	private final Hashtable<Integer, FraisHf> lesFraisHF; // liste des frais hors forfait du mois
	private final LayoutInflater inflater;
	private Controle controle; // Contient l'unique instance du contrôleur

    /**
	 * Constructeur de l'adapter pour valoriser les propriétés
     * @param context Accès au contexte de l'application
     * @param lesFraisHF Liste des frais hors forfait
     */
	public FraisHfAdapter(Context context, Hashtable<Integer, FraisHf> lesFraisHF) {
		inflater = LayoutInflater.from(context);
		this.lesFraisHF = lesFraisHF;
		// Récupération du contrôleur
		this.controle = Controle.getInstance(null);
    }
	
	/**
	 * retourne le nombre d'éléments de la listview
	 */
	@Override
	public int getCount() {
		return lesFraisHF.size();
	}

	/**
	 * retourne l'item de la listview à un index précis
	 */
	@Override
	public Object getItem(int index) {
		return lesFraisHF.get(index);
	}

	/**
	 * retourne l'index de l'élément actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}
	
	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		final Integer idFraisHF = getIdFraisHF(index);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_liste, parent, false);
			holder.txtListJour = convertView.findViewById(R.id.txtListJour);
			holder.txtListMontant = convertView.findViewById(R.id.txtListMontant);
			holder.txtListMotif = convertView.findViewById(R.id.txtListMotif);
			holder.cmdSuppHf = convertView.findViewById(R.id.cmdSuppHf);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.txtListJour.setText(lesFraisHF.get(idFraisHF).getJour());
		holder.txtListMontant.setText(String.format(Locale.FRANCE, "%.2f", lesFraisHF.get(idFraisHF).getMontant()));
		holder.txtListMotif.setText(lesFraisHF.get(idFraisHF).getLibelle());
		holder.cmdSuppHf.setTag(idFraisHF);
		// gestion de l'événement clic sur le bouton de suppression
		holder.cmdSuppHf.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				int indice = (int)v.getTag();
				// Suppression du frais HF sur lequel on clic dans la BDD et dans la liste
				controle.accesDonnees("suppressionFraisHF", convertToJSONArray(idFraisHF));
				controle.supprFraisHF(idFraisHF);
				// Rafraichi la liste visuelle
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	/**
	 * Retourne l'identifiant du frais HF stocké dans la BDD en fonction de l'identifiant du frais HF
	 * implémenté dans la BDD
	 * @param identifiant fourni pour retrouver le frais HF correspondant
	 * @return l'id du frais HF correspondant dans la BDD
	 */
	public Integer getIdFraisHF(int identifiant){
		for (Hashtable.Entry<?, ?> entry : this.lesFraisHF.entrySet()) {
			int idFraisHF = this.lesFraisHF.get(entry.getKey()).getIdentifiant();
			if (identifiant == (idFraisHF)){
				return (Integer)entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Conversion de l'id du frais hors forfait format JSONArray
	 * @return l'identifiant au format JSONArray
	 */
	public JSONArray convertToJSONArray(Integer idFrais){
		List list = new ArrayList();
		list.add(idFrais);

		return new JSONArray(list);
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour;
		TextView txtListMontant;
		TextView txtListMotif;
		ImageButton cmdSuppHf;
	}
}
