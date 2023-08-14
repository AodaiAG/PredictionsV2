package System;

import DTOS.EnvironmentDTO;
import DTOS.WorldDTO;

import java.io.File;

public interface IEngine
{
    public void ParseXmlAndLoadWorld( File file);
    public WorldDTO convertWorldToDTO();
    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception;
    public void startSimulation();
}