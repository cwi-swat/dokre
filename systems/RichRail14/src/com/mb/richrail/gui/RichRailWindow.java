package com.mb.richrail.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RichRailWindow extends JFrame {
    
    public RichRailWindow(Observable trainService, Observable logger, ActionListener commandController) {
        setTitle("RichRail");
        setLayout(new BorderLayout(5,5));
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        Container contentPane = getContentPane();
        
        //Graphic display
        GraphicDisplayPanel graphicDisplayPanel = new GraphicDisplayPanel();
        trainService.addObserver(graphicDisplayPanel);
        JScrollPane graphicScrollPanel = new JScrollPane(graphicDisplayPanel);
        graphicScrollPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/2));
        contentPane.add(graphicScrollPanel, BorderLayout.NORTH);
        
        //Center layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 5, 0));
        
        //Text display
        TextDisplayPanel textDisplayPanel = new TextDisplayPanel();
        JScrollPane textScrollPanel = new JScrollPane(textDisplayPanel);
        trainService.addObserver(textDisplayPanel);
        centerPanel.add(textScrollPanel);
        
        //Log panel
        LogPanel logPanel = new LogPanel();
        JScrollPane logScrollPanel = new JScrollPane(logPanel);
        logger.addObserver(logPanel);
        centerPanel.add(logScrollPanel);
        
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
        CommandPanel commandPanel = new CommandPanel(commandController);
        contentPane.add(commandPanel, BorderLayout.PAGE_END);
        
        setVisible(true);
    }
}
