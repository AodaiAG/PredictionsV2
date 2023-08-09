import Entity.Entity;
import java.util.HashSet;
import java.util.Set;
import Entity.Properties;

public class EntityDTO {

  private String name;

  private int numberOfInstances;

  private Set<PropertiesDTO> properties;

  public EntityDTO(Entity entity) {
    this.name = entity.getNameOfEntity();
    this.numberOfInstances = entity.getNumberOfInstances();
    this.properties = new HashSet<>();
//    for (eProperty property : entity.getPropertiesOfTheEntity()) {
//      PropertiesDTO propertyDTO = new PropertiesDTO(property);
//      this.properties.add(propertyDTO);
    }
    }
  }