package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TanarView extends JPanel implements AdatbazisKapcsolat{
        private JButton jbUjKat;
        private JButton jbUjKerdes;
        private JButton jbKerdesSzerk;
        private JButton jbDiakokEredmenyei;
        private Controller cont;
        private JComboBox jcbFokat;
        private JComboBox jcbAlkat;
        private JComboBox jcbTipus;
        private JComboBox jcbPontszam;


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
                try {
                    controller.ujablakmegynit(diakokEredmenyei());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
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
                JLabel jlCim = new JLabel("Létező főkategóriák");
                JLabel jlFokat = new JLabel("Fő kategória");
                JLabel jlFokatLeir = new JLabel("Fő kategória leírása");
                JTextField jtfFokat = new JTextField(20);
                jtfFokat.addKeyListener(cont.pontosVesszoTilt());
                JTextField jtfFokatLeir = new JTextField(20);
                jtfFokatLeir.addKeyListener(cont.pontosVesszoTilt());
                JComboBox jcbLetezoFokategoriak = new JComboBox();
                JComboBox jcKategoriak = new JComboBox();

            for (int i = 0; i < cont.getModell().letezoFoKategoriak().size(); i++) {
                jcbLetezoFokategoriak.addItem(cont.getModell().letezoFoKategoriak().get(i));
            }

                JButton jbFokatOk = new JButton("OK");
                jbFokatOk.addActionListener(e -> {
                        if (jtfFokat.getText().length()!=0 && jtfFokatLeir.getText().length()!=0){
                                if (cont.getModell().letezoFoKategoriak().contains(jtfFokat.getText())){
                                        JOptionPane.showMessageDialog(jfUjkat,"Ilyen főkategóra már létezik!");
                                }else{
                                        cont.getModell().fokatLetrehoz(jtfFokat.getText(),jtfFokatLeir.getText());
                                        JOptionPane.showMessageDialog(jfUjkat,"Sikeresen létrehozott egy főkategóriaát");
                                    jcKategoriak.removeAllItems();
                                    for (int i = 0; i < cont.getModell().letezoFoKategoriak().size(); i++) {
                                        jcKategoriak.addItem(cont.getModell().letezoFoKategoriak().get(i));
                                    }

                                }
                        }else{
                                JOptionPane.showMessageDialog(jfUjkat,"Kérem töltse ki az összes mezőt!");
                        }

                });
                JLabel jlFokatValaszt = new JLabel("Fő kategória kiválasztása");

                jcKategoriak.setPrototypeDisplayValue("Kategóriák");
                for (int i = 0; i < cont.getModell().letezoFoKategoriak().size(); i++) {
                        jcKategoriak.addItem(cont.getModell().letezoFoKategoriak().get(i));
                }
                JLabel jlAlkat = new JLabel("Alkategória létreozása");

                JLabel jlAlkatLeir = new JLabel("Alkategória leírása");
                JTextField jtfAlkat = new JTextField(20);
                jtfAlkat.addKeyListener(cont.pontosVesszoTilt());
                JTextField jtfAlkatLeir = new JTextField(20);
                jtfAlkatLeir.addKeyListener(cont.pontosVesszoTilt());
                JButton jbAlkatOk = new JButton("OK");
                jbAlkatOk.addActionListener( e->{
                        if(jtfAlkat.getText().length()!=0 && jtfAlkatLeir.getText().length()!=0){
                                if (cont.getModell().letezoAlKategoriak().contains(jbAlkatOk.getText())){
                                        JOptionPane.showMessageDialog(jfUjkat,"Ilyen alkategória már létezik");
                                }else{
                                        cont.getModell().alkatLetrehoz(jtfAlkat.getText(),jcKategoriak.getSelectedItem().toString(),jtfAlkatLeir.getText());
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
                gbc.gridx = 1;
                gbc.gridy = 0;
                jpUjkat.add(jcbLetezoFokategoriak,gbc);
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
                for (int i = 0; i < cont.getModell().letezoFoKategoriak().size(); i++) {
                    jcbFokat.addItem(cont.getModell().letezoFoKategoriak().get(i));
                }
                jcbFokat.setSelectedIndex(0);
                for (int i = 0; i < cont.getModell().fokatAlkategoriai(jcbFokat.getSelectedItem().toString()).size(); i++) {
                    jcbAlkat.addItem(cont.getModell().fokatAlkategoriai(jcbFokat.getSelectedItem().toString()).get(i));
                }
                jcbAlkat.setSelectedIndex(0);

                jcbFokat.addActionListener(e -> {
                        jcbAlkat.removeAllItems();
                        for (int i = 0; i < cont.getModell().fokatAlkategoriai(jcbFokat.getSelectedItem().toString()).size(); i++) {
                                jcbAlkat.addItem(cont.getModell().fokatAlkategoriai(jcbFokat.getSelectedItem().toString()).get(i));
                        }
                });


                JLabel jlTipus = new JLabel("Kérdés típusa");
                jcbTipus = new JComboBox();
                for (int i = 0; i < KERDESTIPUS.length; i++) {
                        jcbTipus.addItem(KERDESTIPUS[i]);

                }
                jcbTipus.setSelectedIndex(0);



                JTextField jtfSzoveg  = new JTextField();
                jtfSzoveg.addKeyListener(cont.pontosVesszoTilt());
                jtfSzoveg.setColumns(50);
                jtfSzoveg.setToolTipText("Ide jön a kérdés szövege");
                JTextField jtfValasz  = new JTextField();
                jtfValasz.addKeyListener(cont.pontosVesszoTilt());
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
                                cont.getModell().kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),jtfSzoveg.getText(),jtfValasz.getText()
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
        jtfK1.addKeyListener(cont.pontosVesszoTilt());
        JTextField jtfHiany = new JTextField(10);
        jtfHiany.setToolTipText("Ide írhatja a hiányzó szót vagy szavakat. Amennyiben több megoldás is van ';'- vel elválasztva soroloja fel őket.");
        JTextField jtfK2 = new JTextField(20);
        jtfK2.addKeyListener(cont.pontosVesszoTilt());
        JButton jbOk = new JButton("Kérdés létrehozása");

        jbOk.addActionListener(e -> {
            String kerdes = jtfK1.getText()+";"+jtfHiany.getText()+";"+jtfK2.getText();
            cont.getModell().kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),kerdes,jtfHiany.getText(),"",(int)jcbPontszam.getSelectedItem());
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
        ArrayList<JFileChooser> jfcKepekLista = new ArrayList<>();

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
        jtfKerdes.addKeyListener(cont.pontosVesszoTilt());

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

        jfcKepekLista.add(jfcKep);
        jfcKepekLista.add(jfcKep2);
        jfcKepekLista.add(jfcKep3);
        jfcKepekLista.add(jfcKep4);
        JButton jbOk = new JButton("Kérdés létrehozása");
        jbOk.addActionListener(e -> {
            try {
                if (kulonbozoekAkepek(jfcKepekLista)) {

                    if (jfcKep.getSelectedFile() != null) {

                        cont.fileMasol(jfcKep.getSelectedFile());

                    }
                    if (jfcKep2.getSelectedFile() != null) {
                        cont.fileMasol(jfcKep2.getSelectedFile());
                    }
                    if (jfcKep3.getSelectedFile() != null) {
                        cont.fileMasol(jfcKep3.getSelectedFile());
                    }
                    if (jfcKep4.getSelectedFile() != null) {
                        cont.fileMasol(jfcKep4.getSelectedFile());
                    }
                    if (jcbValasz.getItemCount() != 0) {
                        String valaszLehetS = "";

                        if (jtfFile1.getText().length() != 0) {
                            valaszLehetS += jtfFile1.getText() + ";";
                        }
                        if (jtfFile2.getText().length() != 0) {
                            valaszLehetS += jtfFile2.getText() + ";";
                        }
                        if (jtfFile3.getText().length() != 0) {
                            valaszLehetS += jtfFile3.getText() + ";";
                        }
                        if (jtfFile4.getText().length() != 0) {
                            valaszLehetS += jtfFile4.getText() + ";";
                        }
                        valaszLehetS = valaszLehetS.substring(0, valaszLehetS.length() - 1);
                        //System.out.println(valaszLehetS);
                        cont.getModell().kerdesLetrehoz(jcbFokat.getSelectedItem().toString(), jcbAlkat.getSelectedItem().toString(), jcbTipus.getSelectedIndex(), jtfKerdes.getText(), jcbValasz.getSelectedItem().toString(), valaszLehetS, (int) jcbPontszam.getSelectedItem());
                        JOptionPane.showMessageDialog(null, "Sikeresen létrehozta a kérdést!");

                    } else {
                        JOptionPane.showMessageDialog(null, "Jelöljön ki helyes választ!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nem töltheti fel ugyanazt a képet többször!");
                }
            }catch (FileAlreadyExistsException f){
                f.printStackTrace();
                JOptionPane.showMessageDialog(null, "Egy ilyen nevű fájl már létezik. A kérdés nem jött létre");
            }catch (IOException b){
                b.printStackTrace();
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

    private boolean kulonbozoekAkepek(ArrayList<JFileChooser> jfcKepekLista) {
        boolean kulonbozoek = true;
        int j=0;
            do {

                if (jfcKepekLista.get(j)!=null){
                    JFileChooser jf = jfcKepekLista.get(j);
                    for (int i = 0; i < jfcKepekLista.size() ; i++) {

                        if (i!=j && kulonbozoek&& jfcKepekLista.get(i).getSelectedFile()!=null && jf.getSelectedFile()!=null){
                            System.out.println("j: "+j);
                            System.out.println("i: "+i);
                            System.out.println("j path : "+jf.getSelectedFile().getPath());
                            System.out.println("i path : "+jfcKepekLista.get(i).getSelectedFile().getPath());
                            kulonbozoek=!(jf.getSelectedFile().getPath().equals(jfcKepekLista.get(i).getSelectedFile().getPath()));
                        }
                    }
                }
                j++;
            }while(j<jfcKepekLista.size() && kulonbozoek);

        return kulonbozoek;
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
        jtfKerdes.addKeyListener(cont.pontosVesszoTilt());
        JTextField jtfValasz = new JTextField(15);


        JButton jbOK = new JButton("Kérdés létrehozása");
        jbOK.addActionListener(e -> {
            String kerdesEsKep = "";
            kerdesEsKep+=jtfKerdes.getText()+";";
            if (jfcKep.getSelectedFile()!=null) {
            kerdesEsKep+=jfcKep.getSelectedFile().getName();
            }else{
                JOptionPane.showMessageDialog(null,"Kép hozzáadása kötelező!");
            }
            try {
                cont.fileMasol(jfcKep.getSelectedFile());
            } catch (FileAlreadyExistsException e1) {
                JOptionPane.showMessageDialog(null,"Ilyen nevű kép már létezik. A kérdés nem jött létre");
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
                cont.getModell().kerdesLetrehoz(jcbFokat.getSelectedItem().toString(), jcbAlkat.getSelectedItem().toString(), jcbTipus.getSelectedIndex(), kerdesEsKep
                        , jtfValasz.getText(), "", (int) jcbPontszam.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Sikeresen létrehozta a kérdést!");
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
        jtfKerd.addKeyListener(cont.pontosVesszoTilt());
        JTextField jtfValsz = new JTextField(20);
        jtfValsz.setToolTipText("Ide írhatja a helyes választ. Amennyiben több megoldás is van ';'- vel elválasztva soroloja fel őket.");

        JButton jbOk = new JButton("Kérdés létrehozása");

        jbOk.addActionListener(e -> {
            cont.getModell().kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),jtfKerd.getText()
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
        jtfKerd.addKeyListener(cont.pontosVesszoTilt());
        JTextField jtfValsz = new JTextField(20);
        jtfValsz.setToolTipText("Ide írhatja a helyes választ. Amennyiben több megoldás is van ';'- vel elválasztva soroloja fel őket.");

        JButton jbOk = new JButton("Kérdés létrehozása");
        jbOk.addActionListener(e -> {
            cont.getModell().kerdesLetrehoz(jcbFokat.getSelectedItem().toString(),jcbAlkat.getSelectedItem().toString(),jcbTipus.getSelectedIndex(),jtfKerd.getText()
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

                jfKerdesSzerk.add(kerdesSzerkPanel(jfKerdesSzerk));
                return jfKerdesSzerk;
        }

       private JPanel kerdesSzerkPanel(JFrame jfKerdesSzerk){
           JPanel jpMain = new JPanel(new GridBagLayout());
           JPanel jpKerdesSzerk = new JPanel(new GridBagLayout());
           JScrollPane jsPane = new JScrollPane(jpKerdesSzerk);

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
           for (int i = 0; i < cont.getModell().letezoKerdesek().size(); i++) {
               int kerdesId = cont.getModell().letezoKerdesek().get(i).getId();
               gbc.gridx=0;
               JLabel jlFokatSzerk = new JLabel(cont.getModell().letezoKerdesek().get(i).getFoKategoria());
               jpKerdesSzerk.add(jlFokatSzerk,gbc);
               gbc.gridx++;
               JLabel jlAlkatSzerk = new JLabel(cont.getModell().letezoKerdesek().get(i).getAlkategoria());
               jpKerdesSzerk.add(jlAlkatSzerk,gbc);
               gbc.gridx++;
               JLabel jlTipusSzerk = new JLabel(cont.getModell().letezoKerdesek().get(i).getTipusNev());
               jpKerdesSzerk.add(jlTipusSzerk,gbc);
               gbc.gridx++;
               JTextField jtfKerdes = new JTextField(cont.getModell().letezoKerdesek().get(i).getKerdesSzovege());
               jpKerdesSzerk.add(jtfKerdes,gbc);
               gbc.gridx++;
               JTextField jtfHelyesValasz = new JTextField(cont.getModell().letezoKerdesek().get(i).getHelyesValasz());
               jpKerdesSzerk.add(jtfHelyesValasz,gbc);
               gbc.gridx++;
               JTextField jtfvalaszLehet=new JTextField(cont.getModell().letezoKerdesek().get(i).getValaszlehetosegek());
               jpKerdesSzerk.add(jtfvalaszLehet,gbc);
               gbc.gridx++;
               JComboBox jcbPontszam = new JComboBox();
               jcbPontszam.addItem(1);
               jcbPontszam.addItem(2);
               jcbPontszam.addItem(3);
               jcbPontszam.addItem(4);
               jcbPontszam.addItem(5);
               jcbPontszam.setSelectedItem(cont.getModell().letezoKerdesek().get(i).getPontszam());
               jpKerdesSzerk.add(jcbPontszam,gbc);
               gbc.gridx++;
               JButton jbOk= new JButton("Elment");
               jbOk.addActionListener(e -> {
                   try {
                       cont.getModell().kerdesSzerk(jtfKerdes.getText(),jtfHelyesValasz.getText(),jtfvalaszLehet.getText(),(int)jcbPontszam.getSelectedItem(),kerdesId);
                   } catch (SQLException e1) {
                       e1.printStackTrace();
                   }
                   JOptionPane.showMessageDialog(jfKerdesSzerk,"Sikeres Változtatás");

               });
               jpKerdesSzerk.add(jbOk,gbc);
               gbc.gridx++;
               JButton jbTorol= new JButton("Kérdés törlése");
               jbTorol.addActionListener(e ->{
                   int biztosAblak = JOptionPane.YES_NO_OPTION;
                   int eredmeny = JOptionPane.showConfirmDialog(jfKerdesSzerk, "Biztos benne? A kérdés ezután nem lesz elérhető.", "Figyelem",biztosAblak);
                   if (eredmeny==0){
                       try {
                           cont.getModell().kerdesTorol(kerdesId);
                          // jfKerdesSzerk.removeAll();
                          /*  jfKerdesSzerk.add(kerdesSzerkPanel(jfKerdesSzerk));
                            jfKerdesSzerk.invalidate();
                            jfKerdesSzerk.revalidate();
                            jfKerdesSzerk.repaint();*/
                       } catch (SQLException e1) {
                           e1.printStackTrace();
                       }
                   }
               });
               jpKerdesSzerk.add(jbTorol,gbc);
               gbc.gridy++;
           }



           jsPane.setMinimumSize(new Dimension(600,600));
           jsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
           jpMain.add(jsPane);
           return jpMain;
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

            for (int i = 0; i < cont.getModell().diakEredmenyLista().size(); i++) {
                gbc.gridx=0;
                jpDiakEredmeny.add(new JLabel(cont.getModell().diakNev(Integer.parseInt(cont.getModell().diakEredmenyLista().get(i).get(0)))),gbc);
                gbc.gridx++;
                for (int j = 1; j < cont.getModell().diakEredmenyLista().get(i).size(); j++) {
                    jpDiakEredmeny.add(new JLabel(cont.getModell().diakEredmenyLista().get(i).get(j)),gbc);
                    gbc.gridx++;
                }
                gbc.gridy++;
            }

                JScrollPane jscp = new JScrollPane(jpDiakEredmeny);
                jscp.setPreferredSize(new Dimension(900,600));
                jscp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                jpMain.add(jscp);
                jfdiakEredmeny.add(jpMain);
                return jfdiakEredmeny;
        }
}
