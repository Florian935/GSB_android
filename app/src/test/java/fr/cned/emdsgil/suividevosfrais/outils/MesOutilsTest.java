package fr.cned.emdsgil.suividevosfrais.outils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class MesOutilsTest {

    String date = (new SimpleDateFormat("yyyy-MMM-dd")).format(new Date());
    String moisActuelInNumber = (new SimpleDateFormat("MM")).format(new Date());

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void convertDateToString() {
        String dateRetournee = MesOutils.convertDateToString(new Date());

        assertEquals(date, dateRetournee);
    }

    @Test
    public void actualDayOfMonth() {
        String jourAttendu = date.substring(11, 13);
        String jourRetourne = MesOutils.actualDayOfMonth(new Date());

        assertEquals(jourAttendu, jourRetourne);
    }

    @Test
    public void actualMonth() {
        String moisAttendu = date.substring(5, 8);
        String moisRetourne = MesOutils.actualMonth(new Date());

        assertEquals(moisAttendu, moisRetourne);
    }

    @Test
    public void actualYear() {
        String anneeAttendue = date.substring(0, 4);
        String anneeRetournee = MesOutils.actualYear(new Date());

        assertEquals(anneeAttendue, anneeRetournee);
    }

    @Test
    public void actualMoisInNumeric() {
        String moisRetourne = MesOutils.actualMoisInNumeric(new Date());

        assertEquals(moisActuelInNumber, moisRetourne);
    }

    @Test
    public void convertIntDayToStringDay() {
        String dayOne = "01";
        String dayTen = "10";

        String dayOneRetourne = MesOutils.convertIntDayToStringDay(1);
        String dayTenRetourne = MesOutils.convertIntDayToStringDay(10);

        assertEquals(dayOne, dayOneRetourne);
        assertEquals(dayTen, dayTenRetourne);
    }



    @After
    public void tearDown() throws Exception {
    }
}