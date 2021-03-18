package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlConnect {
    static Connection connection = null;
    public Connection connectDb() {
        try {
            String url = "jdbc:mysql://localhost:3306/commproject";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, username, password);
            JOptionPane.showMessageDialog(null, "ConnectionEstablished");
            return connection;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
//            e.printStackTrace();
            return null;
        }

    }

    public ObservableList<ObservableList> getColsVal() {
        ObservableList<ObservableList> vals = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from receive_status_check";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                System.out.println("---->" + resultSet.getMetaData().getColumnCount());
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(resultSet.getString(i));
                    System.out.println(row);
                }
                vals.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vals;
    }

        public ObservableList<Result> getResult() {
//        Connection connection = ConnectionDb();
        ObservableList<Result>  list = FXCollections.observableArrayList();
        try {
//            String sql = "INSERT INTO receive_status_check (idx, frame_index, is_intermediate, frame_content) VALUES (?, ?, ?, ?)";
            String sql = "SELECT * from receive_status_check";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        }
        return list;
    }

    public String getColsName(int index) throws SQLException {
//        List<String> list = new ArrayList<>();
//        Connection connection = ConnectionDb();
        String sql = "SELECT * from receive_status_check";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
//        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
//            list.add(resultSet.getMetaData().getColumnName(i + 1));
//            System.out.println(resultSet.getMetaData().getColumnName(i + 1));
//        }
        System.out.println(resultSet.getMetaData().getColumnName(index + 1));

        return resultSet.getMetaData().getColumnName(index + 1);
    }

    public int getColsNum() throws SQLException {
        String sql = "SELECT * from receive_status_check";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return  resultSet.getMetaData().getColumnCount();
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
