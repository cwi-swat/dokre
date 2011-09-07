package com.baycloud.synpos.ui;

import com.baycloud.synpos.util.*;

import javax.swing.*;

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
public class ConfigReceiptPanel extends JTabbedPane {
    public ConfigReceiptPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigReceiptPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.add(new ConfigReceiptOrderPanel(parent), I18N.getLabelString("Receipt"));
        this.add(new ConfigReceiptCCStorePanel(parent),
                 I18N.getLabelString("Credit Card Slip for Store"));
        this.add(new ConfigReceiptCCCustomerPanel(parent),
                 I18N.getLabelString("Credit Card Slip for Customer"));
    }

    JFrame parent;
}
