package com.company;

import com.mysql.jdbc.PreparedStatement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    public void bejelentkezes(){
        String SQL_FELHASZNALOK = "SELECT * FROM felhasznalok";

        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_FELHASZNALOK);
            while (rs.next()){
                System.out.println(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ez a metódus létrehozza a felhasználókat az adatbázisban
     * @param felhasznalonev
     * @param teljesNev
     * @param jelszo
     */
    public void diakRegisztracio(String felhasznalonev,String teljesNev,String jelszo){
            String SQL_UJFELHASZNALOT_LETREHOZZ = "INSERT INTO `felhasznalok` (`id`, `felhasznaloNev`, `teljesNev`, `jelszo`, `szint`, `tipus`, `joValaszDb`, `rosszValaszDb`) VALUES ( NULL , '" + felhasznalonev + "', '" + teljesNev + "','" + jelszo + "', '1', '0', '0', '0')";
            try {
                Statement stm = modell.getCON().createStatement();
                stm.executeUpdate(SQL_UJFELHASZNALOT_LETREHOZZ);
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public char[] titkosit(char[] titkositando){
        char[] titkostitott=titkositando;

        return titkostitott;
    }

    public boolean egyezoJelszo(String jelszo, String jelszomeg){
        return jelszo.equals(jelszomeg)?true:false;
    }

    public boolean szabadFelhasznaloNev(String felhasznaloNev){
        boolean szabad = true;
        String SQL_FELHASZNALOK = "SELECT felhasznalonNev FROM felhasznalok";

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

}
