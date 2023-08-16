package DTOS;

import java.util.List;

public class EntityDTO
{
  private String name;

  private int numberOfInstances;

  private List<PropertyDTO> properties;

    public EntityDTO(String name, int numOfInstances, List<PropertyDTO> propDTO)
    {
      this.name = name;
      this.numberOfInstances = numOfInstances;
      this.properties = propDTO;
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