package com.company;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class View extends JFrame{
    public Controller controller = null;
    private JTabbedPane jt;
    private JFrame regisztracioFrame;

    public JTabbedPane getJt() {
        return jt;
    }

    public View(Controller controller) {
        super("Quiz alkalmazás 1.0");
        if(this.controller==null && controller!=null)
            this.controller=controller;
        fooldalBeallit();
        bejelentkezes().setVisible(true);

    }

    /**
     *Bejelentkezés ablakot jeleníti meg.
     */
    private JFrame bejelentkezes() {
        JPanel pan;
        JLabel jlNev;
        JLabel jlJelszo;
        JTextField jtfNev;
        JPasswordField jpfJelszo;
        JButton jbBejel;
        JButton jbRegisztracio;


        JFrame bejelFrame = new JFrame();
        bejelFrame.setAlwaysOnTop(true);
        bejelFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bejelFrame.setTitle("Bejelentkezés");
        bejelFrame.setSize(300,200);
        bejelFrame.setLocationRelativeTo(null);
        bejelFrame.setResizable(false);
        pan = new JPanel();
        pan.setLayout(new GridLayout(3,2,10,10));
        pan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        jlNev = new JLabel("Felhasználó név:");
        jlJelszo = new JLabel("Jelszó:");

        jtfNev = new JTextField();
        jtfNev.setColumns(1);
        jpfJelszo = new JPasswordField(){
            @Override
            public void paste() {}
        };
        jlJelszo.setSize(new Dimension(50,20));

        jbBejel = new JButton("Bejelentkezés");
        jbBejel.addActionListener(e -> {
            if (jpfJelszo.getPassword().length != 0) {
            if (controller.letezikEfelhasznalo(jtfNev.getText())) {
                String jelszoS = new String(jpfJelszo.getPassword());
                if(controller.helyesJelszoE(jtfNev.getText(), jelszoS)){
                    //JOptionPane.showMessageDialog(bejelFrame, "Sikeres Bejelentkezés");
                    if (controller.aktiveE(jtfNev.getText())) {
                        try {
                            controller.bejelentkezesFolyamat(jtfNev.getText());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        bejelFrame.setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(bejelFrame,
                                "Inaktív Felhasználó nem jelentkezhet be!",
                                "Inaktív felhasználó",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(bejelFrame, "Hibás jelszó");
                }
            } else {
                JOptionPane.showMessageDialog(bejelFrame, "Nem létezik ilyen nevű felhasználó!");
                }
            }else {
                JOptionPane.showMessageDialog(bejelFrame, "Kérem adja meg a jelszavát!");
            }
        });
        jbRegisztracio = new JButton("Regisztráció");
        jbRegisztracio.addActionListener(e -> {
            bejelFrame.setVisible(false);
            regisztracio().setVisible(true);
        });

        pan.add(jlNev);
        pan.add(jtfNev);
        pan.add(jlJelszo);
        pan.add(jpfJelszo);
        pan.add(jbBejel);
        pan.add(jbRegisztracio);
        bejelFrame.add(pan);
        return bejelFrame;
    }


    /**
     *Regisztrációs ablak megjelenéséért felelós függvéy.
     */
    private JFrame regisztracio(){
        regisztracioFrame = new JFrame();

        regisztracioFrame.add(diakReg());
        return regisztracioFrame;
    }

    private JPanel diakReg(){
        JPanel regPan = new JPanel();

        regisztracioFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        regisztracioFrame.setSize(500,400);
        regisztracioFrame.setResizable(false);
        regisztracioFrame.setTitle("Regisztráció");
        regisztracioFrame.setLocationRelativeTo(null);
        regisztracioFrame.setAlwaysOnTop(true);
        regPan.setLayout(new GridLayout(6,2,10,10));
        regPan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        ButtonGroup bgRadio = new ButtonGroup();

        JRadioButton jrbDiak = new JRadioButton("Diák",true);
        JRadioButton jrbTanar = new JRadioButton("Tanár");
        bgRadio.add(jrbDiak);
        bgRadio.add(jrbTanar);

        regPan.add(jrbDiak);
        regPan.add(jrbTanar);
        jrbTanar.addActionListener(e -> {
            if (jrbTanar.isSelected()) {
                regisztracioFrame.getContentPane().removeAll();
                regisztracioFrame.getContentPane().add(tanarReg());
                regisztracioFrame.revalidate();
                regisztracioFrame.repaint();
            }
        });
        JLabel jlFnev = new JLabel("Felhasználónév");
        JLabel jlTeljesNev = new JLabel("Teljes név");
        JLabel jlJelszo = new JLabel("Jelszó");
        JLabel jlJelszomeg = new JLabel("Jelszó megerősítése");
        JTextField jtfFnev = new JTextField();
        JTextField jtfTnev = new JTextField();
        JPasswordField jpfJelszo = new JPasswordField(){
            @Override
            public void paste() {}
        };
        JPasswordField jpfMegerosit = new JPasswordField(){
            @Override
            public void paste() {}
        };
        JButton jbRegisztracio = new JButton("Regisztráció");
        jbRegisztracio.addActionListener(e -> {
            if(!(jtfFnev.getText().isEmpty()||jtfTnev.getText().isEmpty()||jpfJelszo.getPassword().length==0||jpfMegerosit.getPassword().length==0)) {
                //System.out.println(jpfJelszo.getPassword().toString());
                String jelszoSzoveg = new String(jpfJelszo.getPassword());
                String jelszoMeg = new String(jpfMegerosit.getPassword());
                if (controller.egyezoJelszo(jelszoSzoveg, jelszoMeg)) {
                    if (controller.szabadFelhasznaloNev(jtfFnev.getText())) {
                        controller.diakRegisztracio(jtfFnev.getText(), jtfTnev.getText(), controller.titkosit(jelszoSzoveg));
                        JOptionPane.showMessageDialog(regisztracioFrame, "Sikeres regisztráció!");
                    } else {
                        JOptionPane.showMessageDialog(regisztracioFrame, "Foglalt felhasználónév!");
                    }
                } else {
                    JOptionPane.showMessageDialog(regisztracioFrame, "Nem egyezik a két jelszó!");
                }
            }else {
                JOptionPane.showMessageDialog(regisztracioFrame, "Kérem töltse ki az összes mezőt!");
            }
        });
        JButton jbBejel = new JButton("Tovább a bejelentkezéshez");
        jbBejel.addActionListener(ee -> {
            regisztracioFrame.setVisible(false);
            bejelentkezes().setVisible(true);
        });

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
        return regPan;
    }

    /**
     * Tanári regisztráció bejelentkezési felületért felelős függvény.
     */
    private JPanel tanarReg(){
        JPanel jpTanreg = new JPanel();

        jpTanreg.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        ButtonGroup bgRadio = new ButtonGroup();
        JRadioButton jrbDiak = new JRadioButton("Diák");
        jrbDiak.addActionListener(e -> {
            if (jrbDiak.isSelected()){
                regisztracioFrame.getContentPane().removeAll();
                regisztracioFrame.getContentPane().add(diakReg());
                regisztracioFrame.revalidate();
            }
        });
        JRadioButton jrbTanar = new JRadioButton("Tanár",true);
        bgRadio.add(jrbDiak);
        bgRadio.add(jrbTanar);
        String tanariS ="<html>Tanári regsisztráció esetén írjon emailt az alábbi email címre: " +
                "<br>lovas.bertalan97@gmail.com . Az üzenet tárgya legyen a következő:" +
                "<br> \"Quiz tanári regisztráció\".</html>";
        JLabel jlTanregSzoveg = new JLabel(tanariS);
        JButton jbBejel = new JButton("Tovább a bejelentkezéshez");
        jbBejel.addActionListener(ee -> {
            regisztracioFrame.setVisible(false);
            bejelentkezes().setVisible(true);
        });

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        jpTanreg.add(jrbDiak,gbc);
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,5,10,5);
        jpTanreg.add(jrbTanar,gbc);
        gbc.gridx=0;
        gbc.gridy++;
        gbc.gridwidth=2;
        jpTanreg.add(jlTanregSzoveg,gbc);
        gbc.gridwidth=2;
        gbc.gridy++;
        jpTanreg.add(jbBejel,gbc);
        return jpTanreg;
    }


    private void fooldalBeallit() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000,700));
        jt = new JTabbedPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.add(jt);
        setVisible(true);
    }

    /**
     *
     * A felhasználói tab megjelenését biztosítja.
     */
    private JPanel felhasznaloiFul() {
        JPanel felh = new JPanel();
        return felh;
    }

    /**
     *
     * A szótár fül megjelenését biztosítja.
     */
    public JPanel szotarFul() {
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

        JButton kategoria1 = new JButton("Kategória 1");
        JButton kategoria2 = new JButton("Kategória 2");
        JButton kategoria3 = new JButton("Kategória 3");
        kategoria3.setEnabled(false);
        JButton kategoria4 = new JButton("Kategória 4");
        kategoria4.setEnabled(false);

        mainP.add(kategoria1);
        mainP.add(kategoria2);
        mainP.add(kategoria3);
        mainP.add(kategoria4);

        return mainP;
    }





}
