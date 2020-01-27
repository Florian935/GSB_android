package fr.cned.emdsgil.suividevosfrais.modele;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.outils.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.outils.AsyncResponse;
import fr.cned.emdsgil.suividevosfrais.vue.MainActivity;

public class AccesDistant implements AsyncResponse {

    // adresse du serveur
    private static final String SERVERADDR = "http://192.168.1.31/GSB/accesBddAndroid/serveurgsb.php";

    // propriétés
    private Controle controle;

    /**
     * Constructeur
     */
    public AccesDistant(){
        controle = Controle.getInstance(null);
    }

    /**
     * Retour du serveur HTTP
     * @param output
     */
    @Override
    public void processFinish(String output) {
        // pour vérification, affiche le contenu du retour dans la console
        Log.d("serveur", "************" + output);
        // découpage du message reçu
        String[] message = output.split("%");
        // contrôle si le retour est correct (au moins 2 cases)
        if(message.length>1) {
            // Récupération de l'id, du mdp et du login du visiteur qui se connecte
            if (message[0].equals("recupInfos")) {
                try{
                    // Récupération des informations
                    JSONObject info = new JSONObject(message[1]);
                    String identifiant = info.getString("id");
                    String passwordBdd = info.getString("mdp");
                    String login = info.getString("login");
                    controle.setIdentifiantVisiteur(identifiant);
                    controle.setPasswordBdd(passwordBdd);
                    controle.setLogin(login);
                    if (controle.coupleLoginPwdCorrect(MainActivity.login, MainActivity.passwordEntre)){
                       controle.accesMenuActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                // Récupération des frais forfaits du visiteur pour le mois actuel
                if (message[0].equals("getFraisForfait")) {
                    try {
                        // récupération des informations
                        JSONArray lesInfos = new JSONArray(message[1]);
                        Hashtable<String, Integer> lesFraisForfait = new Hashtable<>();
                        for(int k=0 ; k<lesInfos.length() ; k++){
                            JSONObject info = new JSONObject(lesInfos.get(k).toString());
                            String idFrais = info.getString("idfraisforfait");
                            Integer quantite = info.getInt("quantite");
                            lesFraisForfait.put(idFrais, quantite);
                        }
                        // mémorisation des frais forfait
                        controle.setLesFraisForfait(lesFraisForfait);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    // Récupération des frais hors forfait du visiteur pour le mois actuel
                    if (message[0].equals("getFraisHF")) {
                        try {
                            // récupération des informations
                            JSONArray lesInfos = new JSONArray(message[1]);
                            Hashtable<Integer, FraisHf> lesFraisHF = new Hashtable<>();
                            int identifiant = 0;
                            for(int k=0 ; k<lesInfos.length() ; k++){
                                JSONObject info = new JSONObject(lesInfos.get(k).toString());
                                Integer idFraisHF = info.getInt("id");
                                Double montant = info.getDouble("montant");
                                String libelle = info.getString("libelle");
                                String jour = (info.getString("date")).substring(8, 10);
                                FraisHf unFraisHF = new FraisHf(montant, libelle, jour, identifiant);
                                lesFraisHF.put(idFraisHF, unFraisHF);
                                // Mémorisation du dernier id frais HF ajouté dans la BDD
                                controle.setDernierIdFraisHf(identifiant);
                                identifiant++;
                            }

                            // mémorisation des frais hors forfait
                            controle.setLesFraisHorsForfait(lesFraisHF);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        // Mise à jour dans la BDD
                        if (message[0].equals("updateFraisForfait")) {
                            // pour vérification, affiche le contenu du retour dans la console
                            Log.d("updateFraisForfait", "****************" + message[1]);
                        }else{
                            if(message[0].equals("ajoutFraisHF")){
                                // Récupération des informations du frais HF ajouté
                                try {
                                    JSONObject info = new JSONObject(message[1]);
                                    Integer dernierId = info.getInt("idMax");
                                    String libelle = info.getString("libelle");
                                    Double montant = info.getDouble("montant");
                                    String jour = (info.getString("date")).substring(8, 10);

                                    // On ajoute dans la liste des frais HF le dernier frais HF
                                    // qui a été ajouté contenant l'id dernierId qui vient d'être retourné
                                    controle.ajouterUnFraisHf(dernierId, new FraisHf(montant, libelle, jour, controle.getDernierIdFraisHf()+1));
                                    // Mémorisation du dernier id frais HF ajouté dans la BDD
                                    controle.setDernierIdFraisHf(controle.getDernierIdFraisHf()+1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                if(message[0].equals("suppressionFraisHF")){
                                    // pour vérification, affiche le contenu du retour dans la console
                                    Log.d("suppressionFraisHF", "****************" + message[1]);
                                }
                            }
                        }
                    }
                }
            }
        } else{
            /* Si la demande envoyé au serveur était recupInfos et que rien n'est retourné, c'est
             * que le mdp ou le login était incorrect donc on affiche un message à l'écran
             */
            if (message[0].equals("recupInfos") && message.length==1){
                MainActivity.txtErrorAuth.setText("Identifiant ou mot de passe incorrect");
            }
        }
    }

    /**
     * Envoi de données vers le serveur distant
     * @param operation information précisant au serveur l'opération à exécuter
     * @param lesDonneesJSON les données à traiter par le serveur
     */
    public void envoi(String operation, JSONArray lesDonneesJSON){
        AccesHTTP accesDonnees = new AccesHTTP();
        // lien avec AccesHTTP pour permettre à delegate d'appeler la méthode processFinish
        // au retour du serveur
        accesDonnees.delegate = this;
        // ajout de paramètres dans l'enveloppe HTTP
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        // envoi en post des paramètres, à l'adresse SERVERADDR
        accesDonnees.execute(SERVERADDR);
    }
}
