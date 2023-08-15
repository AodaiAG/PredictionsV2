import DTOS.WorldDTO;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        UI user = new UI();
        user.getFileDirectoryAndLoadSimulation();
        WorldDTO worldDTO=user.engine.convertWorldToDTO();
        user.PrintWorldDetails(worldDTO);
        user.startSimulation(worldDTO);
    }

}