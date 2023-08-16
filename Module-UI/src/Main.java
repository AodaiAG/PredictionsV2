import DTOS.WorldDTO;

import java.util.Scanner;
import java.util.UUID;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        UI user = new UI();
        WorldDTO worldDTOBeforeSimulation = user.engine.convertWorldToDTO(); //old values be kept
        Scanner sc = new Scanner(System.in);
        boolean validChoice = false;
        while (!validChoice)
        {
            System.out.println("1. Load xml File ");
            System.out.println("2. Show simulation details");
            System.out.println("3. Start simulation");
            System.out.println("4. Show previous simulations");
            System.out.println("5. Exit");

            int choice = sc.nextInt();
            switch (choice)
            {
                case 1:
                {
                    {
                        user.getFileDirectoryAndLoadSimulation();
                        break;
                    }
                }

                case 2:
                {
                    {
                        user.PrintWorldDetails(user.engine.convertWorldToDTO());
                        break;
                    }
                }
                case 3:
                {
                    user.runSimulation(user.engine.convertWorldToDTO());
                    break;
                }
                case 4:
                {

                    user.showAllSimulationsChooseWhichToShowDetails();
                }
                case 5:
                {
                    {
                        validChoice=true;
                        break;
                    }
                }

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }
    }



}