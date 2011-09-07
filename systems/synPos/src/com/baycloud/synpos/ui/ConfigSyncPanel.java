package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

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
public class ConfigSyncPanel extends JPanel {
    private JFrame parent;
    private StoreConfiguration config;

    private JTextField jTextField2 = new JTextField();
    private JTextField jTextField1 = new JTextField();
    private JPasswordField jPasswordField1 = new JPasswordField();
    private GridLayout gridLayout2 = new GridLayout();
    private GridLayout gridLayout1 = new GridLayout();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JButton jButton1 = new JButton();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel2 = new JPanel();

    JPanel jPanel5 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel6 = new JPanel();
    FlowLayout flowLayout4 = new FlowLayout();
    JPanel jPanel7 = new JPanel();
    FlowLayout flowLayout5 = new FlowLayout();
    JPanel jPanel8 = new JPanel();
    FlowLayout flowLayout6 = new FlowLayout();
    JLabel jLabel4 = new JLabel();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel9 = new JPanel();
    JPanel jPanel11 = new JPanel();
    JRadioButton jRadioButton1 = new JRadioButton();
    JRadioButton jRadioButton2 = new JRadioButton();
    JRadioButton jRadioButton3 = new JRadioButton();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout7 = new FlowLayout();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JPanel jPanel10 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    FlowLayout flowLayout8 = new FlowLayout();
    JButton jButton2 = new JButton();
    JCheckBox jCheckBox1 = new JCheckBox();
    FlowLayout flowLayout9 = new FlowLayout();

    public ConfigSyncPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigSyncPanel(JFrame parent) {
        this.parent = parent;
        config = new StoreConfiguration();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jTextField2.setText((String) config.get("sync_username"));
        jTextField2.setColumns(10);
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        jTextField1.setText((String) config.get("sync_url"));
        jTextField1.setColumns(30);

        jPasswordField1.setText((String) config.get("sync_password"));
        jPasswordField1.setColumns(10);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(8);
        gridLayout2.setVgap(5);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(8);
        gridLayout1.setVgap(5);
        jLabel3.setText(I18N.getLabelString("Password")+": ");
        jLabel2.setText(I18N.getLabelString("Username")+": ");
        jLabel1.setText(I18N.getLabelString("Adaptor URL")+": ");
        jPanel3.setLayout(gridLayout2);
        jPanel2.setLayout(gridLayout1);
        jPanel5.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel6.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jPanel7.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jPanel8.setLayout(flowLayout6);
        flowLayout6.setAlignment(FlowLayout.LEFT);
        flowLayout9.setAlignment(FlowLayout.LEFT);
        jPanel11.setLayout(flowLayout9);
        jCheckBox1.setText(I18N.getLabelString("Debug"));
        String debug = config.get("sync_debug");

        if(debug != null && debug.equals("true")) {
            jCheckBox1.setSelected(true);
        }
        jPanel11.add(jCheckBox1);
        jButton1.setMnemonic('S');
        jButton1.setText(I18N.getButtonString("Submit"));
        jButton1.addActionListener(new ConfigSyncPanel_jButton1_actionAdapter(this));
        this.setLayout(flowLayout8);
        jLabel4.setText(I18N.getLabelString("Mode")+": ");
        int mode = Synchronizer.getMode();

        jRadioButton1.setMnemonic('R');
        jRadioButton1.setText(I18N.getLabelString("Real Time"));

        if (mode == Synchronizer.REAL_SYNC) {
            jRadioButton1.setSelected(true);
        }

        jRadioButton2.setMnemonic('M');
        jRadioButton2.setText(I18N.getLabelString("Manual"));

        if (mode == Synchronizer.MANUAL_SYNC) {
            jRadioButton2.setSelected(true);
        }

        jRadioButton3.setMnemonic('N');
        jRadioButton3.setText(I18N.getLabelString("Never"));

        if (mode == Synchronizer.NEVER_SYNC) {
            jRadioButton3.setSelected(true);
        }

        jPanel1.setLayout(flowLayout2);
        jPanel4.setLayout(flowLayout3);
        jPanel9.setLayout(flowLayout7);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout7.setAlignment(FlowLayout.LEFT);
        jLabel5.setText("");
        jPanel10.setLayout(borderLayout2);
        flowLayout8.setAlignment(FlowLayout.LEFT);
        jButton2.setMnemonic('R');
        jButton2.setText(I18N.getButtonString("Run Synchronizer"));
        jButton2.addActionListener(new ConfigSyncPanel_jButton2_actionAdapter(this));
        jPanel3.add(jPanel5);
        jPanel5.add(jTextField1);
        jPanel3.add(jPanel6);
        jPanel6.add(jTextField2);
        jPanel3.add(jPanel7);
        jPanel7.add(jPasswordField1, null);
        jPanel3.add(jPanel1);
        jPanel1.add(jRadioButton1);
        jPanel3.add(jPanel4);
        jPanel4.add(jRadioButton2);
        jPanel3.add(jPanel9);
        jPanel9.add(jRadioButton3);
        jPanel3.add(jPanel11);
        jPanel3.add(jPanel8);
        jPanel8.add(jButton1);
        jPanel8.add(jButton2);
        jPanel2.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel2.add(jLabel3);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel5);
        jPanel2.add(jLabel6);
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        buttonGroup1.add(jRadioButton3);
        this.add(jPanel10, null);
        jPanel10.add(jPanel3, java.awt.BorderLayout.CENTER);
        jPanel10.add(jPanel2, java.awt.BorderLayout.WEST);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        String sync_url = jTextField1.getText().trim();
        int mode = Synchronizer.NEVER_SYNC;

        if (jRadioButton1.isSelected()) {
            mode = Synchronizer.REAL_SYNC;
        } else if (jRadioButton2.isSelected()) {
            mode = Synchronizer.MANUAL_SYNC;
        } else if (jRadioButton3.isSelected()) {
            mode = Synchronizer.NEVER_SYNC;
        }

        String sync_username = jTextField2.getText().trim();

        if (sync_url.equals("") && mode != Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Please enter your adaptors URL."),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
            return;

        }

        if (sync_username.equals("") && mode != Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Please enter your username."),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
            return;

        }

        if (jPasswordField1.getDocument() == null &&
            mode != Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent, I18N.getMessageString("Please enter your password."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;

        }

        boolean debug = jCheckBox1.isSelected();

        config.set("sync_url", sync_url);
        config.set("sync_username", sync_username);
        config.set("sync_password",
                   new String(jPasswordField1.getPassword()).trim());
        config.set("sync_frequency", mode + "");
        config.set("sync_debug", debug ? "true" : "false");
        Synchronizer.refreshConfig();
        JOptionPane.showMessageDialog(parent,
                                      I18N.getMessageString("Your sync parameters have been updated."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        if (Synchronizer.getMode() != Synchronizer.MANUAL_SYNC) {
            int option = JOptionPane.showConfirmDialog(parent,
                    I18N.getMessageString("It is recommended to run synchronizer in manual mode only. Do you want to continue?"),
                    I18N.getLabelString("Confirm"),
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.NO_OPTION) {
                return;
            }
        }

        SyncDlg dlg = SyncDlg.getInstance();

        if(!dlg.isVisible()) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dlgSize = dlg.getPreferredSize();
            dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                            (screenSize.height - dlgSize.height) / 2);
            dlg.pack();
            dlg.setVisible(true);
        } else {
            dlg.toFront();
        }
    }
}


class ConfigSyncPanel_jButton2_actionAdapter implements ActionListener {
    private ConfigSyncPanel adaptee;
    ConfigSyncPanel_jButton2_actionAdapter(ConfigSyncPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class ConfigSyncPanel_jButton1_actionAdapter implements ActionListener {
    private ConfigSyncPanel adaptee;
    ConfigSyncPanel_jButton1_actionAdapter(ConfigSyncPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
