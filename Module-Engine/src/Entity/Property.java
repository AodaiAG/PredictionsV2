package Entity;

public class Property
{

    private String NameOfProperty;

    private boolean isRandomInitialize;

    private Data e_data;



    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Data getE_data() {
        return e_data;
    }

    public void setE_data(Data e_data)
    {
        this.e_data = e_data;
    }

    public Data getEdata() {
        return e_data;
    }

    public void setData(Data data) {
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



}
