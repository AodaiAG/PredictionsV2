package Entity;

import java.util.Random;

public class Data
{

    private DataType type;
    private String dataString;
    public String from;
    public String to;
    final Random r = new Random();
    public  void calculateNewVal(String initVal, Boolean isRandomInitialize)
    {
        dataString =type.calculateNewVal(initVal,isRandomInitialize,from,to);
    }

    public  void decrease(String value) throws Exception
    {

        try
        {
           dataString= type.decrease(value,dataString,from,to);
        }
        catch(Exception e)
        {
            throw e;
        }

    }
    public  void increase(String value) throws Exception
    {

        try
        {
            dataString= type.increase(value,dataString,from,to);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public  void setNewValue(String value)throws Exception
    {
        try
        {
            dataString= type.setNewValue(value,from,to);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public  void multiply(String arg1,String arg2) throws Exception
    {
        try
        {
            dataString= type.multiply(arg1,arg2,from,to);
        }
        catch(Exception e)
        {
            throw e;
        }

    }
    public void divide(String arg1,String arg2) throws Exception
    {
        try
        {
            dataString= type.divide(arg1,arg2,from,to);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public  Boolean compareTo(String comparedto,String Operator) throws Exception
    {
        try
        {
            return type.compareTo(comparedto,to,dataString);
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type)
    {
        this.type = type;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

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
}

