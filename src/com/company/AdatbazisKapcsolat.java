package com.company;

public interface AdatbazisKapcsolat {
    String ABDRIVER="com.mysql.jdbc.Driver";
    String ABURL="jdbc:mysql://localhost/quiz?characterEncoding=utf8";
    String ABFELHASZNALO="root";
    String ABJELSZO="";
    String[] FELHASZNLAOKTIPUS = new String[]{"diák","tanár","admin"};
    String[] KERDESTIPUS = new String[]{"hianyos","sokKep1JoValasz","egyKep1Valasz","Fordítas","KerdesValasz"};
}
