package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    private Modell modell;
    private View view;

    public Modell getModell() {
        return modell;
    }


    public Controller() {
        modell = new Modell();
        view = new View(this);
    }


    /**
     * Ellenőrzia a tesztet
     * @param kerdesek
     * @param valaszok
     * @param diakId
     */
    public void testEllenoriz(ArrayList<Kerdes> kerdesek,String[] valaszok,int diakId){
        int elertpontszam=0;
        int maxPontszam=0;

        for (int i = 0; i < kerdesek.size(); i++) {
            maxPontszam+=kerdesek.get(i).getPontszam();
        }

        String kerdesSorrend="";
        for (int j = 0; j < kerdesek.size(); j++) {
            kerdesSorrend+=kerdesek.get(j).getId()+";";
        }
        kerdesSorrend=kerdesSorrend.substring(0,kerdesSorrend.length()-1);

        String pontSzamSorrend = "";
        String[] pontSzamTomb = new String[kerdesek.size()];

        String diakValaszai = "";
        String[] diakValaszaiTomb = new String[kerdesek.size()];

        for (int k = 0; k < pontSzamTomb.length; k++) {
            pontSzamTomb[k] = "0";
        }

        for (int k = 0; k < diakValaszaiTomb.length; k++) {
            diakValaszaiTomb[k] = "nem válaszolt";
        }

        for (int i = 0; i < valaszok.length; i++) {
            String[] spl = kerdesek.get(i).getHelyesValasz().split("\\;");
            if (spl.length!=1){
                for (int j = 0; j < spl.length; j++) {
                    if (spl[j].equals(valaszok[i])){
                        pontSzamTomb[i]=kerdesek.get(i).getPontszam()+"";
                        elertpontszam+=kerdesek.get(i).getPontszam();
                    }
                }
            }
            else if (valaszok[i]!=null && valaszok[i].equals(kerdesek.get(i).getHelyesValasz())) {
                pontSzamTomb[i]=kerdesek.get(i).getPontszam() + "";
                elertpontszam+=kerdesek.get(i).getPontszam();
            }
            if (valaszok[i]!=null){
                diakValaszaiTomb[i]=valaszok[i];
            }
        }
        for (int l = 0; l < pontSzamTomb.length; l++) {
            pontSzamSorrend+=pontSzamTomb[l]+";";
        }

        for (int l = 0; l < diakValaszaiTomb.length; l++) {
            diakValaszai+=diakValaszaiTomb[l]+";";
        }

        pontSzamSorrend=pontSzamSorrend.substring(0,pontSzamSorrend.length()-1);
        diakValaszai=diakValaszai.substring(0,diakValaszai.length()-1);

        modell.diakEredFelvisz(diakId,kerdesek.get(0).getFoKategoria(),kerdesek.get(0).getAlkategoria(),kerdesSorrend,pontSzamSorrend,diakValaszai);

        JOptionPane.showMessageDialog(null,"<html>A tesztnek vége, az ön eredménye:<br> Elérető maximális pontszám: "+maxPontszam+"" +
                "<br>Elért pontszám: "+elertpontszam+ "</html>");
    }




    /**
     * A bejelentkezés folyamata. Itt jön létre a Felhasználó objektum.
     * @param fnev
     */
        public void bejelentkezesFolyamat(String fnev) throws SQLException {
        int felhasznaloId = -1;

        felhasznaloId = getModell().felhasznId(fnev);

        modell.setFelhasznalo(modell.felhasznaloBetolt(felhasznaloId));
        int felhasznaloTipus = modell.felhasznaloBetolt(felhasznaloId).getTipus();

        switch (felhasznaloTipus){
            case 0:
                view.getJt().addTab("Quiz",new DiakView(this,felhasznaloId));
                view.getContentPane().setBackground(new Color(164, 197, 249));
                view.setTitle("DiakQuiz");
                break;
            case 1:
                view.getJt().addTab("Quiz",new TanarView(this));
                view.getContentPane().setBackground(new Color(249, 244, 164));
                view.setTitle("TanarQuiz");
                break;
            case 2:
                view.getJt().addTab("Quiz",new AdminView(this));
                view.getContentPane().setBackground(new Color(249, 164, 164));
                view.setTitle("AdminQuiz");
                break;
                default:
                    System.out.println("Nincs ilyen típus!");
                    break;
        }
        view.felhasznaloPanelBetolt(view.getJt());
        modell.naplozas(felhasznaloId);
    }

    /**
     * Két String típusu változót vet össze a függvény, megnézi, hogy egyeznek-e.
     * @param jelszo
     * @param jelszomeg
     * @return
     */
    public boolean egyezoJelszo(String jelszo, String jelszomeg){
        return jelszo.equals(jelszomeg)?true:false;
    }


    /**
     * Felhasználót ad vissza.
     * @return
     */
    public Felhasznalo felhasznaloGetter(){
        return modell.getFelhasznalo();
    }

    public void ujablakmegynit(JFrame fr){
        fr.setVisible(true);
    }



    public KeyListener pontosVesszoTilt(){
        KeyListener klTilt= new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char kar = e.getKeyChar();
                if ( kar==';') {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

        };
        return klTilt;
    }


    public KeyListener gepelFiegyel(JTextField jtf, int index, String[] list){


    KeyListener klGepel = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            char kar = e.getKeyChar();
            if ( kar==';') {
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            list[index]=jtf.getText();
            }
    };
    return klGepel;
    }

    public ActionListener kepnevTombhoz(JRadioButton jrb, String[] tomb, int index, String filenev){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomb[index]=filenev;
            }
        };
        return al;
    }






    public ActionListener fileChooserMegynit(JFileChooser jfc){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfc.showDialog(null,"Válasszon egy filet");

            }
        };
        return al;
    }

    public ActionListener fileNevBeir(JFileChooser jfc,JTextField jtf){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    jtf.setText(jfc.getSelectedFile().getName());
            }
        };
        return al;
    }

    public PopupMenuListener fajlNevek(JTextField jtf,JTextField jtf2, JTextField jtf3,JTextField jtf4,JComboBox jcbValasz){
        PopupMenuListener ppls = new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                jcbValasz.removeAllItems();
                if (jtf.getText().length()!=0){
                    jcbValasz.addItem(jtf.getText());
                }
                if (jtf2.getText().length()!=0){
                    jcbValasz.addItem(jtf2.getText());
                }
                if (jtf3.getText().length()!=0){
                    jcbValasz.addItem(jtf3.getText());
                }
                if (jtf4.getText().length()!=0){
                    jcbValasz.addItem(jtf4.getText());
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        };
        return ppls;
    }

    /**
     * A program mappájába másolja a kiválasztott fájlt.
     * @param forras
     * @throws IOException
     */
    public void fileMasol(File forras) throws IOException {
        File cel = new File("./Images/"+forras.getName());
         Files.copy(forras.toPath(),cel.toPath());
    }

}
