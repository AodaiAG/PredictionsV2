package Entity;

import java.util.Random;

public class Data
{
    private DataType dataType;

    private String dataString;

    private boolean isRangeExist;

    public boolean isRangeExist() {
        return isRangeExist;
    }

    public void setRangeExist(boolean rangeExist) {
        isRangeExist = rangeExist;
    }

    public String from;

    public String to;

    final Random r = new Random();

    public  void calculateNewVal(String initVal, Boolean isRandomInitialize)
    {
        dataString = dataType.calculateNewVal(initVal,isRandomInitialize,from,to);
    }

    public void decrease(String value) throws Exception
    {
        try
        {
           dataString= dataType.decrease(value,dataString,from,to);
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public void increase(String value) throws Exception
    {
        try
        {
            dataString= dataType.increase(value,dataString,from,to);
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
            dataString= dataType.setNewValue(value,from,to);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public void multiply(String arg1,String arg2) throws Exception
    {
        try
        {
            dataString = dataType.multiply(arg1,arg2,from,to);
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
            dataString= dataType.divide(arg1,arg2,from,to);
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
            return dataType.compareTo(comparedto,Operator,dataString);
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType)
    {
        this.dataType = dataType;
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

