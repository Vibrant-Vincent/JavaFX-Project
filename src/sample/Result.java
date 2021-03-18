package sample;

public class Result {
    String sampleBarcode;
    String location;
    String selected;
    String ALPSIGA;
    String ALPSIGG_IGM;

    public Result(String sampleBarcode, String location, String selected, String ALPSIGA, String ALPSIGG_IGM) {
        this.sampleBarcode = sampleBarcode;
        this.location = location;
        this.selected = selected;
        this.ALPSIGA = ALPSIGA;
        this.ALPSIGG_IGM = ALPSIGG_IGM;
    }

    public String getSampleBarcode() {
        return sampleBarcode;
    }

    public void setSampleBarcode(String sampleBarcode) {
        this.sampleBarcode = sampleBarcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getALPSIGA() {
        return ALPSIGA;
    }

    public void setALPSIGA(String ALPSIGA) {
        this.ALPSIGA = ALPSIGA;
    }

    public String getALPSIGG_IGM() {
        return ALPSIGG_IGM;
    }

    public void setALPSIGG_IGM(String ALPSIGG_IGM) {
        this.ALPSIGG_IGM = ALPSIGG_IGM;
    }
}
