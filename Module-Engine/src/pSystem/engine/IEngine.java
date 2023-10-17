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
    public World ParseXmlAndLoadWorldFromDoc(Document doc) throws Exception;

    public WorldDTO convertWorldToDTO(World world);

    public Map<UUID, SimulationResult> getSimulationResults();

    public Map<String, aSimulation> getAllSimulations();
}