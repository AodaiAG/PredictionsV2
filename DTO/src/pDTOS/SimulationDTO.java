package pDTOS;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SimulationDTO
{

    String nameofSimulation;

    WorldDTO worldDTO;


    public TreeView<String> generateTreeView()
    {
        // Create a root item for the SimulationDTO
        TreeItem<String> rootItem = new TreeItem<>(nameofSimulation);

        // Add the WorldDTO's tree view as a child
        TreeView<String> worldTreeView = worldDTO.generateTreeView();
        rootItem.getChildren().add(worldTreeView.getRoot());

        // Create and return the tree view for the SimulationDTO
        TreeView<String> simulationTreeView = new TreeView<>(rootItem);
        simulationTreeView.setShowRoot(true); // Display the root item

        return simulationTreeView;
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
