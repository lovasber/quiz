package com.company;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TanarView extends JPanel {
        JButton jbUjKat;
        JButton jbUjKerdes;
        JButton jbKerdesSzerk;
        JButton jbDiakokEredmenyei;
        Controller cont;

    public TanarView(Controller controller) {
            super.setLayout(new FlowLayout());
            cont = controller;
            jbUjKat = new JButton("Új kategória létrehozása");
            jbUjKat.addActionListener(e -> {
                    controller.ujablakmegynit(ujKategoria());
            });
            jbUjKerdes = new JButton("Új kérdés létrehozása");
            jbUjKerdes.addActionListener(e -> {
                    controller.ujablakmegynit(ujKerdes());
            });
            jbKerdesSzerk = new JButton("Kérdések szerkesztése");
            jbKerdesSzerk.addActionListener(e -> {
                    controller.ujablakmegynit(kerdesSzerk());
            });
            jbDiakokEredmenyei = new JButton("Diákok eredményei");
            jbDiakokEredmenyei.addActionListener(e -> {
                    controller.ujablakmegynit(diakokEredmenyei());
            });

            super.add(jbUjKat);
            super.add(jbUjKerdes);
            super.add(jbKerdesSzerk);
            super.add(jbDiakokEredmenyei);
    }



        private JFrame ujKategoria() {
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
                JLabel jlFokatLeir = new JLabel("Fő kategória leírása");
                JTextField jtfFokat = new JTextField(20);
                JTextField jtfFokatLeir = new JTextField(20);

                JButton jbFokatOk = new JButton("OK");
                jbFokatOk.addActionListener(e -> {
                        if (jtfFokat.getText().length()!=0 && jtfFokatLeir.getText().length()!=0){
                                if (cont.letezoFoKategoriak().contains(jtfFokat.getText())){
                                        JOptionPane.showMessageDialog(jfUjkat,"Ilyen főkategóra már létezik!");
                                }else{
                                        cont.fokatLetrehoz(jtfFokat.getText(),jtfFokatLeir.getText());
                                        JOptionPane.showMessageDialog(jfUjkat,"Sikeresen létrehozott egy főkategóriaát");
                                }
                        }else{
                                JOptionPane.showMessageDialog(jfUjkat,"Kérem töltse ki az összes mezőt!");
                        }

                });
                JLabel jlFokatValaszt = new JLabel("Fő kategória kiválasztása");
                JComboBox jcKategoriak = new JComboBox();
                jcKategoriak.setPrototypeDisplayValue("Kategóriák");
                for (int i = 0; i < cont.letezoFoKategoriak().size(); i++) {
                        jcKategoriak.addItem(cont.letezoFoKategoriak().get(i));
                }
                JLabel jlAlkat = new JLabel("Alkategória létreozása");
                JLabel jlAlkatLeir = new JLabel("Alkategória leírása");
                JTextField jtfAlkat = new JTextField(20);
                JTextField jtfAlkatLeir = new JTextField(20);

                JButton jbAlkatOk = new JButton("OK");
                jbAlkatOk.addActionListener( e->{
                        if(jtfAlkat.getText().length()!=0 && jtfAlkatLeir.getText().length()!=0){
                                if (cont.letezoAlKategoriak().contains(jbAlkatOk.getText())){
                                        JOptionPane.showMessageDialog(jfUjkat,"Ilyen alkategória már létezik");
                                }else{
                                        cont.alkatLetrehoz(jtfAlkat.getText(),jcKategoriak.getSelectedItem().toString(),jtfAlkatLeir.getText());
                                        JOptionPane.showMessageDialog(jfUjkat,"Sikeresen létrehozott egy alkategóriát");
                                }
                        }else{
                                JOptionPane.showMessageDialog(jfUjkat,"Kérem töltse ki az összes mezőt!");
                        }
                });

                gbc.insets = new Insets(5,5,5,5);

                gbc.gridx = 0;
                gbc.gridy = 0;
                jpUjkat.add(jlCim,gbc);

                gbc.gridx = 0;
                gbc.gridy=1;
                jpUjkat.add(jlFokat,gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                jpUjkat.add(jtfFokat,gbc);

                gbc.gridx = 0;
                gbc.gridy= 2;
                jpUjkat.add(jlFokatLeir,gbc);

                gbc.gridx = 1;
                gbc.gridy = 2;
                jpUjkat.add(jtfFokatLeir,gbc);

                gbc.gridx = 2;
                gbc.gridy = 2;
                jpUjkat.add(jbFokatOk,gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                jpUjkat.add(jlFokatValaszt,gbc);

                gbc.gridx = 1;
                gbc.gridy = 3;
                jpUjkat.add(jcKategoriak,gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                jpUjkat.add(jlAlkat,gbc);

                gbc.gridx = 1;
                gbc.gridy = 4;
                jpUjkat.add(jtfAlkat,gbc);

                gbc.gridx = 1;
                gbc.gridy = 5;
                jpUjkat.add(jtfAlkatLeir,gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                jpUjkat.add(jlAlkatLeir,gbc);

                gbc.gridx = 2;
                gbc.gridy = 5;
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


        private JFrame diakokEredmenyei() {
                JFrame jfdiakEredmeny = new JFrame();
                JPanel jpDiakEredmeny = new JPanel();
                jfdiakEredmeny.add(jpDiakEredmeny);
                return jfdiakEredmeny;
        }
}
