package DTOS;

import java.util.List;

public class EntityInstancesDTO
{
    private List<PropertyDTO> properties;
    String name;


    public EntityInstancesDTO(List<PropertyDTO> properties, String name) {
        this.properties = properties;
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public List<PropertyDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDTO> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}