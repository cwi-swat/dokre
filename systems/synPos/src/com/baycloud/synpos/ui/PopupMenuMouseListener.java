package com.baycloud.synpos.ui;

import com.baycloud.synpos.util.*;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * <p>Title: synPOS</p>
 *
 * <p>Description: synPOS is a desktop POS (Point Of Sale) client for online
 * ERP, eCommerce, and CRM systems. Released under the GNU General Public
 * License. Absolutely no warranty. Use at your own risk.</p>
 *
 * <p>Copyright: Copyright (c) 2006 synPOS.com</p>
 *
 * <p>Website: www.synpos.com</p>
 *
 * @author H.Q.
 * @version 0.9.1
 */
public class PopupMenuMouseListener extends MouseAdapter {
    protected JPopupMenu popup = new JPopupMenu();
    protected JMenuItem undoItem, cutItem, copyItem, pasteItem, deleteItem,
            selectAllItem;
    protected JTextComponent textComponent;
    protected String savedstring = "";
    protected String lastactionselected = "";

    public PopupMenuMouseListener() {
        Action action = new AbstractAction(I18N.getButtonString("Undo")) {
            public void actionPerformed(ActionEvent ae) {
                if (lastactionselected.compareTo("") != 0) {
                    textComponent.setText("");
                    textComponent.replaceSelection(savedstring);
                }
            }
        };
        undoItem = popup.add(action);
        undoItem.setMnemonic('u');
        popup.addSeparator();

        action = new AbstractAction(I18N.getButtonString("Cut")) {
            public void actionPerformed(ActionEvent ae) {
                lastactionselected = "c";
                savedstring = textComponent.getText();
                textComponent.cut();
            }
        };
        cutItem = popup.add(action);
        cutItem.setMnemonic('t');

        action = new AbstractAction(I18N.getButtonString("Copy")) {
            public void actionPerformed(ActionEvent ae) {
                lastactionselected = "";
                textComponent.copy();
            }
        };
        copyItem = popup.add(action);
        copyItem.setMnemonic('c');

        action = new AbstractAction(I18N.getButtonString("Paste")) {
            public void actionPerformed(ActionEvent ae) {
                lastactionselected = "p";
                savedstring = textComponent.getText();
                //System.out.println("in paste code savedstring is: " + savedstring);
                textComponent.paste();
            }
        };
        pasteItem = popup.add(action);
        pasteItem.setMnemonic('p');
        action = new AbstractAction(I18N.getButtonString("Delete")) {
            public void actionPerformed(ActionEvent ae) {
                lastactionselected = "d";
                savedstring = textComponent.getText();
                textComponent.replaceSelection("");
            }
        };
        deleteItem = popup.add(action);
        deleteItem.setMnemonic('d');
        popup.addSeparator();

        action = new AbstractAction(I18N.getButtonString("Select All")) {
            public void actionPerformed(ActionEvent ae) {
                lastactionselected = "s";
                savedstring = textComponent.getText();
                textComponent.selectAll();
            }
        };
        selectAllItem = popup.add(action);
        selectAllItem.setMnemonic('a');
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
            if (!(e.getSource() instanceof JTextComponent)) {
                return;
            }
            textComponent = (JTextComponent) e.getSource();
            // 1.3 version
            //textComponent.requestFocus();
            // 1.4 preferred method
            textComponent.requestFocus();
            //textComponent.requestDefaultFocus();
            //textComponent.requestFocusInWindow();

            boolean enabled = textComponent.isEnabled();
            boolean editable = textComponent.isEditable();
            boolean nonempty = !(textComponent.getText() == null ||
                                 textComponent.getText().equals(""));
            boolean marked = textComponent.getSelectedText() != null;
            boolean pasteAvailable = Toolkit.getDefaultToolkit().
                                     getSystemClipboard().
                                     getContents(null)
                                     .isDataFlavorSupported(DataFlavor.
                    stringFlavor);

            undoItem.setEnabled(enabled && editable);
            cutItem.setEnabled(enabled && editable && marked);
            copyItem.setEnabled(enabled && marked);
            pasteItem.setEnabled(enabled && editable && pasteAvailable);
            deleteItem.setEnabled(enabled && editable && marked);
            selectAllItem.setEnabled(enabled && nonempty);
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
