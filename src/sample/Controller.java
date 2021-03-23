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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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


    @FXML
    void  clearContents(ActionEvent actionEvent) throws SQLException {
        tableview.getColumns().clear();
    }
    @FXML
    void  approvePlateID(ActionEvent actionEvent) throws SQLException {

        String pillarPlateID = "FDAD80030001000020";
        tableview.getColumns().clear();
        fetchAndRenderData(pillarPlateID);
    }
    private void fetchAndRenderData(String pillarPlateID) throws SQLException {
        ObservableList<ObservableList> vals = FXCollections.observableArrayList();
        System.out.println("------0----->" + vals);
        /*
         *Given pillarPlateID to get testType
         *FROM vibrant_test_tracking.pillar_plate_info
         */
        String testType = mysqlTest.getTestType(pillarPlateID);
        System.out.println("----1 testType-----" + testType);
        /*
         * Given testType to get QC_Names
         * FROM tsp_test_qc_data.plate_qc_mapping
         * */
        Set<String> QCNamesSet = mysqlTest.getQCNames(testType);
        // Sorting HashSet using List
        List<String> QCNames = new ArrayList<String>(QCNamesSet);
        Collections.sort(QCNames);
        System.out.println("----2 QCNames-----" + QCNames);

        /*
         *Given pillarPlateID to get testNames
         * FROM tsp_test_qc_data.qc_data_v2
         * */
        Set<String> testNamesSet = mysqlTest.getTestNames(pillarPlateID);
        // Sorting HashSet using List
        List<String> testNames = new ArrayList<String>(testNamesSet);
        Collections.sort(testNames);
        System.out.println("----3 testNames-----" + testNames);

//        List<QCData> QCDataList = new ArrayList<>();
        List<String> colsName = getTableColsName(QCNamesSet);
        System.out.println("----8 colsName-----" + colsName);

//        Map<String, String> QCNameAndValue = new HashMap<>();
        for (String testName : testNames) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(testName);
            for (String QCName : QCNames) {
                String QCValue = mysqlTest.getQCValue(pillarPlateID, testName, QCName);
                System.out.println("-------7------- " + testName + " " +  QCName + " " + QCValue);

                if (QCValue == null || QCValue.length() == 0) {
                    QCValue = "Missing Value";
                }
                System.out.println("-------6-------" + QCValue);
                row.add(QCValue);
            }
            System.out.println("-------5-------" + row);
            vals.add(row);
        }
        renderData(colsName, vals);
    }

    private void renderData(List<String> colsName, ObservableList<ObservableList> vals) throws SQLException {
        try {
            for (int i = 0; i < colsName.size(); i++) {
                TableColumn col = new TableColumn(colsName.get(i));
                System.out.println(col);
                final int j = i;
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
// Set the cell factory of the column with a custom TableCell to modify its behavior.
                col.setCellFactory(e -> new TableCell<ObservableList<String>, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        // Always invoke super constructor.
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                            // If index is two we set the background color explicitly.
                            if (item == "Missing Value") {
                                this.setStyle("-fx-background-color: red;");
                            }
                        }
                    }
                });
                tableview.getColumns().add(col);
            }
            ObservableList<ObservableList> data = mysqlConnect.getColsVal();
            tableview.setItems(vals);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private LinkedList<String> getTableColsName(Set<String> qcNames) {
        LinkedList<String> colsName = new LinkedList<>();
        for (String qcName : qcNames) {
            colsName.add(qcName);
        }
        Collections.sort(colsName);
        colsName.addFirst("TestName");
        System.out.println("--------4-------" + colsName);

        return colsName;
    }

    ObservableList<Result> observableList;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SampleBarcode.setCellValueFactory(new PropertyValueFactory<Result, String>("SampleBarcode"));
        Location.setCellValueFactory(new PropertyValueFactory<Result, String>("Location"));
        Selected.setCellValueFactory(new PropertyValueFactory<Result, String>("Selected"));
        ALPSIGA.setCellValueFactory(new PropertyValueFactory<Result, String>("ALPSIGA"));
        ALPSIGG_IGM.setCellValueFactory(new PropertyValueFactory<Result, String>("ALPSIGG_IGM"));

        try {
            observableList = mysqlConnect.getResult();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table.setItems(observableList);
    }
}
