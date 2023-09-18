package pEntity;

public class Data
{
    private DataType dataType;
    private String dataString;
    public String from;

    public String to;
    private boolean isRangeExist;

    @Override
    public Data clone() {
        Data res = new Data();
        res.setDataType(this.dataType);
        res.setDataString(this.dataString);
        res.setFrom(this.from);
        res.setTo(this.to);
        res.setRangeExist(this.isRangeExist);
        return res;
    }


    public boolean isRangeExist() {
        return isRangeExist;
    }

    public void setRangeExist(boolean rangeExist) {
        isRangeExist = rangeExist;
    }

    public void calculateNewVal(String initVal, Boolean isRandomInitialize) {
        dataString = dataType.calculateNewVal(initVal, isRandomInitialize, from, to);
    }

    public boolean decrease(String value) throws Exception {
        try {
            return this.setNewValue(dataType.decrease(value, dataString, from, to));
        } catch (Exception e) {
        }
        return false;
    }

    public boolean increase(String value) throws Exception {
        try {
            return this.setNewValue(dataType.increase(value, dataString, from, to));
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean setNewValue(String value) throws Exception {
        try {
            String dS = dataType.setNewValue(value, from, to);
            if(dS != dataString)
            {
                dataString = dS;
                return true;
            }
            dataString = dS;
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean multiply(String arg1, String arg2) throws Exception {
        try {
           return this.setNewValue(dataType.multiply(arg1, arg2, from, to));
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean divide(String arg1, String arg2) throws Exception {
        try {
            return this.setNewValue(dataType.divide(arg1, arg2, from, to));
        } catch (Exception e) {

        }
        return false;
    }

    public Boolean compareTo(String comparedTo, String Operator) throws Exception {
        try {
            return dataType.compareTo(comparedTo, Operator, dataString);
        } catch (Exception e) {
            throw e;
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
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

