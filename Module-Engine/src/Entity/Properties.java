package Entity;

import java.util.Random;



public class Properties<T>
{
    private String NameOfProperty;
    private boolean randomInitialize;
   private  T Type;
   public  int[] range; // range[0] - from , range[1] - to


    public String getNameOfProperty() {
        return NameOfProperty;
    }

    public void setNameOfProperty(String nameOfProperty) {
        NameOfProperty = nameOfProperty;
    }

    public boolean isRandomInitialize() {
        return randomInitialize;
    }

    public void setRandomInitialize(boolean randomInitialize) {
        this.randomInitialize = randomInitialize;
    }

    public T getType() {
        return Type;
    }

    public void setType(T type) {
        Type = type;
    }





    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Properties()
    {
      range=new int[2];
      NameOfProperty= new String();


    }

    public Properties setPropertiesAcorrdingToRandomInit(Properties p,String typeOfField,String isRandom,int initValue)
    {

        Random r=new Random();
        if(isRandom.equals("true"))
        {
            switch (typeOfField)
            {
                case "decimal":
                   p.Type=(int) Math.random()*p.range[1]+range[0];
                   return p;
                case "float":
                    p.Type= Math.random()*p.range[1]+range[0];
                    return p;

                case "boolean":
                    p.Type=r.nextBoolean();
                    return p;

                case "string":
                {
                    int leftLimit = 97; // letter 'a'
                    int rightLimit = 122; // letter 'z'
                    int targetStringLength = 50;
                    Random random = new Random();
                    StringBuilder buffer = new StringBuilder(targetStringLength);
                    for (int i = 0; i < targetStringLength; i++) {
                        int randomLimitedInt = leftLimit + (int)
                                (random.nextFloat() * (rightLimit - leftLimit + 1));
                        buffer.append((char) randomLimitedInt);
                    }
                    String generatedString = buffer.toString();
                    p.Type=generatedString;
                    return p;
                }

            }
        }
        else
        {
           p.Type=(initValue);
           return p;
        }

return p;
    }
}
