package pSystem.engine;


import Requests.SimulationRequestExecuter.SimulationTaskHelper.EntityWrapper;
import Requests.SimulationRequestExecuter.SimulationTaskHelper.SimulationConditions;
import org.w3c.dom.Document;
import pDTOS.EntityDTO;
import pDTOS.EnvironmentDTO;
import pDTOS.WorldDTO;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public interface IEngine
{
    public void ParseXmlAndLoadWorld(File file) throws Exception;
    public World ParseXmlAndLoadWorldFromDoc(Document doc) throws Exception;

    public WorldDTO convertWorldToDTO(World world);


    public void createEntityPopulation(int popNumber, EntityDTO selectedentityDTO) throws Exception;

    public WorldDTO getWorldBeforeChanging();

    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception;

    public UUID startSimulation(SimulationConditions simulationConditions, Consumer<String> consumer, EntityWrapper entityWrapper);
//    public void stopSimulation();
//    public void pauseSimulation();
//    public void resumeSimulation();
    public World cloneWorld();
    Boolean isWordNull();
    public void setWorldFromExecution(SimulationResult simulationResult);
    public void clearButtonPressed();

    public Map<UUID, SimulationResult> getSimulationResults();

    public Map<String, Integer> endOfSimulationHandlerShowQuantities(UUID simulatioID);
    public Map<String, aSimulation> getAllSimulations();


    public void endOfSimulationHandlerPropertyHistogram(UUID simulationID, String chosenEntityName, String chosenPropertyName);

    public List<Map.Entry<UUID, String>> getSortedSimulationsByDate();
    public World getOriginalWorld();

    int getNumThreads();
}