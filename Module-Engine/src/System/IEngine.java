package System;

import DTOS.EnvironmentDTO;
import DTOS.WorldDTO;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IEngine {
    public void ParseXmlAndLoadWorld(File file) throws Exception;

    public WorldDTO convertWorldToDTO();

    public WorldDTO getWorldBeforeChanging();

    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception;

    public UUID startSimulation();

    Boolean isWordNull();

    public Map<UUID, Simulation> getSimulations();

    public Map<String, Integer> endOfSimulationHandlerShowQuantities(UUID simulatioID);

    public void endOfSimulationHandlerPropertyHistogram(UUID simulationID, String chosenEntityName, String chosenPropertyName);

    public List<Map.Entry<UUID, String>> getSortedSimulationsByDate();
}