package sample;

import main.java.common.utils.DataBaseUtility;
import main.java.lis.constant.PConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) {
        try(DataBaseUtility dbUtility = new DataBaseUtility("SELECT * FROM tsp_test_qc_data.qc_data_v2", PConstants.TSP_SERVER)) {
            System.out.println(dbUtility);
//            String sql = "SELECT * FROM tsp_test_qc_data.qc_data_v2";
            dbUtility.generateRecords_throwException();
//            dbUtility.changeSQL(sql);
            try (ResultSet set = dbUtility.getRecords()) {
                while (set.next()) {
                    System.out.println(set); // each row
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
