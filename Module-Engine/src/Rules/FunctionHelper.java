package Rules;

import Entity.Entity;
import Entity.Properties;

import java.lang.reflect.Field;
import java.util.Random;

public class FunctionHelper
{

    public static Object  environment(Properties e)
    {
        return e.Type;
    }

    public static Object RandomFun(Properties e,int upperbound)
    {

        Random r=new Random();
        try {
            Field resField=e.getClass().getField("Type");
            String typeOfField=resField.getType().getSimpleName();
            switch (typeOfField)

            {
                case "Integer":
                    return r.nextInt(upperbound);

                case "Float":
                    return  0 + (upperbound - 0) * r.nextFloat();

                case "Boolean":
                    return r.nextBoolean();

                case "String":
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
                    return generatedString;
                }
            }
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }



    return 5;
    }

   public static String getTypeOfEntity(Entity e)
    {
        Field resField= null;
        try {
            resField = e.getClass().getField("Type");
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
        String typeOfField=new String();
        typeOfField=resField.getType().getSimpleName();

        return typeOfField;

    }
}
