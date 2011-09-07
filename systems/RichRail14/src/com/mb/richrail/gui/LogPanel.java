package com.mb.richrail.gui;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;

public class LogPanel extends JTextArea implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        this.append(arg + "\n");
    }
}
