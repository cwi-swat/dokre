package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
public class ConfigNetworkPanel extends JPanel {
    public ConfigNetworkPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigNetworkPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        String proxyHost = Configuration.get("network.https_proxy_host");
        String proxyPort = Configuration.get("network.https_proxy_port");

        this.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel1.setLayout(borderLayout1);
        jPanel2.setLayout(gridLayout1);
        jPanel3.setLayout(gridLayout2);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(3);
        gridLayout1.setVgap(5);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(3);
        gridLayout2.setVgap(5);

        jPanel5.setLayout(flowLayout6);
        jButton2.setMnemonic('S');
        jButton2.setText(I18N.getButtonString("Submit"));
        jButton2.addActionListener(new ConfigNetworkPanel_jButton2_actionAdapter(this));
        jLabel2.setText(I18N.getLabelString("HTTPS Proxy Port")+": ");
        jLabel3.setText(I18N.getLabelString("HTTPS Proxy Host")+": ");
        jPanel7.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jTextField3.setColumns(20);
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jPanel8.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jTextField2.setColumns(20);
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        flowLayout6.setAlignment(FlowLayout.LEFT);
        this.add(jPanel1);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel2.add(jLabel3);
        jPanel2.add(jLabel2);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        if (proxyPort != null) {
            jTextField2.setText(proxyPort);
        }
        if (proxyHost != null) {
            jTextField3.setText(proxyHost);
        }
        jPanel3.add(jPanel8);
        jPanel8.add(jTextField3);
        jPanel3.add(jPanel7);
        jPanel7.add(jTextField2);
        jPanel3.add(jPanel5);
        jPanel5.add(jButton2, null);
    }

    JFrame parent;
    Hashtable sysLooks = new Hashtable();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel5 = new JPanel();
    JButton jButton2 = new JButton();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JTextField jTextField2 = new JTextField();
    JTextField jTextField3 = new JTextField();
    JPanel jPanel7 = new JPanel();
    FlowLayout flowLayout3 = new FlowLayout();
    JPanel jPanel8 = new JPanel();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    Hashtable stations = new Hashtable();

    public void jButton2_actionPerformed(ActionEvent event) {
        String host = jTextField3.getText().trim();

        if (host.equals("")) {
            Configuration.delete("network.https_proxy_host");
        } else {
            Configuration.set("network.https_proxy_host", host);
        }

        String port = jTextField2.getText().trim();
        if (port.equals("")) {
            Configuration.delete("network.https_proxy_port");
        } else {
            Configuration.set("network.https_proxy_port", port);
        }

        JOptionPane.showMessageDialog(parent,
                                      I18N.getMessageString("Your network settings have been updated."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);

    }
}


class ConfigNetworkPanel_jButton2_actionAdapter implements ActionListener {
    private ConfigNetworkPanel adaptee;
    ConfigNetworkPanel_jButton2_actionAdapter(ConfigNetworkPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
