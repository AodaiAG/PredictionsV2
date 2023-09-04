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

        return enProperty.generateTreeView();
    }

    public PropertyDTO getEnProperty() {
        return enProperty;
    }
}