package com.company;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminView extends JPanel implements AdatbazisKapcsolat {
    private JButton jbUjKat;
    private JButton jbUjKerdes;
    private JButton jbKerdesSzerk;
    private JButton jbDiakokEredmenyei;
    private JButton jbFelhasznalok;
    private Controller cont;
    private JComboBox jcbFokat;
    private JComboBox jcbAlkat;
    private JComboBox jcbTipus;
    private JComboBox jcbPontszam;

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
            try {
                conntroller.ujablakmegynit(diakokEredmenyei());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        JPanel jpUjkerdes = new JPanel();
        jpUjkerdes.setLayout(gbl);

        JLabel jlCim = new JLabel("Új Kérdés létrehozása");
        JLabel jlKerdesFokat = new JLabel("Kérdés főkategóriája");
        JLabel jlKerdesAlkat = new JLabel("Kérdés alkategóriája");
        //JLabel jlKerdesSzoveg = new JLabel("Kérdés szövege");
        //JLabel jlValasz = new JLabel("Válasz");
        //JLabel jlValaszLehet = new JLabel("Válasz lehetőségek");
        JLabel jlPontszam = new JLabel("Kérdés pontszáma");

        jcbFokat = new JComboBox();
        jcbAlkat = new JComboBox();
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
        jcbTipus = new JComboBox();
        for (int i = 0; i < KERDESTIPUS.length; i++) {
            jcbTipus.addItem(KERDESTIPUS[i]);
        }
        jcbTipus.setSelectedIndex(0);

        JTextField jtfSzoveg  = new JTextField();
        jtfSzoveg.setColumns(50);
        jtfSzoveg.setToolTipText("Ide jön a kérdés szövege");
        JTextField jtfValasz  = new JTextField();
        JTextField jtfValaszLehetosegek  = new JTextField(50);
        jtfValasz.setColumns(50);
        jcbPontszam = new JComboBox();
        jcbPontszam.setPrototypeDisplayValue("pont");
        jcbPontszam.addItem(1);
        jcbPontszam.addItem(2);
        jcbPontszam.addItem(3);
        jcbPontszam.addItem(4);
        jcbPontszam.addItem(5);

        JButton jbOk = new JButton("Ok");
        jbOk.addActionListener(e -> {
            if (jtfSzoveg.getText().length()!=0 && jtfValasz.getText().length()!=0 && jcbAlkat.getSelectedItem()!=null){
                cont.kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),jtfSzoveg.getText(),jtfValasz.getText()
                        ,jtfValaszLehetosegek.getText(),(int)jcbPontszam.getSelectedItem());
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
        jpUjkerdes.add(jlPontszam,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        jpUjkerdes.add(jcbPontszam,gbc);
        JPanel jpTipus = new JPanel();
        jpTipus.add(jpHianyos());

        jcbTipus.addActionListener(e -> {
            jpTipus.removeAll();
            switch (jcbTipus.getSelectedIndex()){
                case 0 :jpTipus.add(jpHianyos()); break;
                case 1 :jpTipus.add(jpSokKep());  break;
                case 2 :jpTipus.add(jpEgykep());  break;
                case 3 :jpTipus.add(jpForditas()); break;
                case 4 :jpTipus.add(jpKerdesValasz()); break;
            }
            jpUjkerdes.revalidate();
        });
        gbc.gridx = 0;
        gbc.gridy = 4;

        jpMain.add(jpUjkerdes,gbcMain);
        gbcMain.gridy++;
        jpMain.add(jpTipus,gbcMain);


        jfUjkerdes.add(jpMain);
        return jfUjkerdes;
    }

    private JPanel jpHianyos(){
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();

        JLabel jlKerdes1 = new JLabel("Mondat első része");
        JLabel jlHianyzo = new JLabel("Hiányzó mondatrész");
        JLabel jlKerdes2 = new JLabel("Mondat második része");

        JTextField jtfK1 = new JTextField(20);
        JTextField jtfHiany = new JTextField(10);
        jtfHiany.setToolTipText("Ide írhatja a hiányzó szót vagy szavakat. Amennyiben több megoldás is van ';'- vel elválasztva soroloja fel őket.");
        JTextField jtfK2 = new JTextField(20);
        JButton jbOk = new JButton("Kérdés létrehozása");

        jbOk.addActionListener(e -> {
            String kerdes = jtfK1.getText()+";"+jtfHiany.getText()+";"+jtfK2.getText();
            cont.kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),kerdes,jtfHiany.getText(),"",(int)jcbPontszam.getSelectedItem());
            JOptionPane.showMessageDialog(null,"Sikeresen létrehozta a kérdést!");

        });

        gbcMain.insets = new Insets(5,5,5,5);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        jpMain.add(jlKerdes1,gbcMain);
        gbcMain.gridy++;
        jpMain.add(jtfK1,gbcMain);

        gbcMain.gridx++;
        gbcMain.gridy = 0;
        jpMain.add(jlHianyzo,gbcMain);
        gbcMain.gridy++;
        jpMain.add(jtfHiany,gbcMain);

        gbcMain.gridx++;
        gbcMain.gridy = 0;
        jpMain.add(jlKerdes2,gbcMain);
        gbcMain.gridy++;
        jpMain.add(jtfK2,gbcMain);

        gbcMain.gridy++;
        gbcMain.gridx = 0;
        gbcMain.gridwidth=3;
        gbcMain.fill=3;
        jpMain.add(jbOk,gbcMain);

        return jpMain;
    }

    private JPanel jpSokKep(){
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        JFileChooser jfcKep = new JFileChooser();
        JFileChooser jfcKep2 = new JFileChooser();
        JFileChooser jfcKep3 = new JFileChooser();
        JFileChooser jfcKep4 = new JFileChooser();
        jfcKep.setMultiSelectionEnabled(false);
        jfcKep2.setMultiSelectionEnabled(false);
        jfcKep3.setMultiSelectionEnabled(false);
        jfcKep4.setMultiSelectionEnabled(false);

        jfcKep.setFileFilter( new FileNameExtensionFilter("Képek", ImageIO.getReaderFileSuffixes()));
        jfcKep2.setFileFilter( new FileNameExtensionFilter("Képek", ImageIO.getReaderFileSuffixes()));
        jfcKep3.setFileFilter( new FileNameExtensionFilter("Képek", ImageIO.getReaderFileSuffixes()));
        jfcKep4.setFileFilter( new FileNameExtensionFilter("Képek", ImageIO.getReaderFileSuffixes()));

        JLabel jlKerdes = new JLabel("Kérdés szövege");
        JTextField jtfKerdes = new JTextField(20);

        JTextField jtfFile1 = new JTextField(10);
        jfcKep.addActionListener(cont.fileNevBeir(jfcKep,jtfFile1));
        jtfFile1.setEnabled(false);
        jtfFile1.setDisabledTextColor(Color.BLACK);
        JButton jbFile1 = new JButton("Tallóz...");
        jbFile1.addActionListener(cont.fileChooserMegynit(jfcKep));

        JTextField jtfFile2 = new JTextField(10);
        jfcKep2.addActionListener(cont.fileNevBeir(jfcKep2,jtfFile2));
        jtfFile2.setEnabled(false);
        jtfFile2.setDisabledTextColor(Color.BLACK);
        JButton jbFile2 = new JButton("Tallóz...");
        jbFile2.addActionListener(cont.fileChooserMegynit(jfcKep2));

        JTextField jtfFile3 = new JTextField(10);
        jfcKep3.addActionListener(cont.fileNevBeir(jfcKep3,jtfFile3));
        jtfFile3.setEnabled(false);
        jtfFile3.setDisabledTextColor(Color.BLACK);
        JButton jbFile3 = new JButton("Tallóz...");
        jbFile3.addActionListener(cont.fileChooserMegynit(jfcKep3));

        JTextField jtfFile4 = new JTextField(10);
        jfcKep4.addActionListener(cont.fileNevBeir(jfcKep4,jtfFile4));
        jtfFile4.setEnabled(false);
        jtfFile4.setDisabledTextColor(Color.BLACK);
        JButton jbFile4 = new JButton("Tallóz...");
        jbFile4.addActionListener(cont.fileChooserMegynit(jfcKep4));

        JLabel jlValasz = new JLabel("Helyes válasz fájl neve: ");
        JLabel jlFileValaszt = new JLabel("Válassza ki a képeket!");
        JComboBox jcbValasz = new JComboBox();
        jcbValasz.setPrototypeDisplayValue("AAAAAAAAA12345.jpg");

        JButton jbOk = new JButton("Kérdés létrehozása");
        jbOk.addActionListener(e -> {
            if (jfcKep.getSelectedFile().getName().length()!=0){
                cont.fileMasol(jfcKep.getSelectedFile());
            }
            if (jfcKep2.getSelectedFile().getName().length()!=0){
                cont.fileMasol(jfcKep2.getSelectedFile());
            }
            if (jfcKep3.getSelectedFile().getName().length()!=0){
                cont.fileMasol(jfcKep3.getSelectedFile());
            }
            if (jfcKep4.getSelectedFile().getName().length()!=0){
                cont.fileMasol(jfcKep4.getSelectedFile());
            }
            if (jcbValasz.getItemCount()!=0) {
                String valaszLehetS = "";

                if (jtfFile1.getText().length()!=0){
                    valaszLehetS +=jtfFile1.getText()+ ";";
                }
                if (jtfFile2.getText().length()!=0){
                    valaszLehetS +=jtfFile2.getText()+ ";";
                }
                if (jtfFile3.getText().length()!=0){
                    valaszLehetS +=jtfFile3.getText()+ ";";
                }
                if (jtfFile4.getText().length()!=0){
                    valaszLehetS +=jtfFile4.getText()+ ";";
                }
                valaszLehetS = valaszLehetS.substring(0, valaszLehetS.length() - 1);
                cont.kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),jtfKerdes.getText(),jcbValasz.getSelectedItem().toString(),valaszLehetS,(int)jcbPontszam.getSelectedItem());
                JOptionPane.showMessageDialog(null,"Sikeresen létrehozta a kérdést!");
            }else{
                JOptionPane.showMessageDialog(null,"Jelöljön ki helyes választ!");
            }

        });
        jcbValasz.addPopupMenuListener(cont.fajlNevek(jtfFile1,jtfFile2,jtfFile3,jtfFile4,jcbValasz));

        gbcMain.insets = new Insets(5,5,5,5);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        jpMain.add(jlKerdes,gbcMain);
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        jpMain.add(jtfKerdes,gbcMain);


        gbcMain.gridx = 1;
        gbcMain.gridy = 0;
        jpMain.add(jlFileValaszt,gbcMain);
        gbcMain.gridy++;
        jpMain.add(jtfFile1,gbcMain);

        gbcMain.gridx++;
        jpMain.add(jbFile1,gbcMain);

        gbcMain.gridx = 1;
        gbcMain.gridy++;
        jpMain.add(jtfFile2,gbcMain);
        gbcMain.gridx++;
        jpMain.add(jbFile2,gbcMain);
        gbcMain.gridx++;
        gbcMain.gridx = 1;
        gbcMain.gridy++;
        jpMain.add(jtfFile3,gbcMain);
        gbcMain.gridx++;
        jpMain.add(jbFile3,gbcMain);
        gbcMain.gridx++;
        gbcMain.gridx = 1;
        gbcMain.gridy++;
        jpMain.add(jtfFile4,gbcMain);
        gbcMain.gridx++;
        jpMain.add(jbFile4,gbcMain);

        gbcMain.gridx=1;
        gbcMain.gridy++;
        gbcMain.gridwidth=2;
        jpMain.add(jbOk,gbcMain);

        gbcMain.gridwidth=1;
        gbcMain.gridx=3;
        gbcMain.gridy=0;
        jpMain.add(jlValasz,gbcMain);

        gbcMain.gridx=3;
        gbcMain.gridy=1;
        jpMain.add(jcbValasz,gbcMain);

        return jpMain;
    }

    private JPanel jpEgykep(){
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        JLabel jlKerdes = new JLabel("Kérdés szövege");
        JLabel jlValasz = new JLabel("Válasz");
        JLabel jlFile = new JLabel("Kép neve");
        JTextField jtfFile = new JTextField(10);
        JFileChooser jfcKep = new JFileChooser();
        jfcKep.addActionListener(cont.fileNevBeir(jfcKep,jtfFile));
        jtfFile.setEnabled(false);
        jtfFile.setDisabledTextColor(Color.BLACK);
        JButton jbFile = new JButton("Tallóz...");
        jbFile.addActionListener(cont.fileChooserMegynit(jfcKep));
        jfcKep.setFileFilter( new FileNameExtensionFilter("Képek", ImageIO.getReaderFileSuffixes()));

        JTextField jtfKerdes = new JTextField(15);
        JTextField jtfValasz = new JTextField(15);


        JButton jbOK = new JButton("Kérdés létrehozása");
        jbOK.addActionListener(e -> {
            String kerdesEsKep = "";
            kerdesEsKep+=jtfKerdes.getText()+";";
            kerdesEsKep+=jfcKep.getSelectedFile().getName();

            cont.fileMasol(jfcKep.getSelectedFile());
            cont.kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),kerdesEsKep
                    ,jtfValasz.getText(),"",(int)jcbPontszam.getSelectedItem());
            JOptionPane.showMessageDialog(null,"Sikeresen létrehozta a kérdést!");

        });

        gbcMain.insets= new Insets(5,5,5,5);
        gbcMain.gridx=0;
        gbcMain.gridy=0;
        jpMain.add(jlKerdes,gbcMain);
        gbcMain.gridx=0;
        gbcMain.gridy=1;
        jpMain.add(jtfKerdes,gbcMain);
        gbcMain.gridx=1;
        gbcMain.gridy=0;
        jpMain.add(jlFile,gbcMain);
        gbcMain.gridx=1;
        gbcMain.gridy=1;
        jpMain.add(jtfFile,gbcMain);
        gbcMain.gridx=1;
        gbcMain.gridy=2;
        jpMain.add(jbFile,gbcMain);
        gbcMain.gridx=2;
        gbcMain.gridy=0;
        jpMain.add(jlValasz,gbcMain);
        gbcMain.gridx=2;
        gbcMain.gridy=1;
        jpMain.add(jtfValasz,gbcMain);
        gbcMain.gridx=0;
        gbcMain.gridy=3;
        gbcMain.gridwidth=3;
        jpMain.add(jbOK,gbcMain);

        return jpMain;
    }

    private JPanel jpForditas(){
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();

        JLabel jlKerd = new JLabel("Fordítandó szöveg");
        JLabel jlValasz = new JLabel("Helyes válasz(ok)");

        JTextField jtfKerd = new JTextField(20);
        JTextField jtfValsz = new JTextField(20);
        jtfValsz.setToolTipText("Ide írhatja a helyes választ. Amennyiben több megoldás is van ';'- vel elválasztva soroloja fel őket.");

        JButton jbOk = new JButton("Kérdés létrehozása");

        jbOk.addActionListener(e -> {
            cont.kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),jtfKerd.getText()
                    ,jtfValsz.getText(),"",(int)jcbPontszam.getSelectedItem());
            JOptionPane.showMessageDialog(null,"Sikeresen létrehozta a kérdést!");
        });

        gbcMain.insets= new Insets(5,5,5,5);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        jpMain.add(jlKerd,gbcMain);
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        jpMain.add(jtfKerd,gbcMain);
        gbcMain.gridx = 1;
        gbcMain.gridy = 0;
        jpMain.add(jlValasz,gbcMain);
        gbcMain.gridx = 1;
        gbcMain.gridy = 1;
        jpMain.add(jtfValsz,gbcMain);

        gbcMain.gridx = 0;
        gbcMain.gridy = 2;
        gbcMain.gridwidth=2;
        jpMain.add(jbOk,gbcMain);

        return jpMain;
    }

    private JPanel jpKerdesValasz(){
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();

        JLabel jlKerd = new JLabel("Kérdés szövege");
        JLabel jlValasz = new JLabel("Helyes válasz(ok)");

        JTextField jtfKerd = new JTextField(20);
        JTextField jtfValsz = new JTextField(20);
        jtfValsz.setToolTipText("Ide írhatja a helyes választ. Amennyiben több megoldás is van ';'- vel elválasztva soroloja fel őket.");

        JButton jbOk = new JButton("Kérdés létrehozása");
        jbOk.addActionListener(e -> {
            cont.kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),jtfKerd.getText()
                    ,jtfValsz.getText(),"",(int)jcbPontszam.getSelectedItem());
            JOptionPane.showMessageDialog(null,"Sikeresen létrehozta a kérdést!");
        });

        gbcMain.insets= new Insets(5,5,5,5);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        jpMain.add(jlKerd,gbcMain);
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        jpMain.add(jtfKerd,gbcMain);
        gbcMain.gridx = 1;
        gbcMain.gridy = 0;
        jpMain.add(jlValasz,gbcMain);
        gbcMain.gridx = 1;
        gbcMain.gridy = 1;
        jpMain.add(jtfValsz,gbcMain);

        gbcMain.gridx = 0;
        gbcMain.gridy = 2;
        gbcMain.gridwidth=2;
        jpMain.add(jbOk,gbcMain);

        return jpMain;
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
            int kerdesId = cont.letezoKerdesek().get(i).getId();
            gbc.gridx=0;
            JLabel jlFokatSzerk = new JLabel(cont.letezoKerdesek().get(i).getFoKategoria());
            jpKerdesSzerk.add(jlFokatSzerk,gbc);
            gbc.gridx++;
            JLabel jlAlkatSzerk = new JLabel(cont.letezoKerdesek().get(i).getAlkategoria());
            jpKerdesSzerk.add(jlAlkatSzerk,gbc);
            gbc.gridx++;
            JLabel jlTipusSzerk = new JLabel(cont.letezoKerdesek().get(i).getTipusNev());
            jpKerdesSzerk.add(jlTipusSzerk,gbc);
            gbc.gridx++;
            JTextField jtfKerdes = new JTextField(cont.letezoKerdesek().get(i).getKerdesSzovege());
            jpKerdesSzerk.add(jtfKerdes,gbc);
            gbc.gridx++;
            JTextField jtfHelyesValasz = new JTextField(cont.letezoKerdesek().get(i).getHelyesValasz());
            jpKerdesSzerk.add(jtfHelyesValasz,gbc);
            gbc.gridx++;
            JTextField jtfvalaszLehet=new JTextField(cont.letezoKerdesek().get(i).getValaszlehetosegek());
            jpKerdesSzerk.add(jtfvalaszLehet,gbc);
            gbc.gridx++;
            JComboBox jcbPontszam = new JComboBox();
            jcbPontszam.addItem(1);
            jcbPontszam.addItem(2);
            jcbPontszam.addItem(3);
            jcbPontszam.addItem(4);
            jcbPontszam.addItem(5);
            jcbPontszam.setSelectedItem(cont.letezoKerdesek().get(i).getPontszam());
            jpKerdesSzerk.add(jcbPontszam,gbc);
            gbc.gridx++;
            JButton jbOk= new JButton("Elment");
            jbOk.addActionListener(e -> {
                try {
                    cont.kerdesSzerk(jtfKerdes.getText(),jtfHelyesValasz.getText(),jtfvalaszLehet.getText(),(int)jcbPontszam.getSelectedItem(),kerdesId);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(jfKerdesSzerk,"Sikeres Változtatás");
            });
            jpKerdesSzerk.add(jbOk,gbc);
            gbc.gridy++;
        }


        JScrollPane jsPane = new JScrollPane(jpKerdesSzerk);
        jsPane.setMinimumSize(new Dimension(600,600));

        jsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jpMain.add(jsPane);
        jfKerdesSzerk.add(jpMain);
        return jfKerdesSzerk;
    }


    private JFrame diakokEredmenyei() throws SQLException {
        JFrame jfdiakEredmeny = new JFrame();
        jfdiakEredmeny.setTitle("Quiz 1.0");
        jfdiakEredmeny.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfdiakEredmeny.setMinimumSize(new Dimension(1000,700));
        JPanel jpMain = new JPanel();
        JPanel jpDiakEredmeny = new JPanel();
        jpDiakEredmeny.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel jlFnev = new JLabel("<html><u>Diák neve</u></html>");
        JLabel jlFokat = new JLabel("<html><u>Kérdés főkategóriája</u></html>");
        JLabel jlAlkat = new JLabel("<html><u>Kérdés alkategóriája</u></html>");
        JLabel jlKerdesSorrend = new JLabel("<html><u>Kérdések sorrendje</u></html>");
        JLabel jlPontszamSorrend = new JLabel("<html><u>Pontszámok sorrendje</u></html>");
        JLabel jlDiakValaszok = new JLabel("<html><u>Diák válaszai</u></html>");
        JLabel jlLeadás = new JLabel("<html><u>Teszt leadásának időpontja</u></html>");

        gbc.insets = new Insets(5,10,5,10);
        gbc.gridx=0;
        gbc.gridy=0;
        jpDiakEredmeny.add(jlFnev,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlFokat,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlAlkat,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlKerdesSorrend,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlPontszamSorrend,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlDiakValaszok,gbc);
        gbc.gridx++;
        jpDiakEredmeny.add(jlLeadás,gbc);
        gbc.gridy=1;

        for (int i = 0; i < cont.diakEredmenyLista().size(); i++) {
            gbc.gridx=0;
            for (int j = 0; j < cont.diakEredmenyLista().get(i).size(); j++) {
                jpDiakEredmeny.add(new JLabel(cont.diakEredmenyLista().get(i).get(j)),gbc);
                gbc.gridx++;
            }
            gbc.gridy++;
        }

        JScrollPane jscp = new JScrollPane(jpDiakEredmeny);
        jscp.setMinimumSize(new Dimension(600,600));
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
                try {
                    cont.felhasznaloFrissit(jcbAktiv.getSelectedIndex(),jcbTipus.getSelectedIndex(),Integer.parseInt(jlId.getText()));

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            jpFelh.add(jbMent,gbc);

            gbc.gridy++;
            gbc.gridx = 0;
        }



        JScrollPane jScrollPane = new JScrollPane(jpFelh);
        jScrollPane.setMinimumSize(new Dimension(600,600));
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
        JPasswordField jpfJelszo = new JPasswordField(20){
            @Override
            public void paste() {}
        };
        JPasswordField jpfJelszoMeg = new JPasswordField(20){
            @Override
            public void paste() {}
        };
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
                    if (!cont.getModell().letezikEfelhasznalo(jtfFelhasznalonev.getText())) {
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
        jscUjfelh.setMinimumSize(new Dimension(600,600));

        gbcMain.gridx=1;
        jpMain.add(jscUjfelh,gbcMain );
        jfFelh.add(jpMain);
        return jfFelh;
    }


}
