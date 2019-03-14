package com.company;

import javax.swing.*;
import java.awt.*;

public class TanarView extends JPanel {
        JButton jbUjKat;
        JButton jbUjKerdes;
        JButton jbKerdesSzerk;
        JButton jbDiakokEredmenyei;

    public TanarView() {
            super.setLayout(new FlowLayout());
            jbUjKat = new JButton("Új kategória létrehozása");
            jbUjKerdes = new JButton("Új kérdés létrehozása");
            jbKerdesSzerk = new JButton("Kérdések szerkesztése");
            jbDiakokEredmenyei = new JButton("Diákok eredményei");

            super.add(jbUjKat);
            super.add(jbUjKerdes);
            super.add(jbKerdesSzerk);
            super.add(jbDiakokEredmenyei);
    }

}
