package pDTOS;

import javafx.scene.control.TreeItem;

public class PropertyDTO
{
    private final String NameOfProperty;

    private final boolean randomInitialize;

    private final String nameOfDataType;

    private final boolean isRange;

    private final String from;

    private final String to;

    private String dataString;
    private int lastUnchangedTicks;


    public PropertyDTO(String nameOfProperty, Boolean randomInitialize, String nameOfDataType, String from, String to, String dataString, boolean isRange,int lastUnchangedTicks)
    {
        this.NameOfProperty = nameOfProperty;
        this.randomInitialize = randomInitialize;
        this.nameOfDataType = nameOfDataType;
        this.isRange = isRange;
        this.from = from;
        this.to = to;
        this.dataString = dataString;
        this.lastUnchangedTicks =lastUnchangedTicks;
    }

    public int getLastUnchangedTicks()
    {
        return lastUnchangedTicks;
    }

    public void setLastUnchangedTicks(int lastUnchangedTicks) {
        this.lastUnchangedTicks = lastUnchangedTicks;
    }

    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootNode = new TreeItem<>( NameOfProperty);
        rootNode.getChildren().add(new TreeItem<>("Random Initialize: " + randomInitialize));
        rootNode.getChildren().add(new TreeItem<>("Data Type: " + nameOfDataType));
        rootNode.getChildren().add(new TreeItem<>("From: " + from));
        rootNode.getChildren().add(new TreeItem<>("To: " + to));
        rootNode.getChildren().add(new TreeItem<>("Data String: " + dataString));

        return rootNode;
    }

    public String getNameOfDataType() {
        return nameOfDataType;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getNameOfProperty() {

        return NameOfProperty;
    }

    public boolean isRandomInitialize() {

        return randomInitialize;
    }

    public boolean isRange() {
        return isRange;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
}