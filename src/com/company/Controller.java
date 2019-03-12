package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Modell modell;
    private View view;

    public Controller() {
        modell = new Modell();
        view = new View(this);
    }

    /**
     *
     * @param eltunoFrame ez az az ablak amelyiknek el fog tűnni
     * @param megjelenoFrame ez az az ablak amelyik megnyílik
     * @return
     */
 public ActionListener ujablakmegnyit(JFrame eltunoFrame,JFrame megjelenoFrame){
        ActionListener listenerUjAbNyit = e -> {
            eltunoFrame.setVisible(false);
            megjelenoFrame.setVisible(true);
        };
        return listenerUjAbNyit;
 }

    public ActionListener ujablakmegnyitM(JFrame eltunoFrame,JFrame megjelenoFrame){
        ActionListener listenerUjAbNyit = e -> {
            eltunoFrame.setVisible(false);
            megjelenoFrame.setVisible(true);
        };
        return listenerUjAbNyit;
    }



}
