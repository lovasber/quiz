package com.company;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminView extends JPanel implements AdatbazisKapcsolat {
    JButton jbUjKat;
    JButton jbUjKerdes;
    JButton jbKerdesSzerk;
    JButton jbDiakokEredmenyei;
    JButton jbFelhasznalok;
    Controller cont;

    public AdminView(Controller conntroller)  throws SQLException {
        this.cont = conntroller;
        this.setLayout(new FlowLayout());
        jbUjKat = new JButton("Új kategória létrehozása");
        jbUjKat.addActionListener(e -> {
            try {
                conntroller.ujablakmegynit(ujKategoria());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
            conntroller.ujablakmegynit(diakokEredmenyei());
        });

        jbFelhasznalok = new JButton("Felhasználók szerkesztése");
        jbFelhasznalok.addActionListener(e -> {
            conntroller.ujablakmegynit(felhSzerk());
        });

        this.add(jbUjKat);
        this.add(jbUjKerdes);
        this.add(jbKerdesSzerk);
        this.add(jbDiakokEredmenyei);
        this.add(jbFelhasznalok);
    }




    private JFrame ujKategoria() throws SQLException {
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
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new SpringLayout());
        JPanel jpUjkerdes = new JPanel();
        jpUjkerdes.setLayout(gbl);

        JLabel jlCim = new JLabel("Új Kérdés létrehozása");
        JLabel jlKerdesFokat = new JLabel("Kérdés főkategóriája");
        JLabel jlKerdesAlkat = new JLabel("Kérdés alkategóriája");
        JLabel jlKerdesSzoveg = new JLabel("Kérdés szövege");
        JLabel jlValasz = new JLabel("Válasz");
        JLabel jlValaszLehet = new JLabel("Válasz lehetőségek");
        JLabel jlPontszam = new JLabel("Elérhető pontszám");

        JComboBox jcbFokat = new JComboBox();
        JComboBox jcbAlkat = new JComboBox();
        for (int i = 0; i < cont.letezoFoKategoriak().size(); i++) {
            jcbFokat.addItem(cont.letezoFoKategoriak().get(i));
        }
        jcbFokat.setSelectedIndex(0);
        jcbFokat.addActionListener(e -> {
            jcbAlkat.removeAllItems();
            for (int i = 0; i < cont.fokatAlkategoriai(jcbFokat.getSelectedItem().toString()).size(); i++) {
                jcbAlkat.addItem(cont.fokatAlkategoriai(jcbFokat.getSelectedItem().toString()).get(i));
            }
        });

        JLabel jlTipus = new JLabel("Kérdés típusa");
        JComboBox jcbTipus = new JComboBox();
        for (int i = 0; i < KERDESTIPUS.length; i++) {
            jcbTipus.addItem(KERDESTIPUS[i]);
        }


        JTextField jtfSzoveg  = new JTextField();
        jtfSzoveg.setColumns(50);
        JTextField jtfValasz  = new JTextField();
        JTextField jtfValaszLehetosegek  = new JTextField(50);
        jtfValasz.setColumns(50);
        JComboBox jcbPontszam = new JComboBox();
        jcbPontszam.setPrototypeDisplayValue("pont");
        jcbPontszam.addItem(1);
        jcbPontszam.addItem(2);
        jcbPontszam.addItem(3);
        jcbPontszam.addItem(4);
        jcbPontszam.addItem(5);

        JButton jbOk = new JButton("Ok");
        jbOk.addActionListener(e -> {
            if (jtfSzoveg.getText().length()!=0 && jtfValasz.getText().length()!=0 && jcbAlkat.getSelectedItem()!=null){
                cont.kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),"<html>"+jtfSzoveg.getText()+"</html>","<html>"+jtfValasz.getText()+"</html>"
                        ,"<html>"+jtfValaszLehetosegek.getText()+"</html>",(int)jcbPontszam.getSelectedItem());
                JOptionPane.showMessageDialog(jfUjkerdes,"Sikeresen létrehozott egy kérdést!");
            }else{
                JOptionPane.showMessageDialog(jfUjkerdes,"Kérem töltsön ki minden mezőt");
            }
        });

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
        jpUjkerdes.add(jlTipus,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        jpUjkerdes.add(jcbTipus,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        jpUjkerdes.add(jlKerdesSzoveg,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        jpUjkerdes.add(jtfSzoveg,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        jpUjkerdes.add(jlValasz,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        jpUjkerdes.add(jtfValasz,gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        jpUjkerdes.add(jlValaszLehet,gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        jpUjkerdes.add(jtfValaszLehetosegek,gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        jpUjkerdes.add(jlPontszam,gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        jpUjkerdes.add(jcbPontszam,gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = 2;
        jpUjkerdes.add(jbOk,gbc);
        jpMain.add(jpUjkerdes);
        jfUjkerdes.add(jpMain);
        return jfUjkerdes;
    }

    private JFrame kerdesSzerk() {
        JFrame jfKerdesSzerk = new JFrame();
        jfKerdesSzerk.setTitle("Quiz 1.0");
        jfKerdesSzerk.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfKerdesSzerk.setMinimumSize(new Dimension(1000,700));
        JPanel jpMain = new JPanel(new GridBagLayout());
        JPanel jpKerdesSzerk = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,5,2,5);
        JLabel jlFokat = new JLabel("<html><u>Főkategória</u></html>");
        JLabel jlAlkat = new JLabel("<html><u>Alkategória</u></html>");
        JLabel jlTipus = new JLabel("<html><u>Típus<u></html>");
        JLabel jlKerdesSzovege = new JLabel("<html><u>Kérdés szövege</u></html>");
        JLabel jlValasz = new JLabel("<html><u>Válasz</u></html>");
        JLabel jlValaszLehet = new JLabel("<html><u>Válasz lehetőségek</u></html>");
        JLabel jlPontszam = new JLabel("<html><u>Pontszám</u></html>");

        gbc.gridx = 0;
        gbc.gridy = 0;
        jpKerdesSzerk.add(jlFokat,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        jpKerdesSzerk.add(jlAlkat,gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        jpKerdesSzerk.add(jlTipus,gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        jpKerdesSzerk.add(jlKerdesSzovege,gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        jpKerdesSzerk.add(jlValasz,gbc);
        gbc.gridx = 5;
        gbc.gridy = 0;
        jpKerdesSzerk.add(jlValaszLehet,gbc);
        gbc.gridx = 6;
        gbc.gridy = 0;
        jpKerdesSzerk.add(jlPontszam,gbc);

        gbc.gridy=1;
        for (int i = 0; i < cont.letezoKerdesek().size(); i++) {
            gbc.gridx=0;
            jpKerdesSzerk.add(new JLabel(cont.letezoKerdesek().get(i).getFoKategoria()),gbc);
            gbc.gridx++;
            jpKerdesSzerk.add(new JLabel(cont.letezoKerdesek().get(i).getAlkategoria()),gbc);
            gbc.gridx++;
            jpKerdesSzerk.add(new JLabel(cont.letezoKerdesek().get(i).getTipusNev()),gbc);
            gbc.gridx++;
            jpKerdesSzerk.add(new JLabel(cont.letezoKerdesek().get(i).getKerdesSzovege()),gbc);
            gbc.gridx++;
            jpKerdesSzerk.add(new JLabel(cont.letezoKerdesek().get(i).getHelyesValasz()),gbc);
            gbc.gridx++;
            jpKerdesSzerk.add(new JLabel(cont.letezoKerdesek().get(i).getValaszlehetosegek()),gbc);
            gbc.gridx++;
            jpKerdesSzerk.add(new JLabel(cont.letezoKerdesek().get(i).getPontszam()+""),gbc);
            gbc.gridy++;
        }


        JScrollPane jsPane = new JScrollPane(jpKerdesSzerk);
        //jsPane.setPreferredSize(new Dimension(600,400));
        jsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //jpKerdesSzerk.add(jsPane);
        jpMain.add(jsPane);
        jfKerdesSzerk.add(jpMain);
        return jfKerdesSzerk;
    }


    private JFrame diakokEredmenyei() {
        JFrame jfdiakEredmeny = new JFrame();
        jfdiakEredmeny.setTitle("Quiz 1.0");
        jfdiakEredmeny.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfdiakEredmeny.setMinimumSize(new Dimension(1000,700));
        JPanel jpMain = new JPanel();
        JPanel jpDiakEredmeny = new JPanel();
        jpDiakEredmeny.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel jlFnev = new JLabel("<html><u>Diák neve</u></html>");
        JLabel jlJovalasDb = new JLabel("<html><u>Jó válaszok száma</u></html>");
        JLabel jlRosszvalasDb = new JLabel("<html><u>Rossz válaszok száma</u></html>");

        gbc.insets = new Insets(5,10,5,10);
        gbc.gridx=0;
        gbc.gridy=0;
        jpDiakEredmeny.add(jlFnev,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlJovalasDb,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlRosszvalasDb,gbc);
        gbc.gridy=1;
        for (int i = 0; i < cont.osszesDiak().size(); i++) {
            gbc.gridx=0;
            jpDiakEredmeny.add(new JLabel(cont.osszesDiak().get(i).getTeljesNev()),gbc);
            gbc.gridx++;
            jpDiakEredmeny.add(new JLabel(cont.osszesDiak().get(i).getJoValasz()+""),gbc);
            gbc.gridx++;
            jpDiakEredmeny.add(new JLabel(cont.osszesDiak().get(i).getRosszValasz()+""),gbc);
            gbc.gridy++;
        }


        JScrollPane jscp = new JScrollPane(jpDiakEredmeny);
        jscp.setPreferredSize(new Dimension(600,400));
        jscp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jpMain.add(jscp);
        jfdiakEredmeny.add(jpMain);
        return jfdiakEredmeny;
    }

    private JFrame felhSzerk() {
        JFrame jfFelh = new JFrame();
        jfFelh.setTitle("Quiz 1.0");
        jfFelh.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfFelh.setMinimumSize(new Dimension(1000,700));
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.anchor = GridBagConstraints.WEST;
        gbcMain.fill = GridBagConstraints.NONE;

        JPanel jpFelh = new JPanel();
        jpFelh.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,20,10,20);

        gbc.gridx = 0;
        jpFelh.add(new JLabel("Felhasználó név"));
        gbc.gridx = 1;
        jpFelh.add(new JLabel("Teljes név"));
        gbc.gridx = 2;
        jpFelh.add(new JLabel("Felhasználó aktív"));
        gbc.gridx = 3;
        jpFelh.add(new JLabel("Felhasználó tipusa"));
        gbc.gridx = 4;
        jpFelh.add(new JLabel("Felhasználó ID-ja"));

        gbc.gridy = 1;
        ArrayList<Felhasznalo> flist = cont.felhasznalokLista();

        for (int i = 0; i <flist.size() ; i++) {
            gbc.gridx = 0;
            jpFelh.add(new JLabel(flist.get(i).getFelhasznalonev()),gbc);
            gbc.gridx = 1;
            jpFelh.add(new JLabel(flist.get(i).getTeljesNev()),gbc);
            gbc.gridx = 2;
            JComboBox jcbAktiv = new JComboBox();
            jcbAktiv.addItem("Aktív");
            jcbAktiv.addItem("Nem aktív");
            jcbAktiv.setPrototypeDisplayValue("Nem aktiv");
            jcbAktiv.setSelectedIndex(flist.get(i).getAktiv());
            jpFelh.add(jcbAktiv,gbc);

            gbc.gridx = 3;
            JComboBox jcbTipus = new JComboBox();
            for (int j = 0; j < FELHASZNLAOKTIPUS.length; j++) {
                jcbTipus.addItem(FELHASZNLAOKTIPUS[j]);
            }

            jcbTipus.setPrototypeDisplayValue("Admin");
            jcbTipus.setSelectedIndex(flist.get(i).getTipus());
            jpFelh.add(jcbTipus,gbc);

            gbc.gridx=4;
            JLabel jlId = new JLabel(flist.get(i).getId()+"");
            jpFelh.add(jlId,gbc);

            gbc.gridx=5;
            JButton jbMent = new JButton("Elment");
            jbMent.addActionListener(e -> {
                cont.felhasznaloFrissit(jcbAktiv.getSelectedIndex(),jcbTipus.getSelectedIndex(),Integer.parseInt(jlId.getText()));
            });
            jpFelh.add(jbMent,gbc);

            gbc.gridy++;
            gbc.gridx = 0;
        }



        JScrollPane jScrollPane = new JScrollPane(jpFelh);
        jScrollPane.setPreferredSize(new Dimension(800,400));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jpMain.add(jScrollPane,gbcMain);

        JPanel jpUjFelhasznalo = new JPanel();
        jpUjFelhasznalo.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5,5,5,5);
        JLabel jlFnev = new JLabel("Felhasználó név");
        JLabel jlTeljesNev = new JLabel("Teljes név");
        JLabel jlJelszo = new JLabel("Jelszó");
        JLabel jlJelszoMeg = new JLabel("Jelszó megerősítése");
        JLabel jlTipus = new JLabel("Típus");
        JLabel jlAktiv = new JLabel("Aktiv");

        JTextField jtfFelhasznalonev = new JTextField(20);
        JTextField jtfTeljesnev = new JTextField(20);
        JPasswordField jpfJelszo = new JPasswordField(20);
        JPasswordField jpfJelszoMeg = new JPasswordField(20);
        JComboBox jcbTipus = new JComboBox();
        jcbTipus.setPrototypeDisplayValue("szint");
        for (int i = 0; i < FELHASZNLAOKTIPUS.length; i++) {
            jcbTipus.addItem(FELHASZNLAOKTIPUS[i]);
        }
        JComboBox jcbAktiv = new JComboBox();
        jcbAktiv.setPrototypeDisplayValue("nem aktív");
        jcbAktiv.addItem("aktív");
        jcbAktiv.addItem("nem aktív");
        JButton jbLetrehoz = new JButton("Felhasználót létrehoz");
        jbLetrehoz.addActionListener(e -> {
            String jelszo = new String(jpfJelszo.getPassword());
            String jelszoMeg = new String(jpfJelszoMeg.getPassword());
            if (jtfFelhasznalonev.getText().length()==0 || jtfTeljesnev.getText().length()==0||jpfJelszo.getPassword().length==0||jcbTipus.getSelectedItem().toString().length()==0||jcbAktiv.getSelectedItem().toString().length()==0){
                JOptionPane.showMessageDialog(jfFelh, "Töltse ki az összes mezőt");
            }else {
                if (jelszo.equals(jelszoMeg)) {
                    if (!cont.letezikEfelhasznalo(jtfFelhasznalonev.getText())) {
                        cont.regisztracio(jtfFelhasznalonev.getText(), jtfTeljesnev.getText(), cont.titkosit(new String(jpfJelszo.getPassword())), jcbTipus.getSelectedIndex(), (int) jcbAktiv.getSelectedIndex());
                        JOptionPane.showMessageDialog(jfFelh, "Sikeresen létrehozta a felhasználót");
                    } else {
                        JOptionPane.showMessageDialog(jfFelh, "Ilyen nevű felhasználó már létezik!");
                    }
                } else {
                    JOptionPane.showMessageDialog(jfFelh, "Nem egyezik a két jelszó!\nA felhasználó nem jött létre");
                }
            }

        });

        jpUjFelhasznalo.add(jlFnev,gbc2);
        gbc2.gridx = 1;
        jpUjFelhasznalo.add(jtfFelhasznalonev,gbc2);
        gbc2.gridy=1;
        gbc2.gridx=0;
        jpUjFelhasznalo.add(jlTeljesNev,gbc2);
        gbc2.gridy=1;
        gbc2.gridx=1;
        jpUjFelhasznalo.add(jtfTeljesnev,gbc2);
        gbc2.gridy=2;
        gbc2.gridx=0;
        jpUjFelhasznalo.add(jlJelszo,gbc2);
        gbc2.gridy=2;
        gbc2.gridx=1;
        jpUjFelhasznalo.add(jpfJelszo,gbc2);
        gbc2.gridy++;
        gbc2.gridx=0;
        jpUjFelhasznalo.add(jlJelszoMeg,gbc2);
        gbc2.gridx=1;
        jpUjFelhasznalo.add(jpfJelszoMeg,gbc2);
        gbc2.gridy++;
        gbc2.gridx=0;
        jpUjFelhasznalo.add(jlTipus,gbc2);
        gbc2.gridx=1;
        jpUjFelhasznalo.add(jcbTipus,gbc2);
        gbc2.gridy++;
        gbc2.gridx=0;
        jpUjFelhasznalo.add(jlAktiv,gbc2);
        gbc2.gridx=1;
        jpUjFelhasznalo.add(jcbAktiv,gbc2);
        gbc2.gridy++;
        gbc2.gridx=0;

        gbc2.gridwidth = 2;
        gbc2.fill=2;
        jpUjFelhasznalo.add(jbLetrehoz,gbc2);

        JScrollPane jscUjfelh = new JScrollPane(jpUjFelhasznalo);

        gbcMain.gridx=1;
        jpMain.add(jscUjfelh,gbcMain );
        jfFelh.add(jpMain);
        return jfFelh;
    }


}
