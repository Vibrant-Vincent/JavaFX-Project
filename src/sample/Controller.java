package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.java.common.utils.DataBaseUtility;
import main.java.lis.constant.PConstants;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Controller implements Initializable {
//    public void pressButton(ActionEvent event) {
//        System.out.println("Hello world!");
//    }
    MysqlConnect mysqlConnect = new MysqlConnect();
    MysqlTest mysqlTest = new MysqlTest();

    @FXML
    private VBox pnItems = null;

    @FXML
    private TableView<Result> table;

    @FXML
    private TableColumn<Result, String> SampleBarcode;

    @FXML
    private TableColumn<Result, String> Location;

    @FXML
    private TableColumn<Result, String> Selected;

    @FXML
    private TableColumn<Result, String> ALPSIGA;

    @FXML
    private TableColumn<Result, String> ALPSIGG_IGM;
    @FXML
    private TableView tableview;
    private ObservableList<ObservableList> data;

    @FXML
    void  approvePlateID(ActionEvent actionEvent) throws SQLException {
        System.out.println("click -------");
        String pillarPlateID = "FDAD80030001000020";
        System.out.println("====================================");
        //Given pillarPlateID to get testType
        String testType = mysqlTest.getTestType(pillarPlateID);
        System.out.println("-------testType----->>>>>>>" + testType);

        //Given testType to get QC_Names
//        FROM tsp_test_qc_data.plate_qc_mapping
        Set<String> QCNames = mysqlTest.getQCNames(testType);
        System.out.println("-------QCNames----->>>>>>>" + QCNames);
        //Given pillarPlateID to get testNames
        //FROM tsp_test_qc_data.qc_data_v2
        System.out.println("-------3----->>>>>>>" + mysqlTest.getTestNames(pillarPlateID));
        Set<String> testNames = mysqlTest.getTestNames(pillarPlateID);
        List<QCData> QCDataList = new ArrayList<>();
        List<String> colsName = getColsName(QCNames);
        System.out.println("-----=====colsName=====-------" + colsName);
        Map<String, String> QCNameAndValue = new HashMap<>();
        ObservableList<ObservableList> vals = FXCollections.observableArrayList();

        for (String testName : testNames) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(testName);
            for (String QCName : QCNames) {
//                //Given pillarPlateID, testName, QCName to get value.
//                QCData qcData = new QCData();
//                qcData.setPillarPlateID(pillarPlateID);
//                qcData.setTestName(testName);
//                qcData.setQcName(QCName);
//                String value = mysqlTest.getQCValue(pillarPlateID, testName, QCName);
//                qcData.setValue(value);
//                QCDataList.add(qcData);
                System.out.println("====getQCNameAndValue==========");
                String QCValue = mysqlTest.getQCValue(pillarPlateID, testName, QCName);
                if (QCValue == null) {
                    QCValue = "Missing Value";
                }
                row.add(QCValue);
            }
            vals.add(row);
        }

        System.out.println("<<<<<<<<<<>>>>>>>>>>>>>>>>>" + mysqlTest.getQCValue(pillarPlateID, "FOOA", "Cal2"));

        System.out.println("=====vals=====" + vals);
        System.out.println("=====vals=====" + vals.size());

        System.out.println("data----->" + QCDataList.size());


        try {
//            for (int i = 0; i < mysqlConnect.getColsNum(); i++) {
//                TableColumn col = new TableColumn(mysqlConnect.getColsName(i));
//                System.out.println(col);
//                final int j = i;
//                System.out.println("j-----j" + j);
//                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
//                tableview.getColumns().add(col);
//            }

            for (int i = 0; i < colsName.size(); i++) {
                TableColumn col = new TableColumn(colsName.get(i));
                System.out.println(col);
                final int j = i;
                System.out.println("j-----j" + j);
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tableview.getColumns().add(col);
            }




            ObservableList<ObservableList> data = mysqlConnect.getColsVal();
//            System.out.println("data--------------" + data);
//            for(ObservableList ob : data) {
//                System.out.println("---2---" + ob);
//            }
            tableview.setItems(vals);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<String> getColsName(Set<String> qcNames) {
        List<String> colsName = new ArrayList<>();
        colsName.add("TestName");
        for (String qcName : qcNames) {
            colsName.add(qcName);
        }
        return colsName;
    }

    ObservableList<Result> observableList;
    int index = -1;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
//    ObservableList<Result> list = FXCollections.observableArrayList(
//            new Result("1","A1","X","388","NO RESULT"),
//            new Result("2","A1","X","388","NO RESULT"),
//            new Result("3","A1","X","388","NO RESULT"),
//            new Result("4","A1","X","388","NO RESULT"),
//            new Result("5","A1","X","388","NO RESULT")
//    );







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SampleBarcode.setCellValueFactory(new PropertyValueFactory<Result, String>("SampleBarcode"));
        Location.setCellValueFactory(new PropertyValueFactory<Result, String>("Location"));
        Selected.setCellValueFactory(new PropertyValueFactory<Result, String>("Selected"));
        ALPSIGA.setCellValueFactory(new PropertyValueFactory<Result, String>("ALPSIGA"));
        ALPSIGG_IGM.setCellValueFactory(new PropertyValueFactory<Result, String>("ALPSIGG_IGM"));

//        connection = mysqlConnect.connectDb();
        try {
            observableList = mysqlConnect.getResult();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table.setItems(observableList);

////>>>>>>>>>>>>>>>>>>>>> start from here
//        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM vibrant_test_tracking.pillar_plate_info", PConstants.TSP_SERVER)) {
//            System.out.println(dbUtility);
//            dbUtility.generateRecords_throwException();
////            dbUtility.changeSQL(sql);
//            try (ResultSet set = dbUtility.getRecords()) {
//                while (set.next()) {
//                    System.out.println(set); // each row
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }





//        try {
//            for (int i = 0; i < mysqlConnect.getColsNum(); i++) {
//                TableColumn col = new TableColumn(mysqlConnect.getColsName(i));
//                final int j = i;
//                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
//                tableview.getColumns().add(col);
//            }
//            ObservableList<ObservableList> data = mysqlConnect.getColsVal();
//            System.out.println("data" + data);
//            for(ObservableList ob : data) {
//                System.out.println("---2---" + ob);
//            }
//            tableview.setItems(data);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }


//
//
//        Node[] nodes = new Node[30];
//        for (int i = 0; i < nodes.length; i++) {
//            try {
//                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
//                pnItems.getChildren().add(nodes[i]);
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//        }
    }
}
