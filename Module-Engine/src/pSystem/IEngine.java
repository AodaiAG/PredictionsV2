package pSystem;

import pDTOS.EntityDTO;
import pDTOS.EnvironmentDTO;
import pDTOS.WorldDTO;
import pEntity.Entity;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IEngine
{
    public void ParseXmlAndLoadWorld(File file) throws Exception;

    public WorldDTO convertWorldToDTO();
    public void createEntityPopulation(int popNumber, EntityDTO selectedentityDTO);


    public WorldDTO getWorldBeforeChanging();

    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception;

    public UUID startSimulation();

    Boolean isWordNull();

    public Map<UUID, Simulation> getSimulations();

    public Map<String, Integer> endOfSimulationHandlerShowQuantities(UUID simulatioID);

    public void endOfSimulationHandlerPropertyHistogram(UUID simulationID, String chosenEntityName, String chosenPropertyName);

    public List<Map.Entry<UUID, String>> getSortedSimulationsByDate();
}