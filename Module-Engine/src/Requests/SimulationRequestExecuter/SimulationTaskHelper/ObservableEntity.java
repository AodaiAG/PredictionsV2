package Requests.SimulationRequestExecuter.SimulationTaskHelper;

import javafx.beans.property.SimpleStringProperty;

public class ObservableEntity
{
    String name;
    String population;

    public ObservableEntity()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }
}
