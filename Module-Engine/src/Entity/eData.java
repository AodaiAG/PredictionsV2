package Entity;

import java.util.Random;

public enum eData
{

    DECIMAL {
        @Override
        public Boolean compareTo(String comparedto, String Operator) throws Exception
        {
            try
            {
                Float ComparedTodata=Float.parseFloat(comparedto);
                Float mydata=Float.parseFloat(this.dataString);
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
        public Integer getData() {
            return Integer.parseInt(dataString);
        }

        @Override
        public void multiply(String arg1, String arg2) throws Exception
        {
            try
            {
                Integer a1=Integer.parseInt(arg1);
                Integer a2=Integer.parseInt(arg2);
                Integer res=a1*a2;
                if(isInRange(res))
                {
                    this.dataString=res.toString();
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
        public void divide(String arg1, String arg2) throws Exception
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
                    if(isInRange(res))
                    {
                        this.dataString=res.toString();
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
        public void setNewValue(String value) throws Exception
        {
            try
            {
                Integer res=Integer.parseInt(value);
                this.dataString=res.toString();

            }
            catch (Exception e)
            {
                throw e;
            }

        }

        @Override
        public void decrease(String value) throws Exception
        {
          try
          {
              Integer fvalue=Integer.parseInt(value);
              Integer res=Integer.parseInt(dataString) - fvalue;
              Integer fromvalue=Integer.parseInt(from);
              Integer tovalue=Integer.parseInt(to);
              if(res>=fromvalue&&res<=tovalue)
              {
                  this.dataString=res.toString();
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
        public Boolean isInRange(Integer value)
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
        public void increase(String value) throws Exception
        {
            try
            {
                Integer fvalue=Integer.parseInt(value);
                Integer res=Integer.parseInt(dataString) + fvalue;

                if(isInRange(res))
                {
                    this.dataString=res.toString();
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
        public void calculateNewVal(String initValue, Boolean isRandomInitialize)
        {
            int val;
            if (isRandomInitialize)
            {
                val =Integer.parseInt(from) + (int)(Math.random() * Integer.parseInt(to) );
            } else {
                val = Integer.parseInt(initValue);
            }
            dataString = Integer.toString(val);
        }
    },

    FLOAT {
        @Override
        public Boolean compareTo(String comparedto, String Operator) throws Exception
        {


            try
            {
                Float ComparedTodata=Float.parseFloat(comparedto);
                Float mydata=Float.parseFloat(this.dataString);
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
        public void multiply(String arg1,String arg2) throws Exception
        {
            try
            {
                Float a1=Float.parseFloat(arg1);
                Float a2=Float.parseFloat(arg2);
                Float res=a1*a2;
                if(isInRange(res))
                {
                    this.dataString=res.toString();
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
        public void divide(String arg1,String arg2) throws Exception
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
                    if(isInRange(res))
                    {
                        this.dataString=res.toString();
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
        public Float getData() {
            return Float.parseFloat(dataString);
        }

        @Override
        public void setNewValue(String value) throws Exception
        {
            try
            {
                Float res=Float.parseFloat(value);
                this.dataString=res.toString();
            }
            catch (Exception e)
            {
                throw e;
            }

        }

        @Override
        public void decrease(String value) throws Exception
        {

            try
            {
                Float fvalue=Float.parseFloat(value);
                Float res=Float.parseFloat(dataString) - fvalue;
                Float fromvalue=Float.parseFloat(from);
                Float tovalue=Float.parseFloat(to);
                if(res>=fromvalue&&res<=tovalue)
                {
                    this.dataString=res.toString();
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

        public Boolean isInRange(Float value)
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
        public void increase(String value) throws Exception
        {
            try
            {
                Float fvalue=Float.parseFloat(value);
                Float res=Float.parseFloat(dataString) + fvalue;

                if(isInRange(res))
                {
                    this.dataString=res.toString();
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
        public void calculateNewVal(String initValue, Boolean isRandomInitialize) {

            float val;
            if (isRandomInitialize) {
                val = Float.parseFloat(getFrom()) + r.nextFloat() * (Float.parseFloat(getTo()) - Float.parseFloat(getFrom()));
            } else {
                val = Float.parseFloat(initValue);
            }
            dataString = Float.toString(val);
        }
    },

    BOOLEAN {
        @Override
        public Boolean compareTo(String comparedto, String Operator) throws Exception
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
        public void multiply(String arg1,String arg2) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");
        }

        @Override
        public void divide(String arg1,String arg2) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");

        }

        @Override
        public void setNewValue(String value) throws Exception
        {
            try
            {
                Boolean res=Boolean.valueOf(value);
                this.dataString=Boolean.toString(res);
            }
            catch (Exception e)
            {
                throw  e;
            }


        }

        @Override
        public Boolean getData() {
            return (dataString.equals("true"));
        }

        @Override
        public void decrease(String value) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");
        }

        @Override
        public void increase(String value) throws Exception
        {
            throw new Exception("Can't apply this action to type boolean");

        }


        @Override
        public void calculateNewVal(String initValue, Boolean isRandomInitialize) {
            {
                boolean val;
                if (isRandomInitialize) {
                    val = r.nextBoolean();
                } else {
                    val = Boolean.parseBoolean(initValue);
                }
                dataString = Boolean.toString(val);
            }
        }
    },

        STRING {
            @Override
            public Boolean compareTo(String comparedto, String Operator) throws Exception
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
                            return this.dataString==comparedto;

                        }
                        case "!=":
                        {
                            return this.dataString!=comparedto;

                        }
                        case "Bt":
                        {
                            int res=this.dataString.compareTo(comparedto);
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
                            int res=this.dataString.compareTo(comparedto);
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
            public void multiply(String arg1,String arg2)  throws Exception
            {
                throw new Exception("Can't apply this action to type string");
            }

            @Override
            public void divide(String arg1,String arg2) throws Exception
            {
                throw new Exception("Can't apply this action to type string");

            }

            @Override
            public void setNewValue(String value) throws Exception {
                this.dataString=value;
            }

            @Override
            public String getData () {
                return dataString;
            }
            public  void decrease(String value) throws Exception {
                throw new Exception("Can't apply this action to type string");

            }
            public  void increase(String value) throws Exception {
                throw new Exception("Can't apply this action to type string");

            }

            @Override
            public void calculateNewVal (String initValue, Boolean isRandomInitialize)
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
                dataString = val;
            }
        };

        final Random r = new Random();

        public String from;

        public String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public String dataString;

        public void setDataString(String dataString) {
            this.dataString = dataString;
        }

        public String getDataString() {
            return dataString;
        }

        public abstract void calculateNewVal(String initVal, Boolean isRandomInitialize);

        public abstract Object getData();
        public abstract void decrease(String value) throws Exception;
        public abstract void increase(String value) throws Exception;
        public abstract void setNewValue(String value)throws Exception;
    public abstract void multiply(String arg1,String arg2) throws Exception;
    public abstract void divide(String arg1,String arg2) throws Exception;
    public abstract Boolean compareTo(String comparedto,String Operator) throws Exception;

    }

