package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.annotation.XmlType;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DiakView extends JPanel implements AdatbazisKapcsolat {
    private Controller cont;
    private JPanel jpFooldal;
    private String fokategoria = "";
    private String alkategoria = "";
    private ArrayList<Kerdes> klist;
    //ArrayList<String> valaszok;
    private String[] valaszok;
    private int id;


    public DiakView(Controller controller,int diakId) {
        this.setLayout(new FlowLayout());
        cont = controller;
        jpFooldal = fooldalFeltolt();
        this.add(jpFooldal);
        this.id=diakId;
    }

    private JPanel fooldalFeltolt() {
        JPanel jpFooldal = new JPanel();
        ArrayList<JButton> gombokList = new ArrayList<>();

        for (int i = 0; i < cont.letezoFoKategoriak().size(); i++) {
            gombokList.add(new JButton(cont.letezoFoKategoriak().get(i)));
        }

        for (int i = 0; i < gombokList.size(); i++) {

            gombokList.get(i).addActionListener(e -> {
                JButton b = (JButton) e.getSource();
                this.removeAll();
                this.add(alkatBetolt(b.getText()));
                this.repaint();
                this.revalidate();
            });
        }

        for (int i = 0; i < gombokList.size(); i++) {
            this.add(gombokList.get(i));
        }

        return jpFooldal;
    }

    private JPanel alkatBetolt(String fokat) {

        fokategoria = fokat;
        ArrayList<String> alkatokL = cont.fokatAlkategoriai(fokat);
        JPanel jpanUj = new JPanel();
        JPanel jpVissza = new JPanel();
        jpVissza.setLayout(new GridBagLayout());
        jpanUj.setLayout(new FlowLayout());

        for (int i = 0; i < alkatokL.size(); i++) {
            JButton b = new JButton(alkatokL.get(i));
            b.addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                alkategoria = button.getText();

                try {
                    jpanUj.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    this.removeAll();
                    this.add(kerdesek(alkategoria));
                    this.repaint();
                    this.revalidate();
                } finally {
                    jpanUj.setCursor(Cursor.getDefaultCursor());
                }

            });
            jpanUj.add(b);
        }

        JButton jbVissza = new JButton("vissza");
        jbVissza.addActionListener(e -> {
            JButton b = (JButton) e.getSource();
            alkategoria = b.getText();
            this.removeAll();
            this.add(fooldalFeltolt());
            this.repaint();
            this.revalidate();
        });
        jpVissza.add(jbVissza);
        jpanUj.add(jpVissza);
        return jpanUj;
    }

    private JPanel kerdesek(String alkat) {

        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;

        JPanel jpanKerdesek = new JPanel();
        CardLayout cl = new CardLayout();
        jpanKerdesek.setLayout(cl);



        JPanel jpLepeget = new JPanel();
        jpLepeget.setLayout(new GridBagLayout());
        GridBagConstraints gbcLepeget = new GridBagConstraints();
        gbcLepeget.gridx = 0;
        gbcLepeget.gridy = 0;
        gbcLepeget.insets = new Insets(10, 5, 0, 5);

        klist = cont.alkatKerdesei(alkat);
        valaszok = new String[klist.size()];
        for (int i = 0; i < klist.size(); i++) {
            switch (klist.get(i).getTipus()) {
                case 0:
                    jpanKerdesek.add(jpHianyos(klist.get(i),i), (i + 1) + "");
                    break;
                case 1:
                    jpanKerdesek.add(jpsokKep1JoValasz(klist.get(i),i), (i + 1) + "");
                    break;
                case 2:
                    jpanKerdesek.add(jpegyKep1Valasz(klist.get(i),i), (i + 1) + "");
                    break;
                case 3:
                    jpanKerdesek.add(jpFordítas(klist.get(i),i), (i + 1) + "");
                    break;
                case 4:
                    jpanKerdesek.add(jpKerdesValasz(klist.get(i),i), (i + 1) + "");
                    break;
            }
        }

        //Lépegetés a kérdések között
        for (int i = 0; i < klist.size(); i++) {
            JButton jbLepeget = new JButton((i + 1) + "");
            jbLepeget.addActionListener(e -> {
                JButton but = (JButton) e.getSource();
                cl.show(jpanKerdesek, but.getText());
            });
            jpLepeget.add(jbLepeget, gbcLepeget);
            gbcLepeget.gridx++;
        }
        JButton jbKiertekel = new JButton("<html>"+"<u>Teszt kiértékelése</u>"+"</html>");
        jbKiertekel.addActionListener(e -> {
            int biztosAblak = JOptionPane.YES_NO_OPTION;
            int eredmeny = JOptionPane.showConfirmDialog(this, "Biztos benne?", "Figyelem", biztosAblak);
            if (eredmeny==0){
                cont.testEllenoriz(klist,valaszok,id);
                this.removeAll();
                this.add(alkatBetolt(fokategoria));
                this.repaint();
                this.revalidate();
            }
        });
        JButton vissza = new JButton("Vissza");
        vissza.addActionListener(e -> {

            this.removeAll();
            this.add(alkatBetolt(fokategoria));
            this.repaint();
            this.revalidate();
        });
        gbcLepeget.gridx = 0;
        gbcLepeget.gridy++;
        jpLepeget.add(vissza, gbcLepeget);
        gbcLepeget.gridx++;
        jpLepeget.add(jbKiertekel, gbcLepeget);


        jpMain.add(jpanKerdesek, gbcMain);
        gbcMain.gridy++;
        jpMain.add(jpLepeget, gbcMain);
        return jpMain;
    }


    private JPanel jpHianyos(Kerdes kerdes, int index) {
        JPanel jphiany = new JPanel();
        jphiany.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel jlCim = new JLabel((index+1)+". "+"Egészítse ki az alábbi mondatot");

        String[] kerdesT = kerdes.getKerdesSzovege().split("\\;");

        String kerdesResz1 = "<html>" + kerdesT[0] + "</html>";
        String valasz = kerdesT[1];
        String kerdesResz2 = "<html>" + kerdesT[2] + "</html>";

        JLabel jlKerd1 = new JLabel(kerdesResz1);
        JTextField jtfValasz = new JTextField(valasz.length());
        jtfValasz.addKeyListener(cont.gepelFiegyel(jtfValasz,index,valaszok));
        jtfValasz.isFocusOwner();
        JLabel jlKerd2 = new JLabel(kerdesResz2);


        jphiany.add(jlCim, gbc);
        gbc.insets = new Insets(20, 5, 20, 5);
        gbc.gridy++;
        gbc.gridx = 0;
        jphiany.add(jlKerd1, gbc);
        gbc.gridx++;
        jphiany.add(jtfValasz, gbc);
        gbc.gridx++;
        jphiany.add(jlKerd2, gbc);

        return jphiany;
    }


    private JPanel jpsokKep1JoValasz(Kerdes kerdes, int index) {
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        JPanel jpKepek = new JPanel();
        jpKepek.setLayout(new GridBagLayout());
        GridBagConstraints gbcKepek = new GridBagConstraints();
        gbcKepek.gridx = 0;
        gbcKepek.gridy = 0;
        JPanel jpValasz = new JPanel();
        jpValasz.setLayout(new GridBagLayout());
        GridBagConstraints gbcvalasz = new GridBagConstraints();
        gbcvalasz.gridx = 0;
        gbcvalasz.gridy = 0;

        //JLabel jlCim = new JLabel("Jelölje ki a megfelelő képet!");

        String kerdesSzov = "<html>"+ (index+1)+". "+ kerdes.getKerdesSzovege() + "</html>";
        String[] valaszLehet = kerdes.getValaszlehetosegek().split("\\;");


        ButtonGroup bgroup = new ButtonGroup();
        gbcKepek.insets = new Insets(5, 5, 5, 5);


        for (int i = 0; i < valaszLehet.length; i++) {
            System.out.println(valaszLehet[i] + " - tölt...");
            BufferedImage kep = null;
            try {
                //Forrás: https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
                kep = ImageIO.read(new File("Images/" + valaszLehet[i]));
                Image image = kep.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(image);
                JRadioButton jrb = new JRadioButton(imageIcon);
                jrb.addActionListener(cont.kepnevTombhoz(jrb,valaszok,index,valaszLehet[i]));
                jrb.addChangeListener(e -> {
                    jrb.setBackground(jpKepek.getBackground());
                    if (jrb.isSelected()){
                        jrb.setBackground(Color.BLUE);
                    }
                });

                bgroup.add(jrb);
                jpKepek.add(jrb, gbcKepek);
                gbcKepek.gridx++;
                if (gbcKepek.gridx % 2 == 0) {
                    gbcKepek.gridx = 0;
                    gbcKepek.gridy++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        JLabel jlKerdes = new JLabel(kerdesSzov);

        jpValasz.add(jlKerdes, gbcvalasz);

        jpMain.add(jpValasz, gbcMain);
        gbcMain.gridy++;
        jpMain.add(jpKepek, gbcMain);


        return jpMain;
    }

    private JPanel jpegyKep1Valasz(Kerdes kerdes, int index) {
        JPanel jpMain = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        jpMain.setLayout(gbl);
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;

        JPanel jpKep = new JPanel();
        jpKep.setLayout(gbl);
        GridBagConstraints gbcKep = new GridBagConstraints();

        JPanel jpValasz = new JPanel();
        //jpValasz.setLayout(gbl);
        GridBagConstraints gbcValasz = new GridBagConstraints();
        gbcValasz.gridx = 0;
        gbcValasz.gridy = 0;

        String[] szovEsKep = kerdes.getKerdesSzovege().split("\\;");
        String kerdesSzov = "<html>"+(index+1)+". "+szovEsKep[0]+"</html>";
        String kepPath = szovEsKep[1];

        BufferedImage kep = null;
        try {
            //Forrás: https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
            kep = ImageIO.read(new File("Images/" + kepPath));
            Image image = kep.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(image);
            jpKep.add(new JLabel(imageIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel jlKerdes = new JLabel(kerdesSzov);
        JTextField jtfValasz = new JTextField(kerdes.getHelyesValasz().length());
        jtfValasz.addKeyListener(cont.gepelFiegyel(jtfValasz,index,valaszok));
        jpValasz.add(jlKerdes,gbcValasz);
        gbcValasz.gridy++;
        jpValasz.add(jtfValasz,gbcValasz);
        jpMain.add(jpKep, gbcMain);
        gbcMain.gridx++;
        jpMain.add(jpValasz,gbcMain);
        return jpMain;
    }


    private JPanel jpFordítas(Kerdes kerdes, int index) {
        JPanel jpMain = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        jpMain.setLayout(gbl);
        GridBagConstraints gbcMain = new GridBagConstraints();

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;

        JPanel jpKerdes = new JPanel();
        jpKerdes.setLayout(gbl);
        GridBagConstraints gbcKerdes = new GridBagConstraints();
        gbcKerdes.insets = new Insets(5,5,5,5);
        gbcKerdes.gridx = 0;
        gbcKerdes.gridy = 0;

        JLabel jlCim = new JLabel((index+1)+". "+"Fordítsa le az alábbi szöveget!");
        JLabel jlKerdes = new JLabel("<html>"+kerdes.getKerdesSzovege()+"</html>");
        JTextField jtfValasz = new JTextField(20);
        jtfValasz.addKeyListener(cont.gepelFiegyel(jtfValasz,index,valaszok));
        jpKerdes.add(jlCim,gbcKerdes);
        gbcKerdes.gridy++;
        jpKerdes.add(jlKerdes,gbcKerdes);
        gbcKerdes.gridx++;
        jpKerdes.add(jtfValasz,gbcKerdes);

        jpMain.add(jpKerdes,gbcMain);
        gbcMain.gridx++;
        return jpMain;
    }

    private JPanel jpKerdesValasz(Kerdes kerdes, int index) {
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(5,5,5,5);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;

        JLabel jlCim = new JLabel((index+1)+". "+"Válaszoljon a kérdésre");
        JLabel jlKerdes = new JLabel("<html>"+kerdes.getKerdesSzovege()+"</html>");
        JTextField jtfValasz = new JTextField(20);
        jtfValasz.addKeyListener(cont.gepelFiegyel(jtfValasz,index,valaszok));

        jpMain.add(jlCim,gbcMain);
        gbcMain.gridy++;
        jpMain.add(jlKerdes,gbcMain);
        gbcMain.gridx++;
        jpMain.add(jtfValasz,gbcMain);

        return jpMain;
    }

}
