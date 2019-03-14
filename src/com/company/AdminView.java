package com.company;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JPanel {
    JButton jbUjKat;
    JButton jbUjKerdes;
    JButton jbKerdesSzerk;
    JButton jbDiakokEredmenyei;
    JButton jbFelhasznalok;

    public AdminView() {
        super.setLayout(new FlowLayout());
        jbUjKat = new JButton("Új kategória létrehozása");
        jbUjKerdes = new JButton("Új kérdés létrehozása");
        jbKerdesSzerk = new JButton("Kérdések szerkesztése");
        jbDiakokEredmenyei = new JButton("Diákok eredményei");
        jbFelhasznalok = new JButton("Felhasználók szerkesztése");

        super.add(jbUjKat);
        super.add(jbUjKerdes);
        super.add(jbKerdesSzerk);
        super.add(jbDiakokEredmenyei);
        super.add(jbFelhasznalok);
    }
}
