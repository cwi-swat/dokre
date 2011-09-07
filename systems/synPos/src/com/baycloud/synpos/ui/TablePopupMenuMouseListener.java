package com.baycloud.synpos.ui;

import com.baycloud.synpos.util.*;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
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
public class TablePopupMenuMouseListener extends MouseAdapter {
    protected JPopupMenu popup = new JPopupMenu();
    protected JMenuItem copyItem, clearItem, selectAllItem;
    protected JTable table;

    public TablePopupMenuMouseListener() {
        Action action = new AbstractAction(I18N.getLabelString("Copy Selected")) {
            public void actionPerformed(ActionEvent ae) {
                StringBuffer sbf = new StringBuffer();
                // Check to ensure we have selected only a contiguous block of
                // cells
                int numcols = table.getSelectedColumnCount();
                int numrows = table.getSelectedRowCount();
                int[] rowsselected = table.getSelectedRows();
                int[] colsselected = table.getSelectedColumns();
                if (!((numrows - 1 ==
                       rowsselected[rowsselected.length - 1] - rowsselected[0] &&
                       numrows == rowsselected.length) &&
                      (numcols - 1 ==
                       colsselected[colsselected.length - 1] - colsselected[0] &&
                       numcols == colsselected.length))) {
                    JOptionPane.showMessageDialog(null,
                                                  I18N.getMessageString("Invalid copy selection."),
                                                  I18N.getLabelString("Error"),
                                                  JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < numrows; i++) {
                    for (int j = 0; j < numcols; j++) {
                        sbf.append(table.getValueAt(rowsselected[i],
                                colsselected[j]));
                        if (j < numcols - 1) {
                            sbf.append("\t");
                        }
                    }
                    sbf.append("\n");
                }
                StringSelection stsel = new StringSelection(sbf.toString());
                Clipboard system = Toolkit.getDefaultToolkit().
                                   getSystemClipboard();
                system.setContents(stsel, stsel);
            }
        };
        copyItem = popup.add(action);
        //copyItem.setMnemonic('c');

        action = new AbstractAction(I18N.getLabelString("Select All")) {
            public void actionPerformed(ActionEvent ae) {
                table.changeSelection(0, 0, false, false);
                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        table.changeSelection(i, j, false, true);
                    }
                }
                table.revalidate();
                table.repaint();
            }
        };
        selectAllItem = popup.add(action);
        //selectAllItem.setMnemonic('a');

        action = new AbstractAction(I18N.getLabelString("Clear Selection")) {
            public void actionPerformed(ActionEvent ae) {
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        };
        clearItem = popup.add(action);
        //clearItem.setMnemonic('c');
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
            if (!(e.getSource() instanceof JTable)) {
                return;
            }
            table = (JTable) e.getSource();

            boolean copyAvailable = (table.getSelectedRowCount() > 0 &&
                                     table.getRowCount() > 0);
            boolean selectAllAvailable = (table.getCellSelectionEnabled() &&
                                          table.getRowCount() > 0);
            copyItem.setEnabled(copyAvailable);
            clearItem.setEnabled(copyAvailable);
            selectAllItem.setEnabled(selectAllAvailable);
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
