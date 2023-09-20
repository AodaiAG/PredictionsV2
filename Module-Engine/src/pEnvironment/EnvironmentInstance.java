package pEnvironment;

import pEntity.Property;

public class EnvironmentInstance
{
    private Property EnvironmentProperty = new Property();

    public Property getEnvironmentProperty() {
        return EnvironmentProperty;
    }

    public void setEnvironmentProperty(Property EnvironmentProperty) {
        this.EnvironmentProperty = EnvironmentProperty;
    }
}