package com.baycloud.synpos.xt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
public class MyDB {
    private static Connection conn; //our connnection to the db - presist for life of program

    public static synchronized void shutdown() throws SQLException {
        if (conn != null) {
            Statement st = conn.createStatement();

            // db writes out to files and performs clean shuts down
            // otherwise there will be an unclean shutdown
            // when program ends
            st.execute("SHUTDOWN");
            conn.close(); // if there are no other open connection
            conn = null;
        }
    }

//use for SQL command SELECT
    public static synchronized ResultSet query(String expression) throws
            SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:hsqldb:file:db/synpos", // filenames
                                               "sa", // username
                                               ""); // password
        }

        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement(); // statement objects can be reused with

        // repeated calls to execute but we
        // choose to make a new one each time
        rs = st.executeQuery(expression); // run the query

        // do something with the result set.
        //dump(rs);
        st.close(); // NOTE!! if you close a statement the associated ResultSet is

        // closed too
        // so you should copy the contents to some other object.
        // the result set is invalidated also  if you recycle an Statement
        // and try to execute some other query before the result set has been
        // completely examined.
        return rs;
    }

//use for SQL commands CREATE, DROP, INSERT and UPDATE
    public static synchronized int update(String expression) throws
            SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:hsqldb:file:db/synpos", // filenames
                                               "sa", // username
                                               ""); // password
        }

        Statement st = null;
        st = conn.createStatement(); // statements
        int i = st.executeUpdate(expression); // run the query
        st.close();
        return i;
    } // void update()

    //use for SQL commands CREATE, DROP, INSERT and UPDATE
    public static synchronized int insertID() throws SQLException {
        if (conn == null) {
            return -1;
        }

        Statement st = null;
        int lastID = -1;
        st = conn.createStatement(); // statements
        ResultSet rs = st.executeQuery("CALL IDENTITY()"); // run the query
        //System.out.print(rs.getMetaData().toString());
        if (rs.next()) {
            lastID = rs.getInt(1);
        }
        st.close();
        return lastID;
    } // void update()

    public static void dump(ResultSet rs) throws SQLException {

        // the order of the rows in a cursor
        // are implementation dependent unless you use the SQL ORDER statement
        ResultSetMetaData meta = rs.getMetaData();
        int colmax = meta.getColumnCount();
        int i;
        Object o = null;

        // the result set is a cursor into the data.  You can only
        // point to one row at a time
        // assume we are pointing to BEFORE the first row
        // rs.next() points to next row and returns true
        // or false if there is no next row, which breaks the loop
        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1); // Is SQL the first column is indexed

                // with 1 not 0
                System.out.print(o.toString() + " ");
            }

            System.out.println(" ");
        }
    } //void dump( ResultSet rs )

    public static String escape(String str) {
        if (str == null) {
            return str;
        } else {
            return str.replaceAll("'", "''");
        }
    }

    static {
        try {
            Class.forName("org.hsqldb.jdbcDriver");

            // connect to the database.   This will load the db files and start the
            // database if it is not alread running.
            // db_file_name_prefix is used to open or create files that hold the state
            // of the db.
            // It can contain directory names relative to the
            // current working directory
            conn = DriverManager.getConnection("jdbc:hsqldb:file:db/synpos", // filenames
                                               "sa", // username
                                               ""); // password
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
