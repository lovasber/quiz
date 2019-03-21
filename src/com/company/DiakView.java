package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiakView extends JPanel {
    Controller cont;
    JPanel jpFooldal;
    String fokategoria="";
    String alkategoria="";

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
        fokategoria=fokat;
        ArrayList<String> alkatokL = cont.fokatAlkategoriai(fokat);
        JPanel jpanUj = new JPanel();
        jpanUj.setLayout(new FlowLayout());

        for (int i = 0; i < alkatokL.size(); i++) {
            JButton b = new JButton(alkatokL.get(i));
            b.addActionListener(e -> {
                JButton button = (JButton)e.getSource();
                alkategoria = button.getText();
                //System.out.println(alkategoria);
                this.removeAll();
                this.add(kerdesek(alkategoria));
                this.repaint();
                this.revalidate();
            });
            jpanUj.add(b);
        }

        JButton jbVissza = new JButton("vissza");
        jbVissza.addActionListener(e -> {
            JButton b = (JButton)e.getSource();
            alkategoria= b.getText();
            this.removeAll();
            this.add(fooldalFeltolt());
            this.repaint();
            this.revalidate();
        });
        jpanUj.add(jbVissza);

        return jpanUj;
    }

    private JPanel kerdesek(String alkat){
        JPanel jpanKerdesek = new JPanel();
        jpanKerdesek.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        ArrayList<Kerdes> klist  = cont.alkatKerdesei(alkat);
        for (int i = 0; i < klist.size(); i++) {
            jpanKerdesek.add(new JLabel(klist.get(i).getKerdesSzovege()),gbc);
            gbc.gridy++;
            System.out.println(klist.get(i).getKerdesSzovege());
        }
        JButton vissza = new JButton("Vissza");
        vissza.addActionListener(e -> {
            this.removeAll();
            this.add(alkatBetolt(fokategoria));
            this.repaint();
            this.revalidate();

        });
        jpanKerdesek.add(vissza);


        return jpanKerdesek;
    }



}
