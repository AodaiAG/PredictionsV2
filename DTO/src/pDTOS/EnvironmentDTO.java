package pDTOS;

import javafx.scene.control.TreeItem;

public class EnvironmentDTO
{
    private final PropertyDTO enProperty;

    public EnvironmentDTO(PropertyDTO enProperty) {
        this.enProperty = enProperty;
    }
    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootNode = new TreeItem<>(enProperty.getNameOfProperty());

        // Add the property details as a child node
        TreeItem<String> propertyNode = new TreeItem<>();
        propertyNode.getChildren().add(enProperty.generateTreeView());

        rootNode.getChildren().add(propertyNode);

        return rootNode;
    }

    public PropertyDTO getEnProperty() {
        return enProperty;
    }
}