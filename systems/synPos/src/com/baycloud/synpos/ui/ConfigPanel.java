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
public class ConfigPanel extends JTabbedPane {
    private JFrame parent;

    public ConfigPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.add(new ConfigSystemPanel(parent), I18N.getLabelString("System"));
        this.add(new ConfigNetworkPanel(parent), I18N.getLabelString("Network"));
        this.add(new ConfigDatabasePanel(parent), I18N.getLabelString("Database"));
        this.add(new ConfigMSRPanel(parent), I18N.getLabelString("MSR"));
        this.add(new ConfigPrinterPanel(parent), I18N.getLabelString("Receipt Printer"));
        this.add(new ConfigCashDrawerPanel(parent), I18N.getLabelString("Cash Drawer"));
        this.add(new ConfigPaymentPanel(parent), I18N.getLabelString("Payment Gateway"));
        this.add(new ConfigSyncPanel(parent), I18N.getLabelString("Synchronization"));
        this.add(new ConfigReceiptPanel(parent), I18N.getLabelString("Receipt Design"));
    }
}
