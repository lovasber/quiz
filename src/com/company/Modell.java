package com.company;

import javax.crypto.SecretKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Modell {

    private Connection CON;
    private Statement ST;
    private ResultSet rs;
    private SecretKey secretKey;

    public Modell() {
        try{
            Class.forName("com.mysql.jdbc.Driver");

            CON = DriverManager.getConnection("jdbc:mysql://localhost/quiz","root","");
            ST = CON.createStatement();
        }catch(Exception e){
            System.out.println("Adatbázis kapcsolódási hiba: "+e.getLocalizedMessage());
        }
    }
}
