import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UI
{
    IEngine engine=new Engine();

    void getFileDirectoryAndLoadSimulation()
    {

        Scanner sc= new Scanner(System.in);
        System.out.println("Please, enter the directory of the file you wish to load : ");
        String path=sc.nextLine();
        try
        {
            Path dd = Paths.get(path);
            while(!Files.exists(dd))
            {
                System.out.println("You have entered a wrong file directory,please enter a valid one: ");
                path=sc.nextLine();
                dd = Paths.get(path);

            }

            Uri uri=new Uri(path);
            File f=new File(String.valueOf(uri));
            engine.ParseXmlAndLoadWorld(f);
            System.out.println("File Loaded successfully!");

        } catch (Exception e)
        {
            throw new RuntimeException(e);

        }
    }



    void PrintWorldDetails(WorldDTO worldDTO)
    {

        Scanner sc= new Scanner(System.in);
        System.out.println("There are"+worldDTO.getEntityDTOSet().size()+"entities,"+worldDTO.getRulesDTOSet().size()+"Laws in the current simulation");
        System.out.println("Please,enter the number of which you would like to get further details about: ");
        System.out.println("1- Entities");
        System.out.println("2- Rules");
        int userChosingFurtherDetails=sc.nextInt();
        checkIfNumberIsWithinRange(userChosingFurtherDetails,2);
// 11.8.2023





    }
    void checkIfNumberIsWithinRange(int number,int bound)
    {
        boolean option;
        option=number>=1&&number<=bound;
        Scanner sc= new Scanner(System.in);
        while(!option)
        {
            System.out.println("Please, choose a valid option number :");
            number=sc.nextInt();
            option=number>=1&&number<=bound;

        }
    }





}
