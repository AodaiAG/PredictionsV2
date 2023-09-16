package pEntity;

public class Property
{
    private String NameOfProperty;

    private int lastUnchangedTicks;

    public int getSumTicksNoChange() {
        return sumTicksNoChange;
    }

    public int getNumOfTimesHasChanged() {
        return numOfTimesHasChanged;
    }
    public void addToNumberOfTimePropHasChanged()
    {
        numOfTimesHasChanged++;
    }

    public void addTicksToSum(int differenceBetweenTicks)
    {
        sumTicksNoChange += differenceBetweenTicks;
    }

    private int sumTicksNoChange = 0;

    public int numOfTimesHasChanged = 0;

    private boolean isRandomInitialize;

    private Data data;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Property clone() {
        Property res = new Property();
        res.setNameOfProperty(this.NameOfProperty);
        res.setRandomInitialize(isRandomInitialize);
        res.setData(this.data.clone());
        return res;
    }

    public int getLastUnchangedTicks() {
        return lastUnchangedTicks;
    }

    public void setLastUnchangedTicks(int lastUnchangedTicks) {
        this.lastUnchangedTicks = lastUnchangedTicks;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getNameOfProperty() {
        return NameOfProperty;
    }

    public void setNameOfProperty(String nameOfProperty) {
        NameOfProperty = nameOfProperty;
    }

    public boolean isRandomInitialize() {
        return isRandomInitialize;
    }

    public void setRandomInitialize(boolean randomInitialize) {
        isRandomInitialize = randomInitialize;
    }

    public String getTypeString() {
        return this.data.getDataType().getDataTypeString();
    }

    public void updateProperty(int currTick)
    {
        int timeNotChanged = currTick - getLastUnchangedTicks();
        addToNumberOfTimePropHasChanged(); //+1
        addTicksToSum(timeNotChanged);
        setLastUnchangedTicks(currTick);
    }
}