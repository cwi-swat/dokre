package com.baycloud.synpos;

import com.baycloud.synpos.ui.*;
import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.plaf.*;
import jpos.util.JposPropertiesConst;


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
public class synPOS {
    public final static int MAIN_VERSION = 1;
    public final static int MINOR_VERSION = 0;
    public final static int MAINTAIN_VERSION = 0;
    public final static String VERSION = MAIN_VERSION + "." + MINOR_VERSION +
                                         "." + MAINTAIN_VERSION;

    //Main method
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        StartPanel startPanel = new StartPanel();
        Dimension pnlSize = startPanel.getPreferredSize();
        startPanel.setLocation((screenSize.width - pnlSize.width) / 2,
                               (screenSize.height - pnlSize.height) / 2);
        startPanel.pack();
        startPanel.setVisible(true);

        String verStart = "1.0.0";
        //First lets check if this is a new install or upgrade.
        //For fresh install, we need to create tables
        String version = Configuration.get("synpos.version");

        try {
            if (version == null) { // new install
                int option = JOptionPane.showConfirmDialog(startPanel,
                        "synPOS is starting to create a new database for this station. Would you like to continue?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    try {
                        MyDB.update("CREATE CACHED TABLE configuration (configuration_id integer identity, configuration_key varchar(255) NOT NULL, configuration_value longvarchar NOT NULL, primary key (configuration_id))");
                        MyDB.update(
                                "INSERT INTO configuration values(NULL, 'database.driver', 'org.hsqldb.jdbcDriver')");
                        MyDB.update(
                                "INSERT INTO configuration values(NULL, 'database.url', 'jdbc:hsqldb:file:db/store')");
                        MyDB.update(
                                "INSERT INTO configuration values(NULL, 'database.username', 'sa')");
                        MyDB.update(
                                "INSERT INTO configuration values(NULL, 'database.password', '')");
                        MyDB.update(
                                "INSERT INTO configuration values(NULL, 'synpos.version', '" +
                                verStart + "')");
                        MyDB.update(
                                "INSERT INTO configuration values(NULL, 'station.id', '1')");
                        version = verStart;
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(startPanel, e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                } else {
                    System.exit(0);
                }
            }

            if (synVersion.compare(version, VERSION) > 0) {
                JOptionPane.showMessageDialog(startPanel, "Your database version is unknown. Please upgrade your synPOS to the latest version.",
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(startPanel, e.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        //First lets check if this is a new install or upgrade.
        //For fresh install, we need to create tables
        version = StoreConfiguration.get("synpos.version");
        String driver = Configuration.get("database.driver");

        if (driver != null && !driver.equals("org.hsqldb.jdbcDriver") &&
            !driver.equals("com.mysql.jdbc.Driver") && version != VERSION) {
            JOptionPane.showMessageDialog(startPanel,
                                          "Please upgrade your store database to the latest version.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            if (version == null) { // new install
                int option = JOptionPane.showConfirmDialog(startPanel,
                        "synPOS is starting to create a new database for your store. Would you like to continue?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    try {
                        if (driver.equals("org.hsqldb.jdbcDriver")) {
                            StoreDB.update("CREATE CACHED TABLE configuration (configuration_id integer identity, configuration_key varchar(255) NOT NULL, configuration_value longvarchar NOT NULL, primary key (configuration_id))");
                            StoreDB.update("CREATE CACHED TABLE categories (categories_id integer identity, categories_code varchar(64), parent_id integer NOT NULL, categories_name varchar(32) NOT NULL, date_created timestamp, last_modified timestamp, primary key (categories_id))");
                            StoreDB.update("CREATE CACHED TABLE products (products_id integer identity, products_code varchar(64), products_category integer NOT NULL, products_barcode varchar(32) default NULL, products_description longvarchar, products_quantity FLOAT NOT NULL, products_price decimal(14,2) NOT NULL, products_tax decimal(14,2) NOT NULL, date_created timestamp, last_modified timestamp, primary key (products_id), foreign key (products_category) references categories (categories_id))");
                            StoreDB.update("CREATE CACHED TABLE users (users_id integer identity,users_firstname varchar(32) NOT NULL,users_lastname varchar(32) NOT NULL,users_username varchar(40) NOT NULL,users_password varchar(40) NOT NULL,users_authorization integer NOT NULL, primary key (users_id))");
                            StoreDB.update("CREATE CACHED TABLE customers (customers_id integer identity, customers_code varchar(64), firstname varchar(32) NOT NULL, lastname varchar(32) NOT NULL, title varchar(32), birthdate varchar(32), address1 varchar(128) NOT NULL, address2 varchar(128) NOT NULL, city varchar(64) NOT NULL, state varchar(64) NOT NULL, zip varchar(64) NOT NULL, phone varchar(64) NOT NULL, fax varchar(64), email varchar(64) NOT NULL, date_created timestamp, last_modified timestamp, synchronized boolean, primary key (customers_id))");
                            StoreDB.update("CREATE CACHED TABLE stations (stations_id integer identity, stations_name varchar(64) NOT NULL, primary key (stations_id))");
                            StoreDB.update("CREATE CACHED TABLE orders (orders_id integer identity, orders_code varchar(64), payment_method varchar(128) NOT NULL, payment_id integer NOT NULL,users_id integer NOT NULL, stations_id integer NOT NULL, date_purchased timestamp,orders_total decimal(14,2),orders_tax decimal(14,2), orders_status boolean, synchronized boolean, customers_id integer, primary key (orders_id), foreign key (customers_id) references customers (customers_id), foreign key (users_id) references users (users_id), foreign key (stations_id) references stations (stations_id))");
                            StoreDB.update("CREATE CACHED TABLE orders_products (orders_products_id integer identity,orders_id integer NOT NULL, products_code varchar(64), products_barcode varchar(32), products_description longvarchar,orders_quantity INTEGER NOT NULL,orders_price decimal(14,2) NOT NULL, orders_tax decimal(14,2) NOT NULL, primary key (orders_products_id), foreign key (orders_id) references orders (orders_id))");
                            StoreDB.update("CREATE CACHED TABLE payments_cash (payments_cash_id integer identity, orders_id integer, payments_cash_paid decimal(14,2), payments_cash_change decimal(14,2), payments_cash_status boolean, primary key (payments_cash_id), foreign key (orders_id) references orders (orders_id))");
                            StoreDB.update("CREATE CACHED TABLE payments_check (payments_check_id integer identity, orders_id integer, payments_check_name varchar(64), payments_check_street1 varchar(128), payments_check_street2 varchar(128), payments_check_city varchar(64),payments_check_zip varchar(64),payments_check_state varchar(64),payments_check_country varchar(64),payments_check_phone varchar(64),payments_check_routing varchar(64),payments_check_account varchar(64),payments_check_number varchar(64), payments_check_status boolean, primary key (payments_check_id), foreign key (orders_id) references orders (orders_id))");
                            StoreDB.update("CREATE CACHED TABLE payments_credit (payments_credit_id integer identity, orders_id integer, payments_credit_name varchar(64), payments_credit_number varchar(128), payments_credit_expire_month varchar(64), payments_credit_expire_year varchar(64),payments_credit_auth varchar(64), payments_credit_transid varchar(64), payments_credit_status boolean, primary key (payments_credit_id), foreign key (orders_id) references orders (orders_id))");
                        } else { //mysql
                            StoreDB.update("CREATE TABLE configuration (configuration_id int NOT NULL auto_increment, configuration_key varchar(255) NOT NULL, configuration_value text NOT NULL, primary key (configuration_id))");
                            StoreDB.update("CREATE TABLE categories (categories_id int NOT NULL auto_increment, categories_code varchar(64), parent_id int NOT NULL, categories_name varchar(32) NOT NULL, date_created timestamp, last_modified timestamp, primary key (categories_id))");
                            StoreDB.update("CREATE TABLE products (products_id int NOT NULL auto_increment, products_code varchar(64), products_category int NOT NULL, products_barcode varchar(32) default NULL, products_description text, products_quantity FLOAT NOT NULL, products_price decimal(14,2) NOT NULL, products_tax decimal(14,2) NOT NULL, date_created timestamp, last_modified timestamp, primary key (products_id), foreign key (products_category) references categories (categories_id))");
                            StoreDB.update("CREATE TABLE users (users_id int NOT NULL auto_increment,users_firstname varchar(32) NOT NULL,users_lastname varchar(32) NOT NULL,users_username varchar(40) NOT NULL,users_password varchar(40) NOT NULL,users_authorization int NOT NULL, primary key (users_id))");
                            StoreDB.update("CREATE TABLE customers (customers_id int NOT NULL auto_increment, customers_code varchar(64), firstname varchar(32) NOT NULL, lastname varchar(32) NOT NULL, title varchar(32), birthdate varchar(32), address1 varchar(128) NOT NULL, address2 varchar(128) NOT NULL, city varchar(64) NOT NULL, state varchar(64) NOT NULL, zip varchar(64) NOT NULL, phone varchar(64) NOT NULL, fax varchar(64), email varchar(64) NOT NULL, date_created timestamp, last_modified timestamp, synchronized boolean, primary key (customers_id))");
                            StoreDB.update("CREATE TABLE stations (stations_id int NOT NULL auto_increment, stations_name varchar(64) NOT NULL, primary key (stations_id))");
                            StoreDB.update("CREATE TABLE orders (orders_id int NOT NULL auto_increment, orders_code varchar(64), payment_method varchar(128) NOT NULL, payment_id int NOT NULL,users_id int NOT NULL, stations_id int NOT NULL, date_purchased timestamp,orders_total decimal(14,2),orders_tax decimal(14,2), orders_status boolean, synchronized boolean, customers_id int, primary key (orders_id), foreign key (customers_id) references customers (customers_id), foreign key (users_id) references users (users_id), foreign key (stations_id) references stations (stations_id))");
                            StoreDB.update("CREATE TABLE orders_products (orders_products_id int NOT NULL auto_increment,orders_id int NOT NULL, products_code varchar(64), products_barcode varchar(32), products_description text,orders_quantity int NOT NULL,orders_price decimal(14,2) NOT NULL, orders_tax decimal(14,2) NOT NULL, primary key (orders_products_id), foreign key (orders_id) references orders (orders_id))");
                            StoreDB.update("CREATE TABLE payments_cash (payments_cash_id int NOT NULL auto_increment, orders_id int, payments_cash_paid decimal(14,2), payments_cash_change decimal(14,2), payments_cash_status boolean, primary key (payments_cash_id), foreign key (orders_id) references orders (orders_id))");
                            StoreDB.update("CREATE TABLE payments_check (payments_check_id int NOT NULL auto_increment, orders_id int, payments_check_name varchar(64), payments_check_street1 varchar(128), payments_check_street2 varchar(128), payments_check_city varchar(64),payments_check_zip varchar(64),payments_check_state varchar(64),payments_check_country varchar(64),payments_check_phone varchar(64),payments_check_routing varchar(64),payments_check_account varchar(64),payments_check_number varchar(64), payments_check_status boolean, primary key (payments_check_id), foreign key (orders_id) references orders (orders_id))");
                            StoreDB.update("CREATE TABLE payments_credit (payments_credit_id int NOT NULL auto_increment, orders_id int, payments_credit_name varchar(64), payments_credit_number varchar(128), payments_credit_expire_month varchar(64), payments_credit_expire_year varchar(64),payments_credit_auth varchar(64), payments_credit_transid varchar(64), payments_credit_status boolean, primary key (payments_credit_id), foreign key (orders_id) references orders (orders_id))");
                        }

                        StoreDB.update(
                                "INSERT INTO configuration values(NULL, 'synpos.version', '" +
                                verStart + "')");
                        StoreDB.update(
                                "INSERT INTO users values(1,'Admin','Admin','123','123',1)");
                        StoreDB.update(
                                "INSERT INTO stations values(1,'Default Station')");
                        String NOW = new java.sql.Timestamp(Calendar.getInstance().
                                getTimeInMillis()).toString();
                        StoreDB.update(
                                "INSERT INTO categories values(1, '', 0, 'Category', '" +
                                NOW + "', '" + NOW + "')");
                        StoreDB.update(
                                "INSERT INTO customers values(1, '', 'Default', 'Customer', 'M', '', 'My Street', '', 'My City', 'CA', '00000', '0000000000', '', 'defautcustomer@mydomain.com', '" +
                                NOW + "', '" + NOW + "', true)");
                        version = verStart;
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(startPanel, e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                } else {
                    System.exit(0);
                }
            }

            if (synVersion.compare(version, VERSION) > 0) {
                JOptionPane.showMessageDialog(startPanel, "Your database version is unknown. Please upgrade your synPOS to the latest version.",
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(startPanel, e.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String myLang = Configuration.get("locale.language");
        String myCountry = Configuration.get("locale.country");
        String myVar = Configuration.get("locale.variant");

        if (myLang != null) {
            Locale.setDefault(new Locale(myLang, myCountry, myVar));
        }

        try {
            String lookNfeel = Configuration.get("system.look");

            if (lookNfeel != null) {
                UIManager.setLookAndFeel(lookNfeel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(startPanel, e.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        int fontDelta = 0;
        String fontStr = Configuration.get("system.font");

        if (fontStr != null) {
            try {
                fontDelta = Integer.parseInt(fontStr);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        if (fontDelta != 0) {
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            Iterator itor = defaults.entrySet().iterator();

            while (itor.hasNext()) {
                Map.Entry entry = (Map.Entry) itor.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();

                if (key.toUpperCase().indexOf(".FONT") >= 0 &&
                    value instanceof Font) {
                    Font font = (Font) value;
                    float size = font.getSize() + fontDelta;
                    font = new FontUIResource(font.deriveFont(size));
                    UIManager.put(key, font);
                }
            }
        }

        String stationId = Configuration.get("station.id");
        int id = 1;

        try {
            id = Integer.parseInt(stationId);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(startPanel, e.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        Station station = Station.getStation(id);

        if (station == null) {
            Configuration.set("station.id", "1");
            station = new Station(1);
        }

        LoginDialog dlg = new LoginDialog(station);
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);

        dlg.setModal(true);
        dlg.pack();
        startPanel.setVisible(false);
        dlg.setVisible(true);

        if (dlg.getUser() == null) {
            System.exit(0);
        }

        InitDialog initDlg = new InitDialog();
        initDlg.pack();
        initDlg.setSize(dlgSize);
        initDlg.setLocation((screenSize.width - dlgSize.width) / 2,
                            (screenSize.height - dlgSize.height) / 2);

        //String version = Configuration.get("synpos.version");
        initDlg.setTitle("synPOS v" + VERSION);
        initDlg.setVisible(true);

        System.setProperty(JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME,
                           new File(".").getAbsolutePath() + "/config/jpos.xml");

        String host = Configuration.get("network.https_proxy_host");
        if (host != null && host != "") {
            System.setProperty("https.proxyHost", host);
        }

        String port = Configuration.get("network.https_proxy_port");
        if (port != null && port != "") {
            System.setProperty("https.proxyPort", port);
        }

        MainFrame frame = new MainFrame(dlg.getUser(), station);
        frame.setTitle("synPOS v" + VERSION);

        // Windowed mode
        frame.pack();
        //Center the window
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getPreferredSize();

        if (frameSize.height > (0.8 * screenSize.height)) {
            frameSize.height = (int) (0.8 * screenSize.height);
        }
        if (frameSize.height < (0.75 * screenSize.height)) {
            frameSize.height = (int) (0.75 * screenSize.height);
        }
        if (frameSize.width > (0.95 * screenSize.width)) {
            frameSize.width = (int) 0.95 * screenSize.width;
        }
        if (frameSize.width < (0.75 * screenSize.width)) {
            frameSize.width = (int) (0.75 * screenSize.width);
        }

        frame.setSize(frameSize.width, frameSize.height);
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        initDlg.setVisible(false);
        frame.setVisible(true);

        if (Configuration.get("auto.sync") != null) {
            SyncDlg syncDlg = SyncDlg.getInstance();
            //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dlgSize = syncDlg.getPreferredSize();
            syncDlg.setLocation((screenSize.width - dlgSize.width) / 2,
                                (screenSize.height - dlgSize.height) / 2);
            syncDlg.pack();
            syncDlg.setVisible(true);
        }
    }
}
