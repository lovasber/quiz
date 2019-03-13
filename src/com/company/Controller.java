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


    public void bejelentkezes(){

    }



}
