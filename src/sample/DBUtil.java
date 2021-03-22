package sample;

import javax.swing.*;
import java.sql.*;

public class DBUtil {


    public Connection connectDb(String url, String username, String password) {
        Connection connection = null;
        try {
//            String url = "jdbc:mysql://localhost:3306/commproject";
//            String username = "root";
//            String password = "root";
            connection = DriverManager.getConnection(url, username, password);
            JOptionPane.showMessageDialog(null, "ConnectionEstablished");
            return connection;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
//            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param pstmt
     */
    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

        /**
         * 关闭连接
         * @param connection
         */
        public static void close(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    /**
     *
     * @param rs
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param stmt
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
