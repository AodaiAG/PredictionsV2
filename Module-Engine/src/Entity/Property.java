package Entity;

public class Property
{
    private String NameOfProperty;

    private boolean isRandomInitialize;

    private Data data;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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

    public String getTypeString(){
        return this.data.getDataType().getDataTypeString();
    }
}
