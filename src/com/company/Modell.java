package com.company;

import java.sql.*;
import java.util.ArrayList;

public class Modell implements AdatbazisKapcsolat {

    private Connection CON;
    private Statement ST;
    //private ArrayList<Kerdes> kerdesekLista;
    private int felhasznaloTipus;
    private int aktualisKerdesDb;
    private Felhasznalo felhasznalo;




    public Modell() {
        adatbazisKapcsolatLetrehoz();
        //kerdesekLista = new ArrayList<>();
    }

    private void adatbazisKapcsolatLetrehoz() {
        try{
            Class.forName(ABDRIVER);
            CON = DriverManager.getConnection(ABURL,ABFELHASZNALO,"");
            ST = CON.createStatement();
        }catch(Exception e){
            System.out.println("Adatbázis kapcsolódási hiba: "+e.getLocalizedMessage());
        }
        System.out.println("sikeres adatbázis kapcsolat");
    }


    public Connection getCON() {
        return CON;
    }

    public void setFelhasznalo(Felhasznalo felhasznalo) {
        this.felhasznalo = felhasznalo;
    }

    public Felhasznalo getFelhasznalo() {
        return felhasznalo;
    }

    public boolean torolhetoEAdmin() throws SQLException {
        boolean torolheto=false;
        int adminDb=0;
        String SQL_COUNTADMIN = "SELECT COUNT('id') FROM felhasznalok WHERE 'tipus'=2";

        PreparedStatement ps = CON.prepareStatement(SQL_COUNTADMIN);
        ResultSet res = ps.executeQuery();
        while (res.next()){
            adminDb++;
        }
        if (adminDb>1){
            torolheto=true;
        }
        System.out.println(adminDb);
        return torolheto;
    }

//    public ArrayList<Kerdes> getKerdesekLista() {
//        return kerdesekLista;
//    }
}
