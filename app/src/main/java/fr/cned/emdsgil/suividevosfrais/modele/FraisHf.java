package fr.cned.emdsgil.suividevosfrais.modele;


/**
 * Classe métier contenant la description d'un frais hors forfait
 *
 */
public class FraisHf implements Comparable {

	private final Double montant;
	private final String libelle;
	private final String jour;
	private final int identifiant; // identifiant incrémenté de 1 à chaque frais HF ajoutés dans l'appli
	public Integer idFraisDansBdd; // id du frais HF dans la BDD
	
	public FraisHf(Double montant, String libelle, String jour, int identifiant, Integer idBdd) {
		this.montant = montant ;
		this.libelle = libelle ;
		this.jour = jour ;
		this.identifiant = identifiant;
		this.idFraisDansBdd = idBdd;
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

	public Integer getIdFraisDansBdd() { return idFraisDansBdd; }

	/**
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(Object o) {
		return ((FraisHf)o).getIdFraisDansBdd().compareTo(idFraisDansBdd);
	}
}
