package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;
import com.baycloud.synpos.xt.Synchronizer;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

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
public class ProductsPanel extends JPanel implements TreeModelListener,
        TreeSelectionListener, ActionListener {
    JFrame parent;

    public ProductsPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jScrollPane2.addMouseListener(new
                                      ProductsPanel_jScrollPane2_mouseAdapter(this));
        jTable1.addMouseListener(new ProductsPanel_jScrollPane2_mouseAdapter(this));
        jPanel1.setBorder(null);
        jPanel1.setLayout(flowLayout1);
        jLabel1.setText(I18N.getLabelString("Item#") + ": ");
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jTextField1.setColumns(15);
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextField1.addKeyListener(new ProductsPanel_jTextField1_keyAdapter(this));
        jButton1.setMnemonic('F');
        jButton1.setText(I18N.getButtonString("Find"));
        jButton1.addActionListener(new ProductsPanel_jButton1_actionAdapter(this));
        jButton2.setMnemonic('I');
        jButton2.setText(I18N.getButtonString("Import"));
        jButton2.addActionListener(new ProductsPanel_jButton2_actionAdapter(this));
        TreeModel treeModel = new CategoryTreeModel();
        treeModel.addTreeModelListener(this);
        jTree1.setModel(treeModel);
        jTree1.setEditable(true);
        jTree1.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.setShowsRootHandles(true);
        jTree1.addTreeSelectionListener(this);
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        jPanel1.add(jLabel1);
        jPanel1.add(jTextField1);
        jPanel1.add(jButton1);
        jTable1.setModel(new ProductsTableModel(0));
        jScrollPane2.getViewport().add(jTable1);
        this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane1.add(jScrollPane2, JSplitPane.BOTTOM);
        jSplitPane1.add(jScrollPane1, JSplitPane.TOP);
        jScrollPane1.getViewport().add(jTree1);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        this.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton2);
        jScrollPane1.addMouseListener(new
                                      ProductsPanel_jScrollPane1_mouseAdapter(this));
        jTree1.addMouseListener(new ProductsPanel_jScrollPane1_mouseAdapter(this));
        jTable1.setCellSelectionEnabled(true);
        jSplitPane1.setDividerLocation(150);
        TableExcelAdapter myAd1 = new TableExcelAdapter(jTable1);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane jSplitPane1 = new JSplitPane();
    JScrollPane jScrollPane1 = new JScrollPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JTree jTree1 = new JTree();
    JTable jTable1 = new JTable();

    public final static int ONE_SECOND = 1000;

    ProgressMonitor progressMonitor;
    Timer timer;
    Category task;

    public void treeNodesChanged(TreeModelEvent e) {
        Category node;
        node = (Category)
               (e.getTreePath().getLastPathComponent());

        /*
         * If the event lists children, then the changed
         * node is the child of the node we've already
         * gotten.  Otherwise, the changed node and the
         * specified node are the same.
         */
        try {
            int index = e.getChildIndices()[0];
            node = (Category)
                   (node.getChildAt(index));
            node.setName(node.getUserObject().toString());
        } catch (NullPointerException exc) {}
    }

    public void treeNodesInserted(TreeModelEvent e) {
    }

    public void treeNodesRemoved(TreeModelEvent e) {
    }

    public void treeStructureChanged(TreeModelEvent e) {
    }

    public void valueChanged(TreeSelectionEvent e) {
        Category node = (Category)
                        jTree1.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }
        ((ProductsTableModel) jTable1.getModel()).setCategory(node.getId());
        jTable1.revalidate();
        jTable1.repaint();
    }

    public void jScrollPane2_mouseReleased(MouseEvent e) {
        productShowPopup(e);
    }

    public void jScrollPane2_mousePressed(MouseEvent e) {
        productShowPopup(e);
    }

    public void jScrollPane1_mouseReleased(MouseEvent e) {
        categoryShowPopup(e);
    }

    public void jScrollPane1_mousePressed(MouseEvent e) {
        categoryShowPopup(e);
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        CategoryTreeModel treeModel = (CategoryTreeModel) jTree1.getModel();
        //Category root = (Category) treeModel.getRoot();
        Category node = (Category) jTree1.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        ProductsTableModel tableModel = (ProductsTableModel) jTable1.getModel();

        if (source.getActionCommand().equals("New Category")) {
            Category newCat = treeModel.createCategory(node);

            if (newCat != null) {
                jTree1.scrollPathToVisible(new TreePath(newCat.getPath()));
            }
        } else if (source.getActionCommand().equals("Copy Category")) {
            copy = node;
            cut = null;
        } else if (source.getActionCommand().equals("Cut Category")) {
            cut = node;
            copy = null;
        } else if (source.getActionCommand().equals("Paste Category")) {
            if (copy != null) {
                Category newCat = treeModel.copyPaste(copy, node);
                if (newCat != null) {
                    jTree1.scrollPathToVisible(new TreePath(newCat.getPath()));
                }
                copy = null;
            } else if (cut != null) {
                Category newCat = treeModel.cutPaste(cut, node);
                if (newCat != null) {
                    jTree1.scrollPathToVisible(new TreePath(newCat.getPath()));
                }

                cut = null;
            }
        } else if (source.getActionCommand().equals("Delete Category") && node != null) {
            int option = JOptionPane.showConfirmDialog(parent,
                    I18N.getMessageString(
                    "Are you sure you want to delete this category and its contents?"),
                                 I18N.getLabelString("Confirm"),
                                 JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                treeModel.deleteCategory(node);
            }
        } else if (source.getActionCommand().equals("New Product")) {
            tableModel.createProduct();
        } else if (source.getActionCommand().equals("Copy Product")) {
            int[] selectedRows = jTable1.getSelectedRows();

            for (int i = 0; i < selectedRows.length; i++) {
                tableModel.copyProduct(selectedRows[i]);
            }
        } else if (source.getActionCommand().equals("Delete Product")) {
            int option = JOptionPane.showConfirmDialog(parent,
                    I18N.getMessageString(
                    "Are you sure you want to delete the selected product(s)?"),
                                 I18N.getLabelString("Confirm"),
                                 JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                int[] selectedRows = jTable1.getSelectedRows();
                tableModel.deleteProducts(selectedRows);
            }
        }

        jTree1.revalidate();
        jTree1.repaint();
        jTable1.revalidate();
        jTable1.repaint();
    }

    private void productShowPopup(MouseEvent e) {
        CategoryTreeModel treeModel = (CategoryTreeModel) jTree1.getModel();
        //Category root = (Category) treeModel.getRoot();
        Category node = (Category) jTree1.getLastSelectedPathComponent();

        if (e.isPopupTrigger() && node != null) {
            JPopupMenu popup = new JPopupMenu();
            JMenuItem copyItem, clearItem, selectAllItem;
            Action action = new AbstractAction("Copy Selected") {
                public void actionPerformed(ActionEvent ae) {
                    StringBuffer sbf = new StringBuffer();
                    // Check to ensure we have selected only a contiguous block of
                    // cells
                    int numcols = jTable1.getSelectedColumnCount();
                    int numrows = jTable1.getSelectedRowCount();
                    int[] rowsselected = jTable1.getSelectedRows();
                    int[] colsselected = jTable1.getSelectedColumns();
                    if (!((numrows - 1 ==
                           rowsselected[rowsselected.length - 1] -
                           rowsselected[0] &&
                           numrows == rowsselected.length) &&
                          (numcols - 1 ==
                           colsselected[colsselected.length - 1] -
                           colsselected[0] &&
                           numcols == colsselected.length))) {
                        JOptionPane.showMessageDialog(null,
                                I18N.getMessageString("Invalid copy selection."),
                                I18N.getLabelString("Error"),
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    for (int i = 0; i < numrows; i++) {
                        for (int j = 0; j < numcols; j++) {
                            sbf.append(jTable1.getValueAt(rowsselected[i],
                                    colsselected[j]));
                            if (j < numcols - 1) {
                                sbf.append("\t");
                            }
                        }
                        sbf.append("\n");
                    }
                    StringSelection stsel = new StringSelection(sbf.toString());
                    Clipboard system = Toolkit.getDefaultToolkit().
                                       getSystemClipboard();
                    system.setContents(stsel, stsel);
                }
            };
            copyItem = popup.add(action);
            copyItem.setName(I18N.getButtonString("Copy Selected"));
            //copyItem.setMnemonic('c');

            action = new AbstractAction("Select All") {
                public void actionPerformed(ActionEvent ae) {
                    jTable1.changeSelection(0, 0, false, false);
                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        for (int j = 0; j < jTable1.getColumnCount(); j++) {
                            jTable1.changeSelection(i, j, false, true);
                        }
                    }
                    jTable1.revalidate();
                    jTable1.repaint();
                }
            };
            selectAllItem = popup.add(action);
            selectAllItem.setName(I18N.getButtonString("Select All"));
            //selectAllItem.setMnemonic('a');

            action = new AbstractAction("Clear Selection") {
                public void actionPerformed(ActionEvent ae) {
                    jTable1.clearSelection();
                    jTable1.revalidate();
                    jTable1.repaint();
                }
            };
            clearItem = popup.add(action);
            clearItem.setName(I18N.getButtonString("Clear Selection"));
            //clearItem.setMnemonic('c');
            boolean copyAvailable = (jTable1.getSelectedRowCount() > 0 &&
                                     jTable1.getRowCount() > 0);
            boolean selectAllAvailable = (jTable1.getCellSelectionEnabled() &&
                                          jTable1.getRowCount() > 0);
            copyItem.setEnabled(copyAvailable);
            clearItem.setEnabled(copyAvailable);
            popup.addSeparator();

            selectAllItem.setEnabled(selectAllAvailable);
            JMenuItem menuItem = new JMenuItem(I18N.getButtonString(
                    "New Product"));
            menuItem.setActionCommand("New Product");
            menuItem.addActionListener(this);
            popup.add(menuItem);

            menuItem = new JMenuItem(I18N.getButtonString("Copy Product"));
            menuItem.setActionCommand("Copy Product");
            menuItem.addActionListener(this);
            popup.add(menuItem);
            if (jTable1.getSelectedRow() < 0 || jTable1.getRowCount() == 0) {
                menuItem.setEnabled(false);
            }
            menuItem = new JMenuItem(I18N.getButtonString("Delete Product"));
            menuItem.setActionCommand("Delete Product");
            menuItem.addActionListener(this);
            popup.add(menuItem);
            if (jTable1.getSelectedRow() < 0 || jTable1.getRowCount() == 0) {
                menuItem.setEnabled(false);
            }
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }

    private Category copy;
    private Category cut;
    JPanel jPanel1 = new JPanel();
    TitledBorder titledBorder1 = new TitledBorder("");
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, "Products");
    JLabel jLabel1 = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();
    JTextField jTextField1 = new JTextField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();

    private void categoryShowPopup(MouseEvent e) {
        Category node = (Category) jTree1.
                        getLastSelectedPathComponent();

        if (e.isPopupTrigger() && node != null) {
            JPopupMenu popup = new JPopupMenu();
            JMenuItem menuItem = new JMenuItem(I18N.getButtonString(
                    "New Category"));
            menuItem.setActionCommand("New Category");
            menuItem.addActionListener(this);
            popup.add(menuItem);

            menuItem = new JMenuItem(I18N.getButtonString("Copy Category"));
            menuItem.setActionCommand("Copy Category");
            menuItem.addActionListener(this);
            popup.add(menuItem);
            if (node.isRoot()) {
                menuItem.setEnabled(false);
            }

            menuItem = new JMenuItem(I18N.getButtonString("Delete Category"));
            menuItem.setActionCommand("Delete Category");
            menuItem.addActionListener(this);
            popup.add(menuItem);
            if (node.isRoot()) {
                menuItem.setEnabled(false);
            }

            menuItem = new JMenuItem(I18N.getButtonString("Cut Category"));
            menuItem.setActionCommand("Cut Category");
            menuItem.addActionListener(this);
            popup.add(menuItem);
            if (node.isRoot()) {
                menuItem.setEnabled(false);
            }

            menuItem = new JMenuItem(I18N.getButtonString("Paste Category"));
            menuItem.setActionCommand("Paste Category");
            menuItem.addActionListener(this);
            popup.add(menuItem);

            if (copy == null && cut == null) {
                menuItem.setEnabled(false);
            }

            popup.show(e.getComponent(),
                       e.getX(), e.getY());

        }
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        String barcode = jTextField1.getText().trim();

        if (barcode.equals("")) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Please enter an item#."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
        findProduct(barcode);
    }

    public void jTextField1_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            String barcode = jTextField1.getText().trim();
            findProduct(barcode);
        }
    }

    private void findProduct(String barcode) {
        int catId = Product.getProductCategory(barcode);

        if (catId < 0) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Product not found."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        CategoryTreeModel treeModel = (CategoryTreeModel) jTree1.getModel();
        Category cat = treeModel.getCategory(catId);

        if (cat == null) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Product not found."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        //jTree1.scrollPathToVisible(new TreePath(cat.getPath()));
        jTree1.setSelectionPath(new TreePath(cat.getPath()));
        ProductsTableModel tableModel = (ProductsTableModel) jTable1.getModel();
        tableModel.setCategory(cat.getId());
        int row = tableModel.findProduct(barcode);
        if (row >= 0) {
            jTable1.changeSelection(row, 0, false, false);
        }

        jTree1.revalidate();
        jTree1.repaint();
        jTable1.revalidate();
        jTable1.repaint();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        if (Synchronizer.getMode() == Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                    "Synchronization not configured."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        String syncUrl = StoreConfiguration.get("sync_url");
        if (syncUrl == null) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                    "Synchronization not configured."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(parent,
                I18N.getMessageString(
                "This will delete all the cached product data. Do you want to continue?"),
                             I18N.getLabelString("Confirm"),
                             JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            CategoryTreeModel treeModel = (CategoryTreeModel) jTree1.getModel();
            treeModel.deleteAllCategory();
            treeModel.reload();
            jTree1.revalidate();
            jTree1.repaint();

            task = (Category) treeModel.getRoot();
            timer = new Timer(ONE_SECOND, new TimerListener());
            progressMonitor = new ProgressMonitor(parent,
                                                  I18N.getMessageString(
                    "This process may take a few seconds."),
                                                  I18N.getMessageString(
                    "Importing ..."), 0,
                                                  task.getLengthOfTask());
            progressMonitor.setProgress(0);
            progressMonitor.setMillisToDecideToPopup(2 * ONE_SECOND);
            task.go();
            timer.start();

            treeModel.reload();
            jTree1.revalidate();
            jTree1.repaint();
            jTable1.revalidate();
            jTable1.repaint();
        }
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            progressMonitor.setProgress(task.getCurrent());
            String s = task.getMessage();
            if (s != null) {
                progressMonitor.setNote(s);
                CategoryTreeModel treeModel = (CategoryTreeModel) jTree1.
                                              getModel();
                treeModel.reload();
                jTree1.revalidate();
                jTree1.repaint();
                jTable1.revalidate();
                jTable1.repaint();
            }
            if (progressMonitor.isCanceled() || task.isDone()) {
                progressMonitor.close();
                task.stop();
                Toolkit.getDefaultToolkit().beep();
                timer.stop();
                CategoryTreeModel treeModel = (CategoryTreeModel) jTree1.
                                              getModel();
                treeModel.reload();
                jTree1.revalidate();
                jTree1.repaint();
                jTable1.revalidate();
                jTable1.repaint();
            }
        }
    }

}


class ProductsPanel_jButton2_actionAdapter implements ActionListener {
    private ProductsPanel adaptee;
    ProductsPanel_jButton2_actionAdapter(ProductsPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class ProductsPanel_jTextField1_keyAdapter extends KeyAdapter {
    private ProductsPanel adaptee;
    ProductsPanel_jTextField1_keyAdapter(ProductsPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void keyTyped(KeyEvent e) {
        adaptee.jTextField1_keyTyped(e);
    }
}


class ProductsPanel_jButton1_actionAdapter implements ActionListener {
    private ProductsPanel adaptee;
    ProductsPanel_jButton1_actionAdapter(ProductsPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class ProductsPanel_jScrollPane1_mouseAdapter extends MouseAdapter {
    private ProductsPanel adaptee;
    ProductsPanel_jScrollPane1_mouseAdapter(ProductsPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseReleased(MouseEvent e) {
        adaptee.jScrollPane1_mouseReleased(e);
    }

    public void mousePressed(MouseEvent e) {
        adaptee.jScrollPane1_mousePressed(e);
    }
}


class ProductsPanel_jScrollPane2_mouseAdapter extends MouseAdapter {
    private ProductsPanel adaptee;
    ProductsPanel_jScrollPane2_mouseAdapter(ProductsPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseReleased(MouseEvent e) {
        adaptee.jScrollPane2_mouseReleased(e);
    }

    public void mousePressed(MouseEvent e) {
        adaptee.jScrollPane2_mousePressed(e);
    }
}
