package com.company;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Controller {
    private Modell modell;
    private View view;

    public Controller() {
        modell = new Modell();
        view = new View(this);
    }

    /**
     * Bejelentkezés
     * @param fnev
     * @param jelszo
     */
    public boolean helyesJelszoE(String fnev, String jelszo){
        boolean helyesjelszo = false;
        String SQL_JELSZO = "SELECT jelszo FROM felhasznalok WHERE felhasznaloNev='"+fnev+"' AND jelszo=+'"+titkosit(jelszo)+"';";


        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_JELSZO);
            if (rs.next() && rs.getString(1).length()!=0){
                //System.out.println(String.valueOf(rs.getString(1)));
                String abJelszo = rs.getString(1);
                System.out.println(abJelszo+"!");
                helyesjelszo = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return helyesjelszo;
    }

    /**
     * Ellenőrzi, hogy az adatbázisban található-e a felhasználó által begépelt felhasználó név.
     * @param fnev
     * @return
     */
    public boolean letezikEfelhasznalo(String fnev){
        String SQL_FELHASZNALOK = "SELECT COUNT(felhasznaloNev) FROM felhasznalok WHERE felhasznaloNev='"+fnev+"';";
        boolean letezik = false;
        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_FELHASZNALOK);
            if (rs.next() && rs.getInt(1)!=0){
                letezik = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letezik;
    }



    public char[] titkosit(char[] titkositando){
        char[] titkostitott=titkositando;

        return titkostitott;
    }

    /**
     * Ez a metódus létrehozza a felhasználókat az adatbázisban
     * @param felhasznalonev
     * @param teljesNev
     * @param jelszo
     */
    public void diakRegisztracio(String felhasznalonev, String teljesNev, String jelszo){
        String SQL_UJFELHASZNALOT_LETREHOZZ = "INSERT INTO `felhasznalok` (`id`, `felhasznaloNev`, `teljesNev`, `jelszo`, `szint`, `tipus`, `joValaszDb`, `rosszValaszDb`) " +
                "VALUES ( NULL , '" + felhasznalonev + "', '" + teljesNev + "','" + jelszo + "', '1', '0', '0', '0')";
        try {
            Statement stm = modell.getCON().createStatement();
            stm.executeUpdate(SQL_UJFELHASZNALOT_LETREHOZZ);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Két String típusu változót vet össze a függvény, megnézi, hogy egyeznek-e.
     * @param jelszo
     * @param jelszomeg
     * @return
     */
    public boolean egyezoJelszo(String jelszo, String jelszomeg){
        return jelszo.equals(jelszomeg)?true:false;
    }

    /**
     * A függvény ellenőrzi hogy a paraméterben átvett felhasználónév létezik e az adatbázisban és az ennek megfelelő értéket adja vissza.
     * @param felhasznaloNev
     * @return
     */
    public boolean szabadFelhasznaloNev(String felhasznaloNev){
        boolean szabad = true;
        String SQL_FELHASZNALOK = "SELECT felhasznaloNev FROM felhasznalok";

        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_FELHASZNALOK);
            while (rs.next()){
                if (felhasznaloNev.equals(rs.getString(1))){
                    szabad=false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return szabad;
    }

    /**
     * MD5 alapú titkosítást végez a függvény a paraméterben átvett Stringen és visszatlér a titkosított megfelelőjével.
     * @param titkositando
     * @return
     */
    public String titkosit(String titkositando){
        String titkositott="";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(titkositando.getBytes());
            byte[] b = md.digest();
            StringBuffer sb =  new StringBuffer();
            for(byte b1 : b) {
                sb.append(Integer.toHexString(b1 & 0xff).toString());
            }
            titkositott = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return titkositott;
    }

}
