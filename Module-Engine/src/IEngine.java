import Entity.Entity;
import Entity.Property;
import java.io.File;
import Entity.Entity;
import Rules.Rules;

public interface IEngine
{
    public void ParseXmlAndLoadWorld( File file);
    public World getWorld();
    public EntityDTO convertEntityToDTO(Entity entity);
    public PropertyDTO convertPropertyToDTO(Property property);
    public RulesDTO convertRuleToDTO(Rules rule);
}
