package com.company;

public interface AdatbazisKapcsolat {
    String ABDRIVER="com.mysql.jdbc.Driver";
    String ABURL="jdbc:mysql://localhost/quiz";
    String ABFELHASZNALO="root";
    String ABJELSZO="";
    String[] FELHASZNLAOKTIPUS = new String[]{"diák","tanár","admin"};
}
