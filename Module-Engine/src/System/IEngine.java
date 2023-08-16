package System;

import DTOS.EnvironmentDTO;
import DTOS.WorldDTO;
import Entity.Entity;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public interface IEngine
{
    public void ParseXmlAndLoadWorld( File file);
    public WorldDTO convertWorldToDTO();
    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception;
    public UUID startSimulation();
    public Map<String, Integer> endOfSimulationHandlerShowQuantities(UUID simulatioID);
    public void endOfSimulationHandlerShowPropertyHistogram(UUID simulatioID, Entity entity);
    }