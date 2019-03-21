package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiakView extends JPanel implements AdatbazisKapcsolat{
    private Controller cont;
    private JPanel jpFooldal;
    private String fokategoria="";
    private String alkategoria="";
    private int aktualisKerdesIndex=0;

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
        gbcLepeget.insets = new Insets(10,5,0,5);

        ArrayList<Kerdes> klist  = cont.alkatKerdesei(alkat);

        for (int i = 0; i < klist.size(); i++) {
            /*JPanel jpCard = new JPanel();
            jpCard.setLayout(cl);
            //jpCard.add(new JLabel(klist.get(i).getKerdesSzovege()));
            jpCard.add(new JTextField(20));
            */

            switch (klist.get(i).getTipus()){
                case 0:
                    jpanKerdesek.add(jpHianyos(klist.get(i)),(i+1)+"");
                    break;
                case 1:
                    jpanKerdesek.add(jpsokKep1JoValasz(klist.get(i)),(i+1)+"");
                    break;
                case 2:break;
                case 3:break;
            }


            //jpanKerdesek.add(jpCard,(i+1)+"");
        }

        for (int i = 0; i < klist.size(); i++) {
            JButton jbLepeget = new JButton((i+1)+"");
            jbLepeget.addActionListener(e -> {
                JButton but = (JButton)e.getSource();
                cl.show(jpanKerdesek,but.getText());
            });
            jpLepeget.add(jbLepeget,gbcLepeget);
            gbcLepeget.gridx++;
        }
        JButton vissza = new JButton("Vissza");
        vissza.addActionListener(e -> {

            this.removeAll();
            this.add(alkatBetolt(fokategoria));
            this.repaint();
            this.revalidate();



        });
        gbcLepeget.gridx=0;
        gbcLepeget.gridy++;
        jpLepeget.add(vissza,gbcLepeget);


        jpMain.add(jpanKerdesek,gbcMain);
        gbcMain.gridy++;
        jpMain.add(jpLepeget,gbcMain);
        return jpMain;
    }


    private JPanel jpHianyos(Kerdes kerdes){
        JPanel jphiany = new JPanel();
        jphiany.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel jlCim = new JLabel("Egészítse ki az alábbi mondatot");

        String[] kerdesT = kerdes.getKerdesSzovege().split("\\;");

        String kerdesResz1 = "<html>"+kerdesT[0]+"</html>";
        String valasz = kerdesT[1];
        String kerdesResz2 = "<html>"+kerdesT[2]+"</html>";

        JLabel jlKerd1 = new JLabel(kerdesResz1);
        JTextField jtfValasz = new JTextField(valasz.length());
        JLabel jlKerd2 = new JLabel(kerdesResz2);


        jphiany.add(jlCim,gbc);
        gbc.insets = new Insets(20,5,20,5);
        gbc.gridy++;
        gbc.gridx=0;
        jphiany.add(jlKerd1,gbc);
        gbc.gridx++;
        jphiany.add(jtfValasz,gbc);
        gbc.gridx++;
        jphiany.add(jlKerd2,gbc);

        return jphiany;
    }


    private JPanel jpsokKep1JoValasz(Kerdes kerdes) {
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        JPanel jpKepek = new JPanel();
        JPanel jpValasz = new JPanel();






        jpMain.add(jpKepek,gbcMain);
        gbcMain.gridx++;
        jpMain.add(jpValasz,gbcMain);
        return jpMain;
    }





}
