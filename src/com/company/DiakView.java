package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiakView extends JPanel {
    Controller cont;
    JPanel jpFooldal;

    public DiakView(Controller controller) {
        this.setLayout(new FlowLayout());
        cont = controller;
        jpFooldal = fooldalFeltolt();
        this.add(jpFooldal);
    }

    private JPanel fooldalFeltolt(){
        JPanel jpFooldal = new JPanel();
        ArrayList<JButton> gombokList = new ArrayList<>();

        for (int i = 0; i < cont.letezoFoKategoriak().size(); i++) {
            gombokList.add(new JButton(cont.letezoFoKategoriak().get(i)));
        }


        for (int i = 0; i < gombokList.size(); i++) {

            gombokList.get(i).addActionListener(e -> {
                JButton b = (JButton)e.getSource();
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

    private JPanel alkatBetolt(String fokat){
        ArrayList<String> alkatokL = cont.fokatAlkategoriai(fokat);
        JPanel jpanUj = new JPanel();
        jpanUj.setLayout(new FlowLayout());

        for (int i = 0; i < alkatokL.size(); i++) {
            jpanUj.add(new JButton(alkatokL.get(i)));
        }

        JButton jbVissza = new JButton("vissza");
        jbVissza.addActionListener(e -> {
            this.removeAll();
            this.add(fooldalFeltolt());
            this.repaint();
            this.revalidate();
        });
        jpanUj.add(jbVissza);

        return jpanUj;
    }

}
