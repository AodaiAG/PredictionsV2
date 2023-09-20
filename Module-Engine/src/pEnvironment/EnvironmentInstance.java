package pEnvironment;

import pEntity.Property;

public class EnvironmentInstance
{
    private Property EnvironmentProperty = new Property();
   private boolean isInitByUser=false;
    public Property getEnvironmentProperty() {
        return EnvironmentProperty;
    }

    public boolean isInitByUser()
    {
        return isInitByUser;
    }

    public void randomlyInitEnvironmentData()
    {
       if(!isInitByUser)
       {
           String initVal = EnvironmentProperty.getData().getDataString();
           if (EnvironmentProperty.isRandomInitialize())
           {
               EnvironmentProperty.getData().calculateNewVal(initVal, true);
           }
       }

    }

    public void setInitByUser(boolean initByUser)
    {
        isInitByUser = initByUser;
    }

    public void setEnvironmentProperty(Property EnvironmentProperty) {
        this.EnvironmentProperty = EnvironmentProperty;
    }
}