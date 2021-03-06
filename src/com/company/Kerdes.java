package com.company;

public class Kerdes implements AdatbazisKapcsolat{
    private int id;
    private int tipus;
    private String foKategoria;
    private String alkategoria;
    private String tipusNev;
    private String kerdesSzovege;
    private String helyesValasz;
    private String valaszlehetosegek;
    private int pontszam;

    public Kerdes(int id, String foKategoria, String alkategoria, int tipus,String kerdesSzovege, String helyesValasz, String valaszlehetosegek, int pontszam) {
        this.id = id;
        this.tipus = tipus;
        this.foKategoria = foKategoria;
        this.alkategoria = alkategoria;
        this.tipusNev =KERDESTIPUS[tipus];
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

    public String getFoKategoria() {
        return foKategoria;
    }

    public String getAlkategoria() {
        return alkategoria;
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
                ", foKategoria='" + foKategoria + '\'' +
                ", alkategoria='" + alkategoria + '\'' +
                ", tipusNev='" + tipusNev + '\'' +
                ", kerdesSzovege='" + kerdesSzovege + '\'' +
                ", helyesValasz='" + helyesValasz + '\'' +
                ", valaszlehetosegek='" + valaszlehetosegek + '\'' +
                ", pontszam=" + pontszam +
                '}';
    }
}
