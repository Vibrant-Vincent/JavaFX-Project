package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.common.utils.DataBaseUtility;
import main.java.lis.constant.PConstants;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class MysqlTest {
    static Connection connection = null;
    static final String username = "qc_test";
    static final String password = "000028";

    /*
    *
    */
    //Given pillarPlateID to get test name
    public  String getTestType(String pillarPlateID) throws SQLException {
        String testName = null;
        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM vibrant_test_tracking.pillar_plate_info where pillar_plate_id = "  + "\"" + pillarPlateID + "\"", PConstants.TSP_SERVER)) {
            System.out.println("ConnectionEstablished");
            dbUtility.generateRecords_throwException();
            try (ResultSet resultSet = dbUtility.getRecords()) {
                while (resultSet.next()) {
                    testName = resultSet.getString("test_name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testName;
    }

    //Given test name to get QC name
    public  Set<String> getQCNames(String testName) throws SQLException {
        Set<String> qcNamesSet = new HashSet<>();
        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM tsp_test_qc_data.plate_qc_mapping where test_type = " + "\"" + testName + "\"", PConstants.TSP_SERVER)) {
            System.out.println("ConnectionEstablished");
            dbUtility.generateRecords_throwException();
            try (ResultSet resultSet = dbUtility.getRecords()) {
                while (resultSet.next()) {
                    String qc_name = resultSet.getString("qc_name");
                    qcNamesSet.add(qc_name);
//                    System.out.println("qcNamesSet : " + qcNamesSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qcNamesSet;
    }

    //Given pillarPlateID to get test name
    public  Set<String> getTestNames(String pillarPlateID) throws SQLException {
        Set<String> testName = new HashSet<>();
        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM tsp_test_qc_data.qc_data_v2 where pillar_plate_id = "  + "\"" + pillarPlateID + "\"", PConstants.TSP_SERVER)) {
//            System.out.println(dbUtility);
            System.out.println("ConnectionEstablished");
            //            String sql = "SELECT * FROM tsp_test_qc_data.qc_data_v2";
            dbUtility.generateRecords_throwException();

            //            dbUtility.changeSQL(sql);
            try (ResultSet resultSet = dbUtility.getRecords()) {
                while (resultSet.next()) {
                    testName.add(resultSet.getString("test_name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testName;
    }


    //Given pillarPlateID, testName, QCName to get value.
    public  String getQCValue(String pillarPlateID, String testName, String QCName) throws SQLException {
        String value = null;
        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM tsp_test_qc_data.qc_data_v2 " +
                "where pillar_plate_id = " + "\"" + pillarPlateID + "\"" +
                "and test_name = " + "\"" + testName + "\"" +
                "and qc_name = " + "\"" + QCName + "\""
                , PConstants.TSP_SERVER)){
            System.out.println("ConnectionEstablished");
            dbUtility.generateRecords_throwException();
            try (ResultSet resultSet = dbUtility.getRecords()) {
                while (resultSet.next()) {
                    value = resultSet.getString("value");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public Map<String, String> getQCNameAndValue(String pillarPlateID, String testName, String QCName, Map<String, String> QCNameAndValue) throws SQLException {
//        WHERE `pillar_plate_id`='FDAD80030001000020' and`test_name`='FOOA' and`qc_name`='Pos2';
        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM tsp_test_qc_data.qc_data_v2 " +
                "where pillar_plate_id = " + "\"" + pillarPlateID + "\"" +
                "and test_name = " + "\"" + testName + "\"" +
                "and qc_name = " + "\"" + QCName + "\""
                , PConstants.TSP_SERVER)) {
            System.out.println("ConnectionEstablished");
            dbUtility.generateRecords_throwException();
            try (ResultSet resultSet = dbUtility.getRecords()) {
                while (resultSet.next()) {
                    QCNameAndValue.put(QCName,resultSet.getString("value"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(QCNameAndValue);
        return QCNameAndValue;
    }



//    public ObservableList<ObservableList> getColsVal(String testName, Map<String, String> ) throws SQLException {
//        ObservableList<ObservableList> vals = FXCollections.observableArrayList();
//        return vals;
//    }



}
