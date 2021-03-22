package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.common.utils.DataBaseUtility;
import main.java.lis.constant.PConstants;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlConnect {
    static Connection connection = null;
    static DBUtil dbUtil = new DBUtil();
    static PreparedStatement preparedStatement= null;
    static ResultSet resultSet = null;
    //tsp_test_qc_data.qc_data_v2
    public ObservableList<ObservableList> getColsVals() throws SQLException {
        ObservableList<ObservableList> vals = FXCollections.observableArrayList();
        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM tsp_test_qc_data.qc_data_v2", PConstants.TSP_SERVER)) {
            System.out.println(dbUtility);
    //            String sql = "SELECT * FROM tsp_test_qc_data.qc_data_v2";
            dbUtility.generateRecords_throwException();
    //            dbUtility.changeSQL(sql);
            try (ResultSet resultSet = dbUtility.getRecords()) {
                while (resultSet.next()) {
                    System.out.println(resultSet); // each row
                    ObservableList<String> row = FXCollections.observableArrayList();
//                    System.out.println("---->" + resultSet.getMetaData().getColumnCount());
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(resultSet.getString(i));
//                        System.out.println(row);
                    }
                    vals.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vals;
    }

//    String url = "jdbc:mysql://192.168.10.121:3306/tsp_test_qc_data";
//    String username = "qc_test";
//    String password = "000028";
//    connection = dbUtil.connectDb(url, username, password);
//    try {
//        String sql = "SELECT * from qc_data_v2";
//        preparedStatement = connection.prepareStatement(sql);
//        resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()) {
//            ObservableList<String> row = FXCollections.observableArrayList();
//            System.out.println("---->" + resultSet.getMetaData().getColumnCount());
//            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
//                //Iterate Column
//                row.add(resultSet.getString(i));
//                System.out.println(row);
//            }
//            vals.add(row);
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    } finally {
//        DBUtil.close(preparedStatement);
//        DBUtil.close(connection);
//        DBUtil.close(resultSet);
//    }


    public ObservableList<ObservableList> getColsVal() throws SQLException {
        ObservableList<ObservableList> vals = FXCollections.observableArrayList();
        String url = "jdbc:mysql://192.168.10.121:3306/tsp_test_qc_data";
        String username = "qc_test";
        String password = "000028";
        connection = dbUtil.connectDb(url, username, password);
        try {
            String sql = "SELECT * from qc_data_v2";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
//                System.out.println("---->" + resultSet.getMetaData().getColumnCount());
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(resultSet.getString(i));
//                    System.out.println(row);
                }
                vals.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
            DBUtil.close(resultSet);
        }
        return vals;
    }

    public ObservableList<Result> getResult() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/commproject";
        String username = "root";
        String password = "root";
        connection = dbUtil.connectDb(url, username, password);
//        connection = DriverManager.getConnection(url, username, password);
//        JOptionPane.showMessageDialog(null, "ConnectionEstablished");

        ObservableList<Result>  list = FXCollections.observableArrayList();
        try {
//            String sql = "INSERT INTO receive_status_check (idx, frame_index, is_intermediate, frame_content) VALUES (?, ?, ?, ?)";
            String sql = "SELECT * from receive_status_check";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new Result(resultSet.getInt("idx") + "",
                        resultSet.getInt("frame_index") + "",
                        resultSet.getInt("is_intermediate") + "",
                        resultSet.getString("frame_content") + "",
//                        resultSet.getString("ALPSIGG_IGM")
                        "a"
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
            DBUtil.close(resultSet);
        }
        return list;
    }

    public String getColsName(int index) throws SQLException {
        String url = "jdbc:mysql://192.168.10.121:3306/tsp_test_qc_data";
        String username = "qc_test";
        String password = "000028";
        String colsName = null;
        try {
            connection = dbUtil.connectDb(url, username, password);
//        connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * from qc_data_v2";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            colsName = resultSet.getMetaData().getColumnName(index + 1);
//        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
//            list.add(resultSet.getMetaData().getColumnName(i + 1));
//            System.out.println(resultSet.getMetaData().getColumnName(i + 1));
//        }
            System.out.println(resultSet.getMetaData().getColumnName(index + 1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
            DBUtil.close(resultSet);
        }
        return colsName;
    }

    public int getColsNum() throws SQLException {
        String url = "jdbc:mysql://192.168.10.121:3306/tsp_test_qc_data";
        String username = "qc_test";
        String password = "000028";
        int colsNum = -1;
        try {
            connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * from qc_data_v2";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            colsNum = resultSet.getMetaData().getColumnCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
            DBUtil.close(resultSet);
        }
        return colsNum;
    }

//    public void connect() throws SQLException {
//        String url = "jdbc:mysql://localhost:3306/commproject";
//        String username = "root";
//        String password = "root";
//
//        Connection connection = DriverManager.getConnection(url, username, password);
//        System.out.println("Connected to the database");
//        String sql = "INSERT INTO receive_status_check (idx, frame_index, is_intermediate, frame_content) VALUES (?, ?, ?, ?)";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setInt(1, 0);
//        statement.setInt(2, 1);
//        statement.setInt(3, 1);
//        statement.setString(4, "O|1|8701|^5100^1|^^^767|R|||||||||20080512103151|1|||||||||ModularR|1|^^^767|1|||27||OPER42^|||E12");
//        statement.executeUpdate();
//    }

}
