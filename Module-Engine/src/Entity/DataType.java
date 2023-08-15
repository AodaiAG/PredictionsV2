package Entity;

import java.util.Random;

public enum DataType
{
    DECIMAL {
        @Override
        public String getDataTypeString() {
            return "Decimal";
        }

        @Override
        public Boolean compareTo(String comparedto, String Operator,String dataString) throws Exception
        {

            try
            {
                Float ComparedTodata=Float.parseFloat(comparedto);
                Float mydata=Float.parseFloat(dataString);
                switch (Operator)
                {
                    case "=":
                    {
                        return mydata==ComparedTodata;

                    }
                    case "!=":
                    {
                        return mydata!=ComparedTodata;

                    }
                    case "bt":
                    {
                        return mydata>ComparedTodata;

                    }
                    case "lt":
                    {
                        return mydata<ComparedTodata;

                    }
                    default:throw new Exception("operator not legit");
                }

            }
            catch (Exception e)
            {
                throw new Exception("Comparison doesn't make sense");
            }
        }

        @Override
        public String multiply(String arg1, String arg2,String from,String to) throws Exception
        {
            try
            {
                Integer a1=Integer.parseInt(arg1);
                Integer a2=Integer.parseInt(arg2);
                Integer res=a1*a2;
                if(isInRange(res,from,to))
                {
                    return res.toString();
                }
                else
                {
                    throw new Exception("The result of this action is out of the predefined range");

                }

            }
            catch (Exception e)
            {

                throw e;
            }

        }

        @Override
        public String divide(String arg1, String arg2,String from,String to) throws Exception
        {

            try
            {
                Integer a1=Integer.parseInt(arg1);
                Integer a2=Integer.parseInt(arg2);
                if(a2==0)
                {
                    throw new Exception("You can't divide by 0 !");
                }
                else
                {
                    Integer res=a1/a2;
                    if(isInRange(res,from,to))
                    {
                       return res.toString();
                    }
                    else
                    {
                        throw new Exception("The result of this action is out of the predefined range");
                    }
                }


            }
            catch (Exception e)
            {

                throw e;
            }
        }

        @Override
        public String setNewValue(String value,String from,String to) throws Exception
        {
            try
            {
                Integer res=Integer.parseInt(value);
                if(isInRange(res,from,to))
                {
                    return res.toString();
                }
                else
                {
                    throw new Exception("value out of range");
                }
            }
            catch (Exception e)
            {
                throw new Exception("The data you entered is not of type Decimal ");
            }
        }

        @Override
        public String decrease(String value,String dataString,String from,String to) throws Exception
        {
            try
            {
                Integer fvalue=Integer.parseInt(value);
                Integer res=Integer.parseInt(dataString) - fvalue;
                Integer fromvalue=Integer.parseInt(from);
                Integer tovalue=Integer.parseInt(to);
                if(res>=fromvalue&&res<=tovalue)
                {
                   return res.toString();
                }
                else
                {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        public Boolean isInRange(Integer value,String from,String to)
        {
            Integer fromvalue = Integer.parseInt(from);
            Integer tovalue = Integer.parseInt(to);
            if (value >= fromvalue && value <= tovalue) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String increase(String value,String dataString,String from,String to) throws Exception
        {
            try
            {
                Integer fvalue=Integer.parseInt(value);
                Integer res=Integer.parseInt(dataString) + fvalue;

                if(isInRange(res,from,to))
                {
                    return res.toString();
                }
                else
                {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }


        @Override
        public String calculateNewVal(String initValue, Boolean isRandomInitialize,String from, String to)
        {

            int val;
            if (isRandomInitialize)
            {
                val =  r.nextInt(Integer.parseInt(to) - Integer.parseInt(from) + 1) + Integer.parseInt(from);


            } else
            {
                val = Integer.parseInt(initValue);
            }
            return Integer.toString(val);
        }
    },

    FLOAT {
        @Override
        public String getDataTypeString() {
            return "Float";
        }

        @Override
        public Boolean compareTo(String comparedto, String Operator,String dataString) throws Exception
        {
            try
            {
                Float ComparedTodata=Float.parseFloat(comparedto);
                Float mydata=Float.parseFloat(dataString);
                switch (Operator)
                {
                    case "=":
                    {
                        return mydata==ComparedTodata;

                    }
                    case "!=":
                    {
                        return mydata!=ComparedTodata;

                    }
                    case "Bt":
                    {
                        return mydata>ComparedTodata;

                    }
                    case "Lt":
                    {
                        return mydata<ComparedTodata;

                    }
                    default:throw new Exception("operator not legit");
                }

            }
            catch (Exception e)
            {
                throw new Exception("Comparison doesn't make sense");
            }


        }

        @Override
        public String multiply(String arg1,String arg2,String from,String to) throws Exception
        {
            try
            {
                Float a1=Float.parseFloat(arg1);
                Float a2=Float.parseFloat(arg2);
                Float res=a1*a2;
                if(isInRange(res,from,to))
                {
                    return res.toString();
                }
                else
                {
                    throw new Exception("The result of this action is out of the predefined range");

                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        @Override
        public String divide(String arg1,String arg2,String from,String to) throws Exception
        {
            try
            {
                Float a1=Float.parseFloat(arg1);
                Float a2=Float.parseFloat(arg2);
                if(a2==0)
                {
                    throw new Exception("You can't divide by 0 !");
                }
                else
                {
                    Float res=a1/a2;
                    if(isInRange(res,from,to))
                    {
                       return res.toString();
                    }
                    else
                    {
                        throw new Exception("The result of this action is out of the predefined range");
                    }
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        @Override
        public String setNewValue(String value,String from,String to) throws Exception
        {
            try
            {
                Float res=Float.parseFloat(value);
                if(isInRange(res,from,to))
                {
                    return res.toString();
                }
                else
                {
                    throw new Exception("Value out of range !");
                }
            }
            catch (Exception e)
            {
                throw new Exception("The data you entered is not of type Float ");
            }
        }

        @Override
        public String decrease(String value,String dataString,String from,String to) throws Exception
        {
            try
            {
                Float fvalue = Float.parseFloat(value);
                Float res = Float.parseFloat(dataString) - fvalue;
                Float fromvalue = Float.parseFloat(from);
                Float tovalue = Float.parseFloat(to);
                if(res >= fromvalue && res <= tovalue)
                {
                    return res.toString();
                }
                else
                {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            }
            catch (Exception e)
            {
                throw e;
            }



        }

        public Boolean isInRange(Float value,String from,String to)
        {
            Float fromvalue=Float.parseFloat(from);
            Float tovalue=Float.parseFloat(to);
            if(value>=fromvalue&&value<=tovalue)
            {
                return true;
            }
            else
            {
                return false;
            }


        }
        @Override
        public String increase(String value,String dataString,String from,String to) throws Exception
        {
            try
            {
                Float fvalue=Float.parseFloat(value);
                Float res=Float.parseFloat(dataString) + fvalue;

                if(isInRange(res,from,to))
                {
                    return res.toString();
                }
                else
                {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            }
            catch (Exception e)
            {
                throw e;
            }



        }

        @Override
        public String calculateNewVal(String initValue, Boolean isRandomInitialize,String from, String to)
        {

            float val;
            if (isRandomInitialize) {
                val = Float.parseFloat(from) + r.nextFloat() * (Float.parseFloat(to) - Float.parseFloat(from));
            } else {
                val = Float.parseFloat(initValue);
            }
            return Float.toString(val);
        }
    },

    BOOLEAN {
        @Override
        public String getDataTypeString() {
            return "Boolean";
        }

        @Override
        public Boolean compareTo(String comparedto, String Operator,String dataString) throws Exception
        {
            try{
                Boolean compTo=Boolean.parseBoolean(comparedto) ;
                switch (Operator)
                {
                    case "=":
                    {
                        return Boolean.parseBoolean(dataString)==Boolean.parseBoolean(comparedto);

                    }
                    case "!=":
                    {
                        return Boolean.parseBoolean(dataString)!=Boolean.parseBoolean(comparedto);

                    }
                    case "Bt":
                    {
                        throw new Exception("Comparator not legit in the context of Boolean");

                    }
                    case "Lt":
                    {
                        throw new Exception("Comparator not legit in the context of Boolean");

                    }
                    default:throw new Exception("operator not legit");
                }

            }
            catch (Exception e)
            {
                throw new Exception("comparison doesn't make sense");
            }



        }

        @Override
        public String multiply(String arg1,String arg2,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");
        }

        @Override
        public String divide(String arg1,String arg2,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");

        }

        @Override
        public String setNewValue(String value,String from,String to) throws Exception
        {
                if((value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")))
                {
                    Boolean res=Boolean.valueOf(value);
                    return Boolean.toString(res);
                }

                else
                {
                    throw new Exception("The data you entered is not of type Boolean ");
                }
        }


        @Override
        public String decrease(String value,String dataString,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");
        }

        @Override
        public String increase(String value,String dataString,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");

        }


        @Override
        public String calculateNewVal(String initValue, Boolean isRandomInitialize,String from, String to)
        {

            {
                boolean val;
                if (isRandomInitialize) {
                    val = r.nextBoolean();
                } else
                {
                    val = Boolean.parseBoolean(initValue);
                }
                return Boolean.toString(val);
            }
        }
    },

    STRING {
        @Override
        public String getDataTypeString()
        {
            return "String";
        }

        @Override
        public Boolean compareTo(String comparedto, String Operator,String dataString) throws Exception
        {

            if (comparedto.getClass().getSimpleName()!=this.getClass().getSimpleName())
            {
                throw new Exception("can't compare"+this.getClass().getSimpleName()+"to"+comparedto.getClass().getSimpleName() );
            }

            else
            {
                switch (Operator)
                {
                    case "=":
                    {
                        return dataString==comparedto;

                    }
                    case "!=":
                    {
                        return dataString!=comparedto;

                    }
                    case "Bt":
                    {
                        int res=dataString.compareTo(comparedto);
                        if(res==0)
                        {
                            return false;
                        }
                        if(res>0)
                        {
                            return true;
                        }
                        if(res<0)
                        {
                            return false;
                        }


                    }
                    case "Lt":
                    {
                        int res=dataString.compareTo(comparedto);
                        if(res==0)
                        {
                            return false;
                        }
                        if(res>0)
                        {
                            return false;
                        }
                        if(res<0)
                        {
                            return true;
                        }

                    }
                    default:throw new Exception("operator not legit");
                }
            }
        }

        @Override
        public String multiply(String arg1,String arg2,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type string");
        }

        @Override
        public String divide(String arg1,String arg2,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type string");

        }

        @Override
        public String setNewValue(String value,String from,String to) throws Exception
        {
            return value;
        }

        @Override
        public  String decrease(String value,String dataString,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type string");

        }
        @Override
        public  String increase(String value,String dataString,String from,String to) throws Exception
        {
            throw new Exception("Can't apply this action to type string");

        }

        @Override
        public String  calculateNewVal(String initValue, Boolean isRandomInitialize,String from, String to)
        {
            String val;
            if (isRandomInitialize) {
                int targetStringLength = r.nextInt(50) + 1;
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,_-.() ";
                Random random = new Random();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < targetStringLength; i++) {
                    int randomIndex = random.nextInt(characters.length());
                    char randomChar = characters.charAt(randomIndex);
                    stringBuilder.append(randomChar);
                }
                val = stringBuilder.toString();
            } else {
                val = initValue;
            }
            return val;
        }
    };

    final Random r = new Random();

    public abstract String calculateNewVal(String initValue, Boolean isRandomInitialize,String from, String to);
    public abstract String decrease(String value,String dataString,String from,String to) throws Exception;
    public abstract String increase(String value,String dataString,String from,String to)  throws Exception;
    public abstract String setNewValue(String value,String from,String to)throws Exception;
    public abstract String multiply(String arg1,String arg2,String from,String to)  throws Exception;
    public abstract String divide(String arg1,String arg2,String from,String to)  throws Exception;
    public abstract Boolean compareTo(String comparedto,String Operator,String dataString) throws Exception;
    public abstract String getDataTypeString();
}