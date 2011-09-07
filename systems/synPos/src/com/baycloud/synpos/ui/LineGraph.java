package com.baycloud.synpos.ui;

import java.awt.*;
import java.awt.event.*;
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
public class LineGraph extends DataGraph {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        computeSize();
        computeInsets();
        findYMinMax();
        drawFrames(g);
        drawLegends(g);
        drawLines(g);
    }

    protected void drawLines(Graphics g) {
        if (values != null) {
            double pointWidth = graphWidth / 40.;
            double pointHeight = graphHeight / 40.;

            for (int i = 0; i < values.length; i++) {
                Color c = getGraphColor(i);
                g.setColor(c);

                double lastX = 0;
                double lastY = 0;
                double x;
                double y;

                for (int j = 0; j < values[i].length; j++) {
                    if (values[i][j] != null) {
                        x = computeX(j);
                        y = computeY(values[i][j].doubleValue());

                        if (j > 0) {
                            g.drawLine((int) lastX, (int) lastY, (int) x,
                                       (int) y);
                        }

                        lastX = x;
                        lastY = y;

                        x -= pointWidth / 2;
                        y -= pointHeight / 2;
                        double width = pointWidth;
                        double height = pointHeight;

                        if (x < xInset) {
                            width -= xInset - x;
                            x = xInset;
                        } else if ((x + width) > (graphWidth - xInset)) {
                            width = graphWidth - xInset - x;
                        }

                        if (y < yInset) {
                            height -= yInset - y;
                            y = yInset;
                        } else if ((y + height) > (graphHeight - yInset)) {
                            height = graphHeight - yInset - y;
                        }

                        g.fill3DRect((int) x, (int) y, (int) width,
                                     (int) height, true);
                    }
                }
            }
        }
    }

    private double computeX(int index) {
        double x = xInset +
                   index * (graphWidth - 2 * xInset) / (times.length - 1);
        return x;
    }

    private double computeY(double value) {
        double y = graphHeight - yInset;

        if (yMax != 0 || yMin != 0) {
            // * 0.8 to make some extra room
            y = (yMax - value * 0.8) / (yMax - yMin) *
                (graphHeight - 2 * yInset) + yInset;
        }

        return y;
    }

    // testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        LineGraph graph = new LineGraph();
        frame.getContentPane().add(graph);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setSize(300, 300);
        frame.setLocation(300, 300);
        frame.pack();
        frame.setVisible(true);
        String[] names = {"test"};
        Double[] values = {new Double(1), new Double(5), new Double(2)};
        Double[][] data = {values};
        Long[] times = {new Long(100), new Long(200), new Long(300)};
        graph.draw(names, times, data);
    }
}
