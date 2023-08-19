package System;

import DTOS.EnvironmentDTO;
import DTOS.WorldDTO;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public interface IEngine {
    public void ParseXmlAndLoadWorld(File file);

    public WorldDTO convertWorldToDTO();

    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception;

    public UUID startSimulation();

    public Map<UUID, Simulation> getSimulations();

    public Map<String, Integer> endOfSimulationHandlerShowQuantities(UUID simulationID);

    public void endOfSimulationHandlerPropertyHistogram(UUID simulationID, String chosenEntityName, String chosenPropertyName);
}