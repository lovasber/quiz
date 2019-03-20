package com.company;

import java.util.Arrays;

public class Felhasznalo {
    private int id;
    private String teljesNev;
    private String felhasznalonev;
    private String jelszo;
    private int tipus;
    private int szint;
    private int aktiv;
    private int joValasz;
    private int rosszValasz;



    public Felhasznalo(int id, String felhasznalonev, String teljesNev, String jelszo, int szint, int tipus, int joValasz, int rosszValasz, int aktiv) {
        this.id = id;
        this.teljesNev = teljesNev;
        this.felhasznalonev = felhasznalonev;
        this.jelszo = jelszo;
        this.tipus = tipus;
        this.szint = szint;
        this.aktiv = aktiv;
        this.joValasz = joValasz;
        this.rosszValasz = rosszValasz;
    }

    public int getJoValasz() {
        return joValasz;
    }

    public int getRosszValasz() {
        return rosszValasz;
    }

    public int getId() {
        return id;
    }

    public String getTeljesNev() {
        return teljesNev;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public int getTipus() {
        return tipus;
    }

    public int getSzint() {
        return szint;
    }

    public int getAktiv() {
        return aktiv;
    }

    @Override
    public String toString() {
        return "Felhasznalo{" +
                "id=" + id +
                ", teljesNev='" + teljesNev + '\'' +
                ", felhasznalonev='" + felhasznalonev + '\'' +
                ", jelszo='" + jelszo + '\'' +
                ", tipus=" + tipus +
                ", szint=" + szint +
                ", aktiv=" + aktiv +
                ", joValasz=" + joValasz +
                ", rosszValasz=" + rosszValasz +
                '}';
    }
}
