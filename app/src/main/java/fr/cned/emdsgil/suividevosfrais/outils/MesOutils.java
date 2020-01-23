package fr.cned.emdsgil.suividevosfrais.outils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MesOutils {

    /**
     * Permet de hasher la chaîne de caractère fournie en paramètre grâce à l'algorithme de hashage
     * SHA-256
     *
     * @param chaine chaîne de caractère
     * @return la chaîne de caractère hashée
     */
    public static String hashString(String chaine) {
        byte[] hash = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            hash = md.digest((chaine).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byteToHex(hash);
    }

    /**
     * Permet d'effectuer une conversion d'un tableau de bits au format hexadécimale pour finalement
     * retourné le tableau de bits sous forme de chaîne de caractère String
     *
     * @param bits conversion au format hexadécimale puis au format String
     * @return les bits passés en paramètre au format String
     */
    public static String byteToHex(byte[] bits) {
        if (bits == null) {
            return null;
        }
        StringBuffer hex = new StringBuffer(bits.length * 2);
        for (int i = 0; i < bits.length; i++) {
            if (((int) bits[i] & 0xff) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toString((int) bits[i] & 0xff, 16));
        }
        return hex.toString();
    }

    /**
     * Conversion d'une date au format Date vers le format String
     * @param uneDate au format Date
     * @return uneDate au format String
     */
    public static String convertDateToString(Date uneDate){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MMM-dd");
        return date.format(uneDate);
    }

    /**
     * Retourne le mois de la date passée en paramètre au format littéral
     * @param uneDate au format Date
     * @return le mois actuel sous forme MMM au format String
     */
    public static String actualMonth(Date uneDate){
        String date = convertDateToString(uneDate);
        return date.substring(5, 8);
    }

    /**
     * Retourne l'année de la date passée en paramètre
     * @param uneDate au format Date
     * @return l'année actuelle sous forme yyyy au format String
     */
    public static String actualYear(Date uneDate){
        String date = convertDateToString(uneDate);
        return date.substring(0, 4);
    }

    /**
     * Retourne le mois de la date passée en paramètre au format numérique
     * @param uneDate au format Date
     * @return le mois actuel sous forme MM au format String
     */
    public static String actualMoisInNumeric(Date uneDate){
        SimpleDateFormat date = new SimpleDateFormat("MM");
        return date.format(uneDate);
    }
}
