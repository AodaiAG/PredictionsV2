import DTOS.WorldDTO;

import java.util.UUID;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        UI user = new UI();
        user.getFileDirectoryAndLoadSimulation();
        WorldDTO worldDTOBeforeSimulation = user.engine.convertWorldToDTO(); //old values be kept
        user.PrintWorldDetails(worldDTOBeforeSimulation);
        user.runSimulation(worldDTOBeforeSimulation);
        WorldDTO WorldDTOAfterSimulation = user.engine.convertWorldToDTO(); //updated world turn into worldDTO


    }

}