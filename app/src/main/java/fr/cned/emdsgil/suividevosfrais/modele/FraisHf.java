package fr.cned.emdsgil.suividevosfrais.modele;


/**
 * Classe métier contenant la description d'un frais hors forfait
 *
 */
public class FraisHf {

	private final Double montant;
	private final String libelle;
	private final String jour;
	private final int identifiant; // identifiant incrémenté de 1 à chaque frais HF ajoutés dans l'appli
	
	public FraisHf(Double montant, String libelle, String jour, int identifiant) {
		this.montant = montant ;
		this.libelle = libelle ;
		this.jour = jour ;
		this.identifiant = identifiant;
	}

	public double getMontant() {
		return montant;
	}

	public String getLibelle() {
		return libelle;
	}

	public String getJour() {
		return jour;
	}

	public int getIdentifiant() { return identifiant; }
}
