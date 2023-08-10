public class PropertyDTO {
    private final String NameOfProperty;
    private final boolean randomInitialize;

    private final String nameOfDataType;
    private final String from;
    private final String to;

    public PropertyDTO(String nameOfProperty, Boolean randomInitialize, String nameOfDataType, String from, String to)
    {
        this.NameOfProperty = nameOfProperty;
        this.randomInitialize = randomInitialize;
        this.nameOfDataType = nameOfDataType;
        this.from = from;
        this.to = to;
    }

    public String getNameOfProperty() {
        return NameOfProperty;
    }

    public boolean isRandomInitialize() {
        return randomInitialize;
    }

}