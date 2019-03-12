package com.company;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{
    private Controller controller;

    public View() {
        super("Quiz alkalmazás 1.0");
        //controller = new Controller();
        fooldalBeallit();
        regisztracio().setVisible(true);

    }

    /**
     *Bejelentkezés ablakot jeleníti meg.
     */
    private JFrame bejelentkezes() {
        JPanel pan;
        JLabel nevL;
        JLabel jelszL;
        JTextField nevTf;
        JPasswordField jelszPf;
        JButton bejelB;
        JButton regisztB;

        JFrame bejelFrame = new JFrame();
        bejelFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bejelFrame.setTitle("Bejelentkezés");
        bejelFrame.setSize(300,200);
        bejelFrame.setLocationRelativeTo(this);
        bejelFrame.setResizable(false);
        pan = new JPanel();
        pan.setLayout(new GridLayout(3,2,10,10));
        pan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        nevL = new JLabel("Felhasználó név:");
        jelszL = new JLabel("Jelszó:");

        nevTf = new JTextField();
        nevTf.setColumns(1);
        jelszPf = new JPasswordField();
        jelszL.setSize(new Dimension(50,20));

        bejelB = new JButton("Bejelentkezés");
        regisztB = new JButton("Regisztráció");

        pan.add(nevL);
        pan.add(nevTf);
        pan.add(jelszL);
        pan.add(jelszPf);
        pan.add(bejelB);
        pan.add(regisztB);
        bejelFrame.add(pan);
        return bejelFrame;
    }

    /**
     *Regisztrációs ablak megjelenéséért felelós függvéy.
     */
    private JFrame regisztracio(){
        JFrame regisztracioFrame = new JFrame();
        JPanel regPan = new JPanel();

        ButtonGroup bgRadio = new ButtonGroup();
        JRadioButton jrbDiak = new JRadioButton("Diák",true);
        JRadioButton jrbTanar = new JRadioButton("Tanár");
        JLabel jlFnev = new JLabel("Felhasználónév");
        JLabel jlTeljesNev = new JLabel("Teljes név");
        JLabel jlJelszo = new JLabel("Jelszó");
        JLabel jlJelszomeg = new JLabel("Jelszó megerősítése");
        JTextField jtfFnev = new JTextField();
        JTextField jtfTnev = new JTextField();
        JPasswordField jpfJelszo = new JPasswordField();
        JPasswordField jpfMegerosit = new JPasswordField();
        JButton jbRegisztracio = new JButton("Regisztráció");
        JButton jbBejel = new JButton("Tovább a bejelentkezéshez");


        regisztracioFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        regisztracioFrame.setSize(500,400);
        regisztracioFrame.setResizable(false);
        regisztracioFrame.setTitle("Regisztráció");
        regisztracioFrame.setLocationRelativeTo(null);
        regPan.setLayout(new GridLayout(6,2,10,10));
        regPan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton bejelentkezes = new JButton("Tovább a bejelentkezéshez");

        bgRadio.add(jrbDiak);
        bgRadio.add(jrbTanar);
        regPan.add(jrbDiak);
        regPan.add(jrbTanar);
        regPan.add(jlFnev);
        regPan.add(jtfFnev);
        regPan.add(jlTeljesNev);
        regPan.add(jtfTnev);
        regPan.add(jlJelszo);
        regPan.add(jpfJelszo);
        regPan.add(jlJelszomeg);
        regPan.add(jpfMegerosit);
        regPan.add(jbRegisztracio);
        regPan.add(jbBejel);

        regisztracioFrame.add(regPan);
        return regisztracioFrame;
    }

    private void fooldalBeallit() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JTabbedPane jt = new JTabbedPane();
        jt.addTab("Quiz",quizFul());
        jt.addTab("Szótár",szotarFul());
        jt.addTab("Felhasználó",felhasznaloiFul());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.add(jt);
        //setSize(new Dimension(500,600));
        setVisible(true);
    }

    /**
     *
     * A felhasználói tab megjelenését biztosítja.
     */
    private JPanel felhasznaloiFul() {
        JPanel felh = new JPanel();
        felh.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        String fnevS = "Felhasnzáló név";
        JLabel fnevL = new JLabel(fnevS);
        String fszintS = "Felhasználó szintje: 1";
        JLabel fszintL = new JLabel(fszintS);
        JButton kijelentezB = new JButton("Kijelentkezés");
        JButton statisztikaB = new JButton("Statisztika");

        gbc.weighty = 1;

        felh.add(fszintL,gbc);
        felh.add(fnevL,gbc);
        felh.add(kijelentezB,gbc);
        felh.add(statisztikaB,gbc);
        return felh;
    }

    /**
     *
     * A szótár fül megjelenését biztosítja.
     */
    private JPanel szotarFul() {
        JPanel szotarPanel = new JPanel();

        szotarPanel.setLayout(new FlowLayout());
        JComboBox forditandoCb = new JComboBox();
        forditandoCb.addItem("Angol");
        forditandoCb.addItem("Magyar");

        JComboBox forditottCb = new JComboBox();
        forditottCb.addItem("Magyar");
        forditottCb.addItem("Angol");

        JTextField forditandoTf = new JTextField("",20);
        JTextField forditottTf = new JTextField("",20);

        szotarPanel.add(forditandoCb);
        szotarPanel.add(forditandoTf);

        szotarPanel.add(forditottCb);
        szotarPanel.add(forditottTf);

        return szotarPanel;
    }

    /**
     *
     * A Quiz fül megjelenését biztosítja.
     */
    private JPanel quizFul() {
        JPanel mainP = new JPanel();
        mainP.setLayout(new FlowLayout());

        //tipusActionListener tipusClick = new tipusActionListener();

        JButton kategoria1 = new JButton("Kategória 1");
//        kategoria1.addActionListener(tipusClick);
        JButton kategoria2 = new JButton("Kategória 2");
//        kategoria2.addActionListener(tipusClick);
        JButton kategoria3 = new JButton("Kategória 3");
        kategoria3.setEnabled(false);
//        kategoria3.addActionListener(tipusClick);
        JButton kategoria4 = new JButton("Kategória 4");
        kategoria4.setEnabled(false);
//        kategoria4.addActionListener(tipusClick);

        mainP.add(kategoria1);
        mainP.add(kategoria2);
        mainP.add(kategoria3);
        mainP.add(kategoria4);

        return mainP;
    }
}
