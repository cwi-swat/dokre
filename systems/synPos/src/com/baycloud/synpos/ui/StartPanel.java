package com.baycloud.synpos.ui;

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
public class StartPanel extends JFrame {
    public StartPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setUndecorated(true);
        //java.net.URL imgURL = StartPanel.class.getResource("images/synpos.jpg");
        //if(imgURL != null) {
        JLabel jLabel1 = new JLabel(new ImageIcon("images/splash.jpg"));
        this.getContentPane().add(jLabel1);
        //}
    }
}
