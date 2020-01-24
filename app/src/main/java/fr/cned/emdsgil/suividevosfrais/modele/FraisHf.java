package fr.cned.emdsgil.suividevosfrais.modele;


/**
 * Classe métier contenant la description d'un frais hors forfait
 *
 */
public class FraisHf {

	private final Double montant;
	private final String libelle;
	private final String jour;
	
	public FraisHf(Double montant, String libelle, String jour) {
		this.montant = montant ;
		this.libelle = libelle ;
		this.jour = jour ;
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

}
