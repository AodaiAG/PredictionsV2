package pDTOS;

public class EnvironmentDTO {
    private final PropertyDTO enProperty;

    public EnvironmentDTO(PropertyDTO enProperty) {
        this.enProperty = enProperty;
    }

    public PropertyDTO getEnProperty() {
        return enProperty;
    }
}