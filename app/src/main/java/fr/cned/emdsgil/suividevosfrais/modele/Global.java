package fr.cned.emdsgil.suividevosfrais.modele;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;

public abstract class Global {

    // tableau d'informations mémorisées
    public static Hashtable<Integer, FraisMois> listFraisMois = new Hashtable<>();
    public static ArrayList<FraisForfait> lesFraisForfaits = new ArrayList<FraisForfait>();
    /* Retrait du type de l'Hashtable (Optimisation Android Studio)
     * Original : Typage explicit =
	 * public static Hashtable<Integer, FraisMois> listFraisMois = new Hashtable<Integer, FraisMois>();
	*/

    // fichier contenant les informations sérialisées
    public static final String filename = "save.fic";
}
