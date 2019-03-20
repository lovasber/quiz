package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiakView extends JPanel {
    Controller cont;
    public DiakView(Controller controller) {
        this.setLayout(new FlowLayout());
        cont = controller;
        fooldalFeltolt();
    }

    private void fooldalFeltolt(){
        ArrayList<JButton> gombokList = new ArrayList<>();

        for (int i = 0; i < cont.letezoFoKategoriak().size(); i++) {
            gombokList.add(new JButton(cont.letezoFoKategoriak().get(i)));
        }
        for (int i = 0; i < gombokList.size(); i++) {
            gombokList.get(i).addActionListener(e -> {

            });
        }

        for (int i = 0; i < gombokList.size(); i++) {
            this.add(gombokList.get(i));
        }
    }

    private JPanel alkatBetolt(){
        JPanel jpAlkat = new JPanel();
        JTabbedPane jtab = cont.getView().getJt();
        jpAlkat=(JPanel)jtab.getTabComponentAt(0);

        return jpAlkat;
    }

}
