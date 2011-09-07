package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

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
 * @version 0.9.2
 */
public class StationsPanel extends JPanel {
    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable(new StationsTableModel());
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JTextField jTextField1 = new JTextField();
    private JButton jButton1 = new JButton();
    private JComboBox jComboBox2 = new JComboBox();
    JPanel jPanel2 = new JPanel();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    FlowLayout flowLayout2 = new FlowLayout();
    public StationsPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StationsPanel(JFrame parent) {
        this.parent = parent;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setBorder(null);
        jPanel1.setLayout(flowLayout1);
        jLabel1.setText(I18N.getLabelString("Station Name")+": ");
        jButton1.setMnemonic('N');
        jButton1.setText(I18N.getButtonString("New"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        jTextField1.setColumns(15);
        jTextField1.addMouseListener(new PopupMenuMouseListener());

        jButton2.setMnemonic('E');
        jButton2.setText(I18N.getButtonString("Edit"));
        jButton2.addActionListener(new StationsPanel_jButton2_actionAdapter(this));
        jButton3.setMnemonic('F');
        jButton3.setText(I18N.getButtonString("Find"));
        jButton3.addActionListener(new StationsPanel_jButton3_actionAdapter(this));
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jLabel1, null);
        jPanel1.add(jTextField1, null);
        jPanel1.add(jButton3);
        jScrollPane1.getViewport().add(jTable1);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        this.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton2);
        jPanel2.add(jButton1);
        //jTable1.addMouseListener(new TablePopupMenuMouseListener());
        jTable1.setCellSelectionEnabled(true);
        TableExcelAdapter myAd1 = new TableExcelAdapter(jTable1);
        jTable1.addMouseListener(new TablePopupMenuMouseListener());
    }

    void jButton1_actionPerformed(ActionEvent e) {
        StationDlg dlg = new StationDlg(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);

        String[] station = dlg.getStation();

        if (station != null) {
            StationsTableModel model = (StationsTableModel) jTable1.getModel();
            String msg = model.addStation(station[0]);

            if (msg != null) {
                JOptionPane.showMessageDialog(parent, msg,
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
                dlg.setVisible(true);
            } else {
                jTable1.revalidate();
                jTable1.repaint();
            }
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            StationsTableModel model1 = (StationsTableModel) jTable1.getModel();
            int stationId = ((Integer) model1.getValueAt(selectedRow, 0)).
                         intValue();
            StationDlg dlg = new StationDlg(new Station(stationId));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dlgSize = dlg.getPreferredSize();
            dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                            (screenSize.height - dlgSize.height) / 2);
            dlg.pack();
            dlg.setVisible(true);

            String[] station = dlg.getStation();

            if (station != null) {
                model1.setValueAt(station[0], selectedRow, 1);
                jTable1.revalidate();
                jTable1.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Please select a station to edit."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        String stationName = jTextField1.getText().trim();
        StationsTableModel model1 = (StationsTableModel) jTable1.getModel();

        int row = model1.findStation(stationName);
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Station not found:") + stationName,
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

        } else {
            jTable1.changeSelection(row, 1, false, false);
            //int orderId = ( (Integer) model1.getValueAt(row, 1)).intValue();
            jTable1.revalidate();
            jTable1.repaint();
        }
    }
}

class StationsPanel_jButton3_actionAdapter implements ActionListener {
    private StationsPanel adaptee;
    StationsPanel_jButton3_actionAdapter(StationsPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}


class StationsPanel_jButton2_actionAdapter implements ActionListener {
    private StationsPanel adaptee;
    StationsPanel_jButton2_actionAdapter(StationsPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
