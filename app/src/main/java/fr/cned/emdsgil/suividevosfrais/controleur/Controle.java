package fr.cned.emdsgil.suividevosfrais.controleur;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.modele.AccesDistant;
import fr.cned.emdsgil.suividevosfrais.modele.FraisForfait;
import fr.cned.emdsgil.suividevosfrais.modele.FraisHf;
import fr.cned.emdsgil.suividevosfrais.outils.MesOutils;
import fr.cned.emdsgil.suividevosfrais.vue.MenuActivity;

public final class Controle {

    // propriétés
    private static Controle instance = null;
    private static AccesDistant accesDistant;
    public static Context context;
    private String identifiantVisiteur;
    private int dernierIdFraisHf; // dernier id frais HF ajouté dans la BDD
    private String passwordBdd;
    private String login;
    private ArrayList<FraisForfait> lesFraisForfaits = new ArrayList<FraisForfait>();
    private Hashtable<String, Integer> lesFraisForfait = new Hashtable<>();
    private Hashtable<Integer, FraisHf> lesFraisHF = new Hashtable<>();

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

    public void setIdentifiantVisiteur(String id){
        identifiantVisiteur = id;
    }

    public String getIdentifiantVisiteur(){
        return identifiantVisiteur;
    }

    public int getDernierIdFraisHf() { return dernierIdFraisHf; }

    public void setDernierIdFraisHf(int dernierIdFraisHf) { this.dernierIdFraisHf = dernierIdFraisHf; }

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

    public Hashtable<Integer, FraisHf> getLesFraisHF() { return lesFraisHF; }

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

        // Si le couple identifiantVisiteur/mdp crypté correspond au couple identifiantVisiteur/mdp crypté dans la
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
     * Mise à jour  ou récupération dans la BDD en fonction des paramètres fournis
     * @param operation opération à effectuer
     * @param lesDonnees données fournies
     */
    public void accesDonnees(String operation, JSONArray lesDonnees){
        accesDistant.envoi(operation, lesDonnees);
    }

    /**
     * Valorise les frais forfaits du mois actuel
     * @param lesFraisForfait à valoriser
     */
    public void setLesFraisForfait(Hashtable<String, Integer> lesFraisForfait){
        this.lesFraisForfait = lesFraisForfait;
    }

    /**
     * Valorise les frais hors forfaits du mois actuel
     * @param lesFraisHF à valoriser
     */
    public void setLesFraisHorsForfait(Hashtable <Integer, FraisHf> lesFraisHF){
        this.lesFraisHF = lesFraisHF;;
    }

    /**
     * Permet d'ajouter un frais HF dans la liste des frais HF
     * @param unFraisHF a ajouter dans la liste
     */
    public void ajouterUnFraisHf(Integer idFraisHF, FraisHf unFraisHF){
        this.lesFraisHF.put(idFraisHF, unFraisHF);
    }

    /**
     * Permet de récupérer un frais forfait en fonction de la clé fournie
     * @param key permettant de savoir clé frais forfait on récupère
     * @return la quantité du frais forfait souhaité
     */
    public Integer getUnFraisForfait(String key){
        return this.lesFraisForfait.get(key);
    }

    /**
     * Supprime le frais HF passé dont l'id correspond à celui passé paramètre
     * @param id du frais HF à supprimer
     */
    public void supprFraisHF(Integer id){
        this.lesFraisHF.remove(id);
    }

    /**
     * Modification de l'affichage du DatePicker passé en paramètre en fonction des paramètres fournis
     *
     * @param datePicker à formater
     * @param afficheJours true ou false
     * @param afficheMois true ou false
     * @param afficheAnnee true ou false
     */
    public static void changeAfficheDate(DatePicker datePicker, boolean afficheJours, boolean afficheMois, boolean afficheAnnee) {
        try {
            Field f[] = datePicker.getClass().getDeclaredFields();
            for (Field field : f) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
                int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
                datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), null);
                if (daySpinnerId != 0)
                {
                    View daySpinner = datePicker.findViewById(daySpinnerId);
                    View monthSpinner = datePicker.findViewById(monthSpinnerId);
                    View yearSpinner = datePicker.findViewById(yearSpinnerId);
                    if (!afficheJours)
                    {
                        daySpinner.setVisibility(View.GONE);
                    }
                    if (!afficheMois){
                        monthSpinner.setVisibility(View.GONE);
                    }
                    if (!afficheAnnee) {
                        yearSpinner.setVisibility(View.GONE);
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        }
    }
}
