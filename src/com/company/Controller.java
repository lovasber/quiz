package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Modell modell;
    private View view;

    public Controller() {
        modell = new Modell();
        view = new View();
    }


 public ActionListener ujablakmegnyit(JFrame eltunoFrame,JFrame megjelenoFrame){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eltunoFrame.setVisible(true);
                megjelenoFrame.setVisible(false);
            }
        };
 }



}
