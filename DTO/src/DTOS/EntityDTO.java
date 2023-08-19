package DTOS;

import java.util.List;

public class EntityDTO {
    private String name;

    private int numberOfInstances;
    private List<EntityInstancesDTO> instancesDTOS;
    private List<PropertyDTO> properties;

    public EntityDTO(String name, int numOfInstances, List<PropertyDTO> propDTO, List<EntityInstancesDTO> ldto) {
        this.name = name;
        this.numberOfInstances = numOfInstances;
        this.properties = propDTO;
        instancesDTOS = ldto;
    }

    public List<EntityInstancesDTO> getInstancesDTOS() {
        return instancesDTOS;
    }

    public void setInstancesDTOS(List<EntityInstancesDTO> instancesDTOS) {
        this.instancesDTOS = instancesDTOS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfInstances() {
        return numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }

    public List<PropertyDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDTO> properties) {
        this.properties = properties;
    }
}