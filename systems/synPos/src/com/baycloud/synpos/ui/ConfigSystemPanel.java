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
import javax.swing.plaf.FontUIResource;

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
public class ConfigSystemPanel extends JPanel {
    public static final int MAX_FONT_ADJUSTMENT = 10;
    public static final int MIN_FONT_ADJUSTMENT = -10;

    public ConfigSystemPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigSystemPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
        String myLook = Configuration.get("system.look");

        for (int i = 0; i < info.length; i++) {
            jComboBox1.addItem(info[i].getName());
            if (info[i].getClassName().equals(myLook)) {
                jComboBox1.setSelectedItem(info[i].getName());
            }
        }

        Locale[] locales = Locale.getAvailableLocales();

        String myLang = Configuration.get("locale.language");
        String myCountry = Configuration.get("locale.country");
        String myVar = Configuration.get("locale.variant");
        Locale myLocale = Locale.getDefault();

        if (myLang != null) {
            myLocale = new Locale(myLang, myCountry, myVar);
        }

        for (int i = 0; i < locales.length; i++) {
            jComboBox4.addItem(locales[i].getDisplayName());

            if (locales[i].equals(myLocale)) {
                jComboBox4.setSelectedItem(myLocale.getDisplayName());
            }
        }

        int fontDelta = 0;
        String fontStr = Configuration.get("system.font");

        if (fontStr != null) {
            try {
                fontDelta = Integer.parseInt(fontStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = MIN_FONT_ADJUSTMENT; i < (MAX_FONT_ADJUSTMENT + 1); i++) {
            jComboBox3.addItem(i > 0 ? "+" + i : "" + i);

            if (fontDelta == i) {
                jComboBox3.setSelectedItem(i > 0 ? "+" + i : "" + i);
            }
        }

        this.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel1.setLayout(borderLayout1);
        jPanel2.setLayout(gridLayout1);
        jPanel3.setLayout(gridLayout2);
        jLabel1.setText(I18N.getLabelString("Look & Feel")+": ");
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(5);
        gridLayout1.setVgap(5);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(5);
        gridLayout2.setVgap(5);

        jPanel5.setLayout(flowLayout6);
        jButton2.setMnemonic('S');
        jButton2.setText(I18N.getButtonString("Submit"));
        jButton2.addActionListener(new ConfigSystemPanel_jButton2_actionAdapter(this));
        jPanel6.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout6.setAlignment(FlowLayout.LEFT);
        jLabel4.setText(I18N.getLabelString("Station Name")+": ");
        jPanel4.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jLabel2.setText(I18N.getLabelString("Font Size")+": ");
        jLabel3.setText(I18N.getLabelString("Locale")+": ");
        jPanel7.setLayout(flowLayout3);
        jPanel8.setLayout(flowLayout4);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        this.add(jPanel1);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel1);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        Object[] objs = Station.getAllStations();
        String id = Configuration.get("station.id");

        for (int i = 0; i < objs.length; i++) {
            stations.put(((Station) objs[i]).getName(),
                         ((Station) objs[i]).getId() + "");
            jComboBox2.addItem(((Station) objs[i]).getName());

            if ((((Station) objs[i]).getId() + "").equals(id)) {
                jComboBox2.setSelectedIndex(i);
            }
        }

        jPanel6.add(jComboBox1, null);
        jPanel7.add(jComboBox3);
        jPanel8.add(jComboBox4);
        jPanel4.add(jComboBox2);
        jPanel3.add(jPanel4);
        jPanel3.add(jPanel6);
        jPanel3.add(jPanel7);
        jPanel3.add(jPanel8);
        jPanel3.add(jPanel5);
        jPanel5.add(jButton2, null);
        jPanel2.add(jLabel2);
        jPanel2.add(jLabel3);
    }

    JFrame parent;
    //Hashtable sysLooks = new Hashtable();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JComboBox jComboBox1 = new JComboBox();
    JPanel jPanel5 = new JPanel();
    JButton jButton2 = new JButton();
    JPanel jPanel6 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    JLabel jLabel4 = new JLabel();
    JPanel jPanel4 = new JPanel();
    JComboBox jComboBox2 = new JComboBox();
    FlowLayout flowLayout5 = new FlowLayout();
    Hashtable stations = new Hashtable();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JComboBox jComboBox3 = new JComboBox();
    JComboBox jComboBox4 = new JComboBox();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();

    public void jButton2_actionPerformed(ActionEvent event) {
        int selectedLocale = jComboBox4.getSelectedIndex();
        Locale[] locales = Locale.getAvailableLocales();
        Configuration.set("locale.language",
                          locales[selectedLocale].getLanguage());
        Configuration.set("locale.country", locales[selectedLocale].getCountry());
        Configuration.set("locale.variant", locales[selectedLocale].getVariant());
        Locale.setDefault(locales[selectedLocale]);

        int selectedFont = jComboBox3.getSelectedIndex();
        int newDelta = MIN_FONT_ADJUSTMENT + selectedFont;
        int oldDelta = 0;
        String fontStr = Configuration.get("system.font");

        if (fontStr != null) {
            try {
                oldDelta = Integer.parseInt(fontStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //int fontDelta = newDelta - oldDelta;

        if (newDelta != oldDelta) {
            Configuration.set("system.font", newDelta + "");
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            Iterator itor = defaults.entrySet().iterator();

            while (itor.hasNext()) {
                Map.Entry entry = (Map.Entry) itor.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();

                if (key.toUpperCase().indexOf( ".FONT" ) >= 0 && value instanceof Font) {
                    Font font = (Font) value;
                    float size = font.getSize() + newDelta;
                    font = new FontUIResource(font.deriveFont(size));
                    UIManager.put(key, font);
                }
            }
        }

        int selectedLook = jComboBox1.getSelectedIndex();
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

        try {
            Configuration.set("system.look", info[selectedLook].getClassName());
            UIManager.setLookAndFeel(info[selectedLook].getClassName());
            SwingUtilities.updateComponentTreeUI(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Configuration.set("station.id",
                          (String) stations.get(jComboBox2.getSelectedItem().
                                                toString()));
        JOptionPane.showMessageDialog(parent,
                                      I18N.getMessageString("Your system settings have been updated."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);

    }
}


class ConfigSystemPanel_jButton2_actionAdapter implements ActionListener {
    private ConfigSystemPanel adaptee;
    ConfigSystemPanel_jButton2_actionAdapter(ConfigSystemPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
