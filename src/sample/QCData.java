package sample;

import java.util.Map;

public class QCData {
    private String pillarPlateID;
    private String testName;
    private String qcName;

    public String getPillarPlateID() {
        return pillarPlateID;
    }

    public void setPillarPlateID(String pillarPlateID) {
        this.pillarPlateID = pillarPlateID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getQcName() {
        return qcName;
    }

    public void setQcName(String qcName) {
        this.qcName = qcName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

}
