package Environment;
import java.util.HashSet;
import java.util.Set;
import Entity.Property;
import Entity.eData;

public class EnvironmentInstance
{
    public Property getEnvironmentProperty() {
        return EnvironmentProperty;
    }

    public void setEnvironmentProperty(Property EnvironmentProperty) {
        this.EnvironmentProperty = EnvironmentProperty;
    }

    private Property EnvironmentProperty=new Property();

}