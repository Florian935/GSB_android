package fr.cned.emdsgil.suividevosfrais.controleur;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.modele.AccesDistant;
import fr.cned.emdsgil.suividevosfrais.modele.FraisForfait;
import fr.cned.emdsgil.suividevosfrais.modele.Global;
import fr.cned.emdsgil.suividevosfrais.outils.MesOutils;
import fr.cned.emdsgil.suividevosfrais.vue.MainActivity;
import fr.cned.emdsgil.suividevosfrais.vue.MenuActivity;

public final class Controle {

    // propriétés
    private static Controle instance = null;
    private static String nomFic = "saveprofil";
    private static AccesDistant accesDistant;
    public static Context context;
    private static MainActivity mainActivity;
    private String identifiant;
    private String passwordBdd;
    private String login;
    private ArrayList<FraisForfait> lesFraisForfaits = new ArrayList<FraisForfait>();
    private Hashtable<String, Integer> lesFraisForfait = new Hashtable<>();

    /**
     * Constructeur privé
     */
    private Controle() {
        super();
    }

    /**
     * Création de l'instance unique de Controle
     * @return instance
     */
    public static final Controle getInstance(Context context){
        if(Controle.instance==null){
            Controle.context = context;
            Controle.instance = new Controle();
            accesDistant = new AccesDistant();
        }
        return Controle.instance;
    }

    public void setIdentifiant(String id){
        identifiant = id;
    }

    public String getIdentifiant(){
        return identifiant;
    }

    public void setPasswordBdd(String pwd){
        passwordBdd = pwd;
    }

    public String getPasswordBdd(){
        return passwordBdd;
    }

    public void setLogin(String log){
        login = log;
    }

    public String getLogin(){
        return login;
    }

    /**
     * Vérification du login et du mdp entré par le visiteur lors de l'authentification
     *
     * @param login login entré par le visiteur
     * @param password mdp entré par le visiteur
     * @return
     */
    public boolean coupleLoginPwdCorrect(String login, String password) {
        // On crypte le mot de passe passé en paramètre
        String passCrypte = MesOutils.hashString(password);

        // Si le couple identifiant/mdp crypté correspond au couple identifiant/mdp crypté dans la
        // bdd, on retourne true
        if (login.equals(this.login) && passCrypte.equals(this.passwordBdd)) {
            return true;
        }
        return false;
    }

    public void accesMenuActivity() {
        Intent intent = new Intent(this.context, MenuActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // startActivity(intent);
        Controle.this.context.startActivity(intent);
    }


    /**
     * Récupération dans la BDD
     * @param operation opération à effectuer
     * @param lesDonnees données fournies
     */
    public void recupDonnees(String operation, JSONArray lesDonnees){
        accesDistant.envoi(operation, lesDonnees);
    }

    /**
     * Mise à jour dans la BDD
     * @param operation opération à effectuer
     * @param lesDonnees données fournies
     */
    public void updateDonnees(String operation, JSONArray lesDonnees){
        accesDistant.envoi(operation, lesDonnees);
    }

    /**
     * Valorise les frais forfaits du mois actuel
     * @param lesFraisForfait
     */
    public void setLesFraisForfait(Hashtable<String, Integer> lesFraisForfait){
        this.lesFraisForfait = lesFraisForfait;
    }

    /**
     * Permet de récupérer un frais forfait en fonction de la clé fournie
     * @param key permettant de savoir clé frais forfait on récupère
     * @return la quantité du frais forfait souhaité
     */
    public Integer getUnFraisForfait(String key){
        return this.lesFraisForfait.get(key);
    }
}
