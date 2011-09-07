package com.mb.richrail.gui;

import com.mb.richrail.parser.CommandParser;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

public class CommandController implements ActionListener {

    private CommandParser commandParser;
    
    public CommandController(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComponent) {
            JComponent button = (JComponent) e.getSource();
            Container parent = button.getParent();
            if (parent instanceof CommandPanel) {
                CommandPanel panel = (CommandPanel) parent;
                commandParser.parse(panel.getCommand());
            }
        }
    }

}
