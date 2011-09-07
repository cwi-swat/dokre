package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import org.hsqldb.util.*;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

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
public class MainFrame extends JFrame {
    private User user;
    private Station station;
    //private JPanel contentPane;
    private BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    CardLayout cardLayout1 = new CardLayout();
    private JButton jButton3 = new JButton();
    private JButton jButton5 = new JButton();
    private JButton jButton4 = new JButton();
    private JButton jButton6 = new JButton();
    private JToolBar jToolBar1 = new JToolBar();
    private JButton jButton1 = new JButton();
    private JLabel jLabel1 = new JLabel();
    JButton jButton2 = new JButton();
    JButton jButton7 = new JButton();
    JButton jButton8 = new JButton();
    JButton jButton9 = new JButton();
    Border border10 = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    JButton jButton10 = new JButton();
    JButton jButton11 = new JButton();
    Hashtable panels = new Hashtable();
    JLabel jLabel2 = new JLabel();

    //Construct the frame
    public MainFrame(User user, Station station) {
        this.user = user;
        this.station = station;
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        //this.setSize(new Dimension(400, 300));
        this.setTitle("synPOS");
        jPanel2.setLayout(cardLayout1);
        jButton3.setMnemonic('X');
        jButton3.setText(I18N.getButtonString("Exit"));
        jButton3.addActionListener(new MainFrame_jButton3_actionAdapter(this));
        jButton5.setText(I18N.getButtonString("Configuration"));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton5_actionPerformed(e);
            }
        });
        jButton4.setText(I18N.getButtonString("Sales"));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton4_actionPerformed(e);
            }
        });
        jButton6.setText(I18N.getButtonString("Users"));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton6_actionPerformed(e);
            }
        });

        //jButton1.setNextFocusableComponent(jButton4);
        jButton1.setActionCommand("New Sale");
        jButton1.setText(I18N.getButtonString("New Sale"));

        jButton1.addActionListener(new MainFrame_jButton1_actionAdapter(this));
        jToolBar1.setMargin(new Insets(5, 5, 5, 5));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("<html><b><nobr>"+I18N.getLabelString("STATION")+": " + station.getName() +
                        " / "+I18N.getLabelString("USER")+": " +
                        user.getUsername() + "</nobr></b></html>");
        jButton2.setText(I18N.getButtonString("Products"));
        jButton2.addActionListener(new MainFrame_jButton2_actionAdapter(this));
        jButton7.setText(I18N.getButtonString("Reports"));
        jButton7.addActionListener(new MainFrame_jButton7_actionAdapter(this));
        jButton8.setText(I18N.getButtonString("Database"));
        jButton8.addActionListener(new MainFrame_jButton8_actionAdapter(this));
        jButton9.setMnemonic('O');
        jButton9.setText(I18N.getButtonString("Logout"));
        jButton9.addActionListener(new MainFrame_jButton9_actionAdapter(this));
        jPanel2.setBorder(border10);
        jButton10.setText(I18N.getButtonString("Customers"));
        jButton10.addActionListener(new MainFrame_jButton10_actionAdapter(this));
        jButton11.setText(I18N.getButtonString("Stations"));
        jButton11.addActionListener(new MainFrame_jButton11_actionAdapter(this));
        NewSalePanel panel = new NewSalePanel(this);
        panels.put(jButton1.getActionCommand(), panel);
        jLabel2.setText(" ");
        jPanel2.add(panel, jButton1.getActionCommand());
        /*
         jPanel2.add(new ProductsPanel(this), jButton2.getActionCommand());
                jPanel2.add(new SalesPanel(this), jButton4.getActionCommand());
         jPanel2.add(new ConfigPanel(this), jButton5.getActionCommand());
                jPanel2.add(new UsersPanel(this), jButton6.getActionCommand());
         jPanel2.add(new ReportsPanel(this), jButton7.getActionCommand());
         jPanel2.add(new CustomersPanel(this), jButton10.getActionCommand());
         jPanel2.add(new StationsPanel(this), jButton11.getActionCommand());
         */
        this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
        jToolBar1.add(jButton1, null);
        jToolBar1.add(jButton2);
        jToolBar1.add(jButton10);
        jToolBar1.add(jButton4, null);
        jToolBar1.add(jButton7);
        jToolBar1.add(jButton11);
        jToolBar1.add(jButton6, null);
        jToolBar1.add(jButton5, null);
        jToolBar1.add(jButton9);
        //jToolBar1.add(jButton8);
        jToolBar1.add(jButton3, null);
        jToolBar1.add(jLabel2);
        jToolBar1.add(jLabel1, null);
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
        setButtonStatus();
    }

    private void setButtonStatus() {
        if (user.getType() == User.EMPLOYEE) {
            jButton4.setEnabled(false);
        } else {
            jButton4.setEnabled(true);
        }
        if (user.getType() == User.EMPLOYEE) {
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
        if (user.getType() == User.EMPLOYEE) {
            jButton7.setEnabled(false);
        } else {
            jButton7.setEnabled(true);
        }
        if (user.getType() == User.EMPLOYEE) {
            jButton10.setEnabled(false);
        } else {
            jButton10.setEnabled(true);
        }
        if (user.getType() != User.ADMINISTRATOR) {
            jButton11.setEnabled(false);
        } else {
            jButton11.setEnabled(true);
        }
        if (user.getType() != User.ADMINISTRATOR) {
            jButton8.setEnabled(false);
        } else {
            jButton8.setEnabled(true);
        }
        if (user.getType() != User.ADMINISTRATOR) {
            jButton6.setEnabled(false);
        } else {
            jButton6.setEnabled(true);
        }
        if (user.getType() != User.ADMINISTRATOR) {
            jButton5.setEnabled(false);
        } else {
            jButton5.setEnabled(true);
        }
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    public void changeSize() {
        Dimension size = getSize();
        int width = size.width;
        int height = size.height;

        if (width < getPreferredSize().width) {
            width = getPreferredSize().width;
        }

        if (height < getPreferredSize().height) {
            height = getPreferredSize().height;
        }

        //setSize(width, height);
        validate();
        repaint();
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new NewSalePanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((NewSalePanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new ProductsPanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((ProductsPanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void jButton4_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new SalesPanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((SalesPanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }

    void jButton5_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new ConfigPanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((ConfigPanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }

    void jButton6_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new UsersPanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((UsersPanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }

    public void jButton7_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new ReportsPanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((ReportsPanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }

    public void jButton8_actionPerformed(ActionEvent e) {
        try {
            StoreDB.shutdown();
            JFrame frame = new JFrame();
            DatabaseManagerSwing dbMan = new DatabaseManagerSwing(frame);
            dbMan.main();
            /*
                Runtime.getRuntime().exec(new String[] {"java", "-cp",
                                          "../lib/hsqldb.jar",
             "org.hsqldb.util.DatabaseManagerSwing"});
             */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jButton9_actionPerformed(ActionEvent e) {
        setVisible(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        LoginDialog dlg = new LoginDialog(station);
        dlg.setModal(true);
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.setTitle(getTitle());
        //dlg.setModal(true);
        dlg.pack();
        dlg.setVisible(true);

        while (dlg.isVisible()) {
            try {
                Thread.sleep(10);
            } catch (Exception ex) {
            }
        }

        if (dlg.getUser() == null) {
            System.exit(0);
        }

        this.user = dlg.user;
        jLabel1.setText("<html><b><nobr>"+I18N.getLabelString("STATION")+": " + station.getName() +
                        " / "+I18N.getLabelString("USER")+": " +
                        user.getUsername() + "</nobr></b></html>");

        setButtonStatus();
        setVisible(true);
    }

    public User getUser() {
        return user;
    }

    public Station getStation() {
        return station;
    }

    public void jButton10_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new CustomersPanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((CustomersPanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }

    public void jButton11_actionPerformed(ActionEvent e) {
        Object panel = panels.get(e.getActionCommand());

        if (panel == null) {
            panel = new StationsPanel(this);
            panels.put(e.getActionCommand(), panel);
            jPanel2.add((StationsPanel) panel, e.getActionCommand());
        }

        CardLayout cl = (CardLayout) jPanel2.getLayout();
        cl.show(jPanel2, e.getActionCommand());
        changeSize();
    }
}


class MainFrame_jButton11_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton11_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton11_actionPerformed(e);
    }
}


class MainFrame_jButton10_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton10_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton10_actionPerformed(e);
    }
}


class MainFrame_jButton9_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton9_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton9_actionPerformed(e);
    }
}


class MainFrame_jButton8_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton8_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton8_actionPerformed(e);
    }
}


class MainFrame_jButton7_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton7_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton7_actionPerformed(e);
    }
}


class MainFrame_jButton2_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton2_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class MainFrame_jButton3_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton3_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}


class MainFrame_jButton1_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jButton1_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
