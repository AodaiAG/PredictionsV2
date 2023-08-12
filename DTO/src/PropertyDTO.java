public class PropertyDTO
{
    private final String NameOfProperty;

    private final boolean randomInitialize;

    private final String nameOfDataType;

    private final boolean isRange;

    private final String from;

    private final String to;

    private String dataString;

    public PropertyDTO(String nameOfProperty, Boolean randomInitialize, String nameOfDataType, String from, String to, String dataString, boolean isRange)
    {
        this.NameOfProperty = nameOfProperty;
        this.randomInitialize = randomInitialize;
        this.nameOfDataType = nameOfDataType;
        this.isRange = isRange;
        this.from = from;
        this.to = to;
    }

    public String getNameOfDataType() {
        return nameOfDataType;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public String getNameOfProperty() {

        return NameOfProperty;
    }

    public boolean isRandomInitialize() {

        return randomInitialize;
    }

    public String getNameOfDataType() {
        return nameOfDataType;
    }

    public boolean isRange() {
        return isRange;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
}