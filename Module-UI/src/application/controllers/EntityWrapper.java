package application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntityWrapper
{
    private ObservableList<ObservableEntity> entityList = FXCollections.observableArrayList();

    public ObservableList<ObservableEntity> getEntityList()
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

