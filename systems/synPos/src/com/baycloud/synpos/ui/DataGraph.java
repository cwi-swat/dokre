package com.baycloud.synpos.ui;

import java.util.*;
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
public abstract class DataGraph extends JPanel {
    protected String[] names = null;
    protected Double[][] values = null;
    protected Long[] times = null;

    // maximum value to display
    protected double yMax = 0;

    // minimum value to display
    protected double yMin = 0;

    // graph inset
    protected double xInset = 0;

    // graph inset
    protected double yInset = 0;

    // graph width
    protected int graphWidth = 0;

    // graph height
    protected int graphHeight = 0;

    public DataGraph() {
        setBackground(Color.white);
    }

    public void draw(String[] names, Long[] times, Double[][] values) {
        this.names = names;
        this.values = values;
        this.times = times;

        repaint();
    }

    protected void computeSize() {
        Insets insets = getInsets();
        graphWidth = getWidth() - insets.left - insets.right;
        graphHeight = getHeight() - insets.top - insets.bottom;
    }

    protected void computeInsets() {
        xInset = graphWidth / 20.;
        yInset = graphHeight / 20.;
    }

    protected void findYMinMax() {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                for (int j = 0; j < values[i].length; j++) {
                    if (values[i][j] != null) {
                        if (values[i][j].doubleValue() > yMax) {
                            yMax = values[i][j].doubleValue();
                        } else if (values[i][j].doubleValue() < yMin) {
                            yMin = values[i][j].doubleValue();
                        }
                    }
                }
            }
        }
    }

    protected Color getGraphColor(int index) {
        int[] rgb = new int[3];
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        switch (index % 3) {
        case 0:
            rgb[0] = 255;
            rgb[1] = (int) 255. * index / names.length;
            break;
        case 1:
            rgb[1] = 255;
            rgb[2] = (int) 255. * index / names.length;
            break;
        case 2:
            rgb[2] = 255;
            rgb[0] = (int) 255. * index / names.length;
            break;
        default:
        }

        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    protected void drawFrames(Graphics g) {
        g.setColor(Color.black);
        g.fillRect((int) xInset, (int) yInset,
                   (int) (graphWidth - 2 * xInset),
                   (int) (graphHeight - 2 * yInset));
    }

    protected void drawLegends(Graphics g) {
        if (names != null) {
            double legendWidth = graphWidth / 40.;
            double legendHeight = graphHeight / 40.;

            double y = graphHeight - (yInset - legendHeight) / 2 - legendHeight;

            FontMetrics metrics = g.getFontMetrics();
            int labelWidth = metrics.stringWidth("Legend:") + 2;
            g.drawString("Legend:", (int) xInset,
                         (int) (graphHeight - (yInset - legendHeight) / 2));

            for (int i = 0; i < names.length; i++) {
                g.setColor(getGraphColor(i));
                double x = i * 2 * legendWidth + xInset + labelWidth;
                g.fill3DRect((int) x, (int) y, (int) legendWidth,
                             (int) legendHeight, true);
            }
        }
    }
}
