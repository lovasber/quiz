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
    private int[] statisztika;



    public Felhasznalo(int id, String felhasznalonev, String teljesNev, String jelszo, int szint, int tipus, int joValasz, int rosszValasz, int aktiv) {
        this.id = id;
        this.teljesNev = teljesNev;
        this.felhasznalonev = felhasznalonev;
        this.jelszo = jelszo;
        this.tipus = tipus;
        this.szint = szint;
        this.aktiv = aktiv;
        this.statisztika = new int[]{joValasz, rosszValasz};
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
                ", statisztika=" + Arrays.toString(statisztika) +
                '}';
    }


    public int[] getStatisztika() {
        return statisztika;
    }
}
