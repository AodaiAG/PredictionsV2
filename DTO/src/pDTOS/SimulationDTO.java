package pDTOS;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SimulationDTO
{

    String nameofSimulation;
    WorldDTO worldDTO;


    public TreeItem<String> generateTreeView()
    {
        // Create a root item for the SimulationDTO
        TreeItem<String> rootItem = new TreeItem<>(nameofSimulation);

        // Add the WorldDTO's tree view as a child
        TreeItem<String> worldTreeItem = worldDTO.generateTreeView().getRoot();
        rootItem.getChildren().add(worldTreeItem);

        return rootItem;
    }

    public SimulationDTO(WorldDTO worldDTO, String nameofSimulation)
    {
        this.worldDTO = worldDTO;
        this.nameofSimulation = nameofSimulation;
    }


    public WorldDTO getWorldDTO()
    {
        return worldDTO;
    }

    public void setWorldDTO(WorldDTO worldDTO)
    {
        this.worldDTO = worldDTO;
    }

    public String getNameofSimulation()
    {
        return nameofSimulation;
    }

    public void setNameofSimulation(String nameofSimulation)
    {
        this.nameofSimulation = nameofSimulation;
    }



}
