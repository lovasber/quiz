package com.company;

public class Kerdes implements AdatbazisKapcsolat{
    private int id;
    private int tipus;
    private String tipusNev;
    private String kerdesSzovege;
    private String helyesValasz;
    private String valaszlehetosegek;
    private int pontszam;

    public Kerdes(int id, int tipus, String kerdesSzovege, String helyesValasz, String valaszlehetosegek, int pontszam) {
        this.id = id;
        this.tipus = tipus;
        this.tipusNev = KERDESTIPUS[tipus];
        this.kerdesSzovege = kerdesSzovege;
        this.helyesValasz = helyesValasz;
        this.valaszlehetosegek = valaszlehetosegek;
        this.pontszam = pontszam;
    }

    public int getId() {
        return id;
    }

    public int getTipus() {
        return tipus;
    }

    public String getTipusNev() {
        return tipusNev;
    }

    public String getKerdesSzovege() {
        return kerdesSzovege;
    }

    public String getHelyesValasz() {
        return helyesValasz;
    }

    public String getValaszlehetosegek() {
        return valaszlehetosegek;
    }

    public int getPontszam() {
        return pontszam;
    }

    @Override
    public String toString() {
        return "Kerdes{" +
                "id=" + id +
                ", tipus=" + tipus +
                ", tipusNev='" + tipusNev + '\'' +
                ", kerdesSzovege='" + kerdesSzovege + '\'' +
                ", helyesValasz='" + helyesValasz + '\'' +
                ", valaszlehetosegek='" + valaszlehetosegek + '\'' +
                ", pontszam=" + pontszam +
                '}';
    }
}
