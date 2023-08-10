import Entity.Entity;

import java.io.File;
import Entity.Entity;


public interface IEngine
{
    public void ParseXmlAndLoadWorld( File file);
    public World getWorld();
    public EntityDTO convertEntityToDTO(Entity entity);




}
