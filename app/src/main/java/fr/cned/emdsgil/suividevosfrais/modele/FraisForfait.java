package fr.cned.emdsgil.suividevosfrais.modele;


/**
 * Classe métier contenant les informations des frais forfait du mois
 */
public class FraisForfait {

    private String idFraisForfait; // id du frais forfait
    private Integer quantite; // quantité du frais forfait

    // Constructeur
    public FraisForfait(String idFraisForfait, Integer quantite){
        super();
        this.idFraisForfait = idFraisForfait;
        this.quantite = quantite;
    }

    public String getIdFraisForfait() {
        return idFraisForfait;
    }

    public void setIdFraisForfait(String idFraisForfait) {
        this.idFraisForfait = idFraisForfait;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
