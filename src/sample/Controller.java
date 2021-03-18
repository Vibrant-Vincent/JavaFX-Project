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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
//    public void pressButton(ActionEvent event) {
//        System.out.println("Hello world!");
//    }

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


    ObservableList<Result> observableList;
    int index = -1;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    MysqlConnect mysqlConnect = new MysqlConnect();
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

        connection = mysqlConnect.connectDb();
        observableList = mysqlConnect.getResult();
        table.setItems(observableList);


        try {
            for (int i = 0; i < mysqlConnect.getColsNum(); i++) {
                TableColumn col = new TableColumn(mysqlConnect.getColsName(i));
                final int j = i;
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableview.getColumns().add(col);
            }
            ObservableList<ObservableList> data = mysqlConnect.getColsVal();
            System.out.println("data" + data);
            for(ObservableList ob : data) {
                System.out.println("---2---" + ob);
            }
            tableview.setItems(data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


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
