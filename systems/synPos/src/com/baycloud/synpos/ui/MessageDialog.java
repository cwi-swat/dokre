package com.baycloud.synpos.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

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
public class MessageDialog extends JDialog {
    private String msg;
    private String title;
    private JFrame parent;
    public MessageDialog(JFrame parent, String title, String msg) {
        super(parent);
        this.title = title;
        this.msg = msg;
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jLabel1.setBorder(border1);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText(msg);
        setLayout(borderLayout1);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.
                                      DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        setTitle(title);
        getContentPane().add(jLabel1, java.awt.BorderLayout.CENTER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension pnlSize = getPreferredSize();
        setLocation((screenSize.width - pnlSize.width) / 2,
                    (screenSize.height - pnlSize.height) / 2);
    }

    JLabel jLabel1 = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    Border border1 = BorderFactory.createEmptyBorder(20, 20, 20, 20);
}
