package Requests.SimulationRequestExecuter.SimulationTaskHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EntityWrapper
{
    private List<ObservableEntity> entityList = new ArrayList<>();

    public List<ObservableEntity> getEntityList()
    {
        return entityList;
    }

    public void addEntity(ObservableEntity entity) {
        entityList.add(entity);
    }

    public void removeEntity(ObservableEntity entity) {
        entityList.remove(entity);
    }
}

