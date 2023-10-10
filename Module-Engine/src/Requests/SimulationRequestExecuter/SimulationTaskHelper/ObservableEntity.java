package Requests.SimulationRequestExecuter.SimulationTaskHelper;

import javafx.beans.property.SimpleStringProperty;

public class ObservableEntity
{
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty population = new SimpleStringProperty();

    public ObservableEntity()
    {

    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPopulation()
    {
        return population.get();
    }

    public void setPopulation(String population)
    {
        this.population.set(population);
    }

    // Define getters and setters for SimpleStringProperty and SimpleIntegerProperty
    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty populationProperty() {
        return population;
    }
}
