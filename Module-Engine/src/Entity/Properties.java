package Entity;

import java.lang.reflect.Array;
import java.util.Random;



public class Properties<T>
{
    private String NameOfProperty;
    private boolean randomInitialize;
   private  T Type; // data
   public  T[] range; // range[0] - from , range[1] - to


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



      NameOfProperty= new String();
        Object ob = new Object();
        range=(T[]) new Object[2];


    }

    public Properties setPropertiesAcorrdingToRandomInit(Properties p,String typeOfField,String isRandom,String initValue)
    {

        Random r=new Random();


            switch (typeOfField)
            {
                case "decimal":
                    if(isRandom.equals("true"))
                    {
                        p.Type= (Integer)p.range[1] * r.nextInt() +(Integer)range[0];
                        return p;
                    }
                    else {
                        p.Type=Integer.parseInt(initValue);
                        return p;
                    }

                case "float":
                    if(isRandom.equals("true"))
                    {
                        p.Type=((Float)p.range[1]*r.nextFloat()+(Float)range[0]);
                        return p;
                    }
                    else
                    {
                        p.Type=Float.parseFloat(initValue);
                        return p;
                    }


                case "boolean":
                {
                    if(isRandom.equals("true"))
                    {
                        p.Type=r.nextBoolean();
                        return p;
                    }
                    else
                    {
                        p.Type=Boolean.parseBoolean(initValue);
                    }
                }

                case "string":
                {
                    if(isRandom.equals("true"))
                    {
                        // remmber to generate string including those of another chars
                        int leftLimit = 97; // letter 'a'
                        int rightLimit = 122; // letter 'z'
                        int targetStringLength =  (int)(Math.random()*50+1);
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
                    else
                    {
                        p.Type=initValue;
                        return p;
                    }
                }

            }



return p;
    }
}
