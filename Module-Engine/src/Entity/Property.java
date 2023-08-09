package Entity;

public class Property {

    private String NameOfProperty;

    private boolean isRandomInitialize;

    private eData e_data;

    private String from;

    private String to;


    public eData getEdata() {
        return e_data;
    }

    public void setData(eData data) {
        this.e_data = data;
    }

    public String getNameOfProperty() {
        return NameOfProperty;
    }

    public void setNameOfProperty(String nameOfProperty) {
        NameOfProperty = nameOfProperty;
    }

    public boolean isRandomInitialize() {
        return isRandomInitialize;
    }

    public void setRandomInitialize(boolean randomInitialize) {
        isRandomInitialize = randomInitialize;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


}
