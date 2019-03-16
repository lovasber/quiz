package com.company;

import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminView extends JPanel {
    JButton jbUjKat;
    JButton jbUjKerdes;
    JButton jbKerdesSzerk;
    JButton jbDiakokEredmenyei;
    JButton jbFelhasznalok;
    Controller cont;

    public AdminView(Controller conntroller) {
        this.cont = conntroller;
        super.setLayout(new FlowLayout());
        jbUjKat = new JButton("Új kategória létrehozása");
        jbUjKat.addActionListener(e -> {
            conntroller.ujablakmegynit(ujKategoria());
        });
        jbUjKerdes = new JButton("Új kérdés létrehozása");
        jbUjKerdes.addActionListener(e -> {
            conntroller.ujablakmegynit(ujKerdes());
        });
        jbKerdesSzerk = new JButton("Kérdések szerkesztése");
        jbKerdesSzerk.addActionListener(e -> {
            conntroller.ujablakmegynit(kerdesSzerk());
        });
        jbDiakokEredmenyei = new JButton("Diákok eredményei");
        jbDiakokEredmenyei.addActionListener(e -> {
            conntroller.ujablakmegynit(jbDiakokEredmenyei());
        });

        jbFelhasznalok = new JButton("Felhasználók szerkesztése");
        jbFelhasznalok.addActionListener(e -> {
            conntroller.ujablakmegynit(felhSzerk());
        });

        super.add(jbUjKat);
        super.add(jbUjKerdes);
        super.add(jbKerdesSzerk);
        super.add(jbDiakokEredmenyei);
        super.add(jbFelhasznalok);
    }




    private JFrame ujKategoria(){
        JFrame jfUjkat = new JFrame();
        jfUjkat.setTitle("Quiz 1.0");
        jfUjkat.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfUjkat.setMinimumSize(new Dimension(1000,700));
        GridBagLayout gbl = new GridBagLayout();
        JPanel jpUjkat = new JPanel();
        jpUjkat.setLayout(gbl);

        GridBagConstraints gbc =  new GridBagConstraints();
        JLabel jlCim = new JLabel("Új kategória létrehozása");
        JLabel jlFokat = new JLabel("Fő kategória");
        JTextField jtfFokat = new JTextField();
        jtfFokat.setColumns(20);
        JButton jbFokatOk = new JButton("OK");
        JLabel jlFokatValaszt = new JLabel("Fő kategória kiválasztása");
        JComboBox jcKategoriak = new JComboBox();
        jcKategoriak.setPrototypeDisplayValue("Kategóriák");
        JLabel jlAlkat = new JLabel("Alkategória létreozása");
        JTextField jtfAlkat = new JTextField();
        jtfAlkat.setColumns(20);
        JButton jbAlkatOk = new JButton("OK");

        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        jpUjkat.add(jlCim,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        jpUjkat.add(jlFokat,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        jpUjkat.add(jtfFokat,gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 2;
        gbc.gridy = 1;
        jpUjkat.add(jbFokatOk,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        jpUjkat.add(jlFokatValaszt,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        jpUjkat.add(jcKategoriak,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        jpUjkat.add(jlAlkat,gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        jpUjkat.add(jtfAlkat,gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        jpUjkat.add(jbAlkatOk,gbc);

        jfUjkat.add(jpUjkat);
        return jfUjkat;
    }


    private JFrame ujKerdes() {
        JFrame jfUjkerdes = new JFrame();
        jfUjkerdes.setTitle("Quiz 1.0");
        jfUjkerdes.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfUjkerdes.setMinimumSize(new Dimension(1000,700));

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        JPanel jpUjkerdes = new JPanel();
        jpUjkerdes.setLayout(gbl);

        JLabel jlCim = new JLabel("Új Kérdés létrehozása");
        JLabel jlKerdesFokat = new JLabel("Kérdés főkategóriája");
        JLabel jlKerdesAlkat = new JLabel("Kérdés alkategóriája");
        JLabel jlKerdesSzoveg = new JLabel("Kérdés szövege");
        JLabel jlValasz = new JLabel("Válasz");
        JLabel jlPontszam = new JLabel("Elérhető pontszám");

        JComboBox jcbFokat = new JComboBox();
        jcbFokat.setPrototypeDisplayValue("Főkategória");
        JComboBox jcbAlkat = new JComboBox();
        jcbAlkat.setPrototypeDisplayValue("Alkategória");
        JTextField jtfSzoveg  = new JTextField();
        jtfSzoveg.setColumns(50);
        JTextField jtfValasz  = new JTextField();
        jtfValasz.setColumns(50);
        JComboBox jcbPontszam = new JComboBox();
        jcbPontszam.setPrototypeDisplayValue("pont");
        jcbPontszam.addItem(1);
        jcbPontszam.addItem(2);
        jcbPontszam.addItem(3);
        jcbPontszam.addItem(4);
        jcbPontszam.addItem(5);
        JButton jbOk = new JButton("Ok");

        gbc.gridx = 0;
        gbc.gridy = 0;
        jpUjkerdes.add(jlCim,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        jpUjkerdes.add(jlKerdesFokat,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        jpUjkerdes.add(jcbFokat,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        jpUjkerdes.add(jlKerdesAlkat,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        jpUjkerdes.add(jcbAlkat,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        jpUjkerdes.add(jlKerdesSzoveg,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        jpUjkerdes.add(jtfSzoveg,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        jpUjkerdes.add(jlValasz,gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        jpUjkerdes.add(jtfValasz,gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        jpUjkerdes.add(jlPontszam,gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        jpUjkerdes.add(jcbPontszam,gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = 2;
        jpUjkerdes.add(jbOk,gbc);

        jfUjkerdes.add(jpUjkerdes);
        return jfUjkerdes;
    }

    private JFrame kerdesSzerk() {
        JFrame jfKerdesSzerk = new JFrame();
        JPanel jpKerdesSzerk = new JPanel();
        jfKerdesSzerk.add(jpKerdesSzerk);
        return jfKerdesSzerk;
    }


    private JFrame jbDiakokEredmenyei() {
        JFrame jfdiakEredmeny = new JFrame();
        JPanel jpDiakEredmeny = new JPanel();
        jfdiakEredmeny.add(jpDiakEredmeny);
        return jfdiakEredmeny;
    }

    private JFrame felhSzerk() {
        JFrame jfFelh = new JFrame();
        jfFelh.setTitle("Quiz 1.0");
        jfFelh.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfFelh.setMinimumSize(new Dimension(1000,700));
        JPanel jpMain = new JPanel();

        JPanel jpFelh = new JPanel();
        jpFelh.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,20);

        gbc.gridx = 0;
        jpFelh.add(new JLabel("Felhasználó név"));
        gbc.gridx = 1;
        jpFelh.add(new JLabel("Teljes név"));
        gbc.gridx = 2;
        jpFelh.add(new JLabel("Felhasználó aktív"));
        gbc.gridx = 3;
        jpFelh.add(new JLabel("Felhasználó tipusa"));

        gbc.gridy = 1;
        ArrayList<Felhasznalo> flist = cont.felhasznalokLista();
        for (int i = 0; i <flist.size() ; i++) {
            gbc.gridx = 0;
            jpFelh.add(new JLabel(flist.get(i).getFelhasznalonev()),gbc);
            gbc.gridx = 1;
            jpFelh.add(new JLabel(flist.get(i).getTeljesNev()),gbc);
            gbc.gridx = 2;
            JComboBox jcbAktiv = new JComboBox();
            jcbAktiv.addItem(0);
            jcbAktiv.addItem(1);
            jcbAktiv.setPrototypeDisplayValue(0);
            jcbAktiv.setSelectedItem(flist.get(i).getAktiv());
            jpFelh.add(jcbAktiv,gbc);

            gbc.gridx = 3;
            JComboBox jcbTipus = new JComboBox();
            jcbTipus.addItem(cont.felhtipus.get("Diák"));
            jcbTipus.addItem(cont.felhtipus.get("Tanár"));
            jcbTipus.addItem(cont.felhtipus.get("Admin"));
            jcbTipus.setPrototypeDisplayValue("Admin");
            jcbTipus.setSelectedItem(flist.get(i).getTipus());
            jpFelh.add(jcbTipus,gbc);

            gbc.gridy++;
            gbc.gridx = 0;
        }



        JScrollPane jScrollPane = new JScrollPane(jpFelh);
        jScrollPane.setPreferredSize(new Dimension(600,400));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        jpMain.add(jScrollPane);
        jfFelh.add(jpMain);
        return jfFelh;
    }


}
