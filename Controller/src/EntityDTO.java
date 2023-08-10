import Entity.Entity;
import java.util.HashSet;
import java.util.Set;
import Entity.Property;

public class EntityDTO
{

  private String name;

  private int numberOfInstances;

  private Set<PropertiesDTO> properties;

    public EntityDTO(Entity entity)
    {
      this.name = entity.getNameOfEntity();
      this.numberOfInstances = entity.getNumberOfInstances();
      this.properties = new HashSet<>();
    }

}

