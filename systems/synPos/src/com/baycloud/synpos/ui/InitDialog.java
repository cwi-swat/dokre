package com.baycloud.synpos.ui;

import com.baycloud.synpos.util.*;

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
public class InitDialog extends JDialog {
    public InitDialog() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText(I18N.getMessageString("Initializing ..."));
        setLayout(borderLayout1);
        this.setDefaultCloseOperation(3);
        setResizable(false);
        setTitle("synPOS");
        add(jLabel1, java.awt.BorderLayout.CENTER);
    }

    JLabel jLabel1 = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();

    public static void main(String[] args) {
        InitDialog init = new InitDialog();
        init.pack();
        init.setVisible(true);
    }
}
