import Entity.eData;

public class PropertiesDTO{
    private final String NameOfProperty;
    private final boolean randomInitialize;


    public PropertiesDTO(eData property) {
        this.NameOfProperty = property.getNameOfProperty();
        this.randomInitialize = property.isRandomInitialize();
    }

    public String getNameOfProperty() {
        return NameOfProperty;
    }

    public boolean isRandomInitialize() {
        return randomInitialize;
    }

}