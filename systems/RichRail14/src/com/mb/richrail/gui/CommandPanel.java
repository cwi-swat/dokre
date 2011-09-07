package com.mb.richrail.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CommandPanel extends JPanel {

    private JButton button;
    private JTextField textField;
    
    CommandPanel(ActionListener commandController) {
        setLayout(new BorderLayout());
        button = new JButton("Execute");
        textField = new JTextField();
        
        button.setPreferredSize(new Dimension(100, 30));
        add(button, BorderLayout.EAST);
        add(textField, BorderLayout.CENTER);
        
        button.addActionListener(commandController);
        textField.addActionListener(commandController);
    }

    public String getCommand() {
        String text = textField.getText();
        textField.setText("");
        return text;
    }
}
