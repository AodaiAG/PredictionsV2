import java.io.File;

public interface IEngine
{
    public void ParseXmlAndLoadWorld( File file);
    public WorldDTO convertWorldToDTO();
}
