package fr.cned.emdsgil.suividevosfrais.modele;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.outils.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.outils.AsyncResponse;
import fr.cned.emdsgil.suividevosfrais.vue.MainActivity;

public class AccesDistant implements AsyncResponse {

    // adresse du serveur
    private static final String SERVERADDR = "http://192.168.1.31/androidgsb/serveurgsb.php";

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
            if (message[0].equals("recupInfos")) {
                Log.d("recupInfos", "****************" + message[1]);
                try{
                    // Récupération des informations
                    JSONObject info = new JSONObject(message[1]);
                    String identifiant = info.getString("id");
                    String passwordBdd = info.getString("mdp");
                    String login = info.getString("login");
                    controle.setIdentifiant(identifiant);
                    controle.setPasswordBdd(passwordBdd);
                    controle.setLogin(login);
                    if (controle.coupleLoginPwdCorrect(MainActivity.login, MainActivity.passwordEntre)){
                       controle.accesMenuActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                if (message[0].equals("fraisForfait")) {
                    // TODO : valorisation des frais forfaits dans Global.listFraisMois (prendre ex
                    //                    sur kmActivity pour les valorisations)
                    Log.d("fraisForfait", "****************" + message[1]);
                }else{

                }if (message[0].equals("fraisHorsForfait")) {
                    // TODO : valorisation des frais hors forfaits dans Global.listFraisMois (prendre ex
                    //                    sur kmActivity pour les valorisations)
                    Log.d("fraisHorsForfait", "****************" + message[1]);
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
