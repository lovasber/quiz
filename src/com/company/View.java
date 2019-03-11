package com.company;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{

    private boolean bejelentkezett;

    public View() {
        super("Quiz alkalmazás 1.0");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JTabbedPane jt = new JTabbedPane();

        jt.addTab("Quiz",quizFul());
        jt.addTab("Szótár",szotarFul());
        jt.addTab("Felhasználó",felhasznaloiFul());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        super.add(jt);
        setSize(new Dimension(500,600));
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
