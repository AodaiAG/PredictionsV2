import java.util.Set;

public class EntityDTO
{
  private String name;

  private int numberOfInstances;

  private Set<PropertyDTO> properties;

    public EntityDTO(String name, int numOfInstances,Set<PropertyDTO> propDTO)
    {
      this.name = name;
      this.numberOfInstances = numOfInstances;
      this.properties = propDTO;
    }

}

