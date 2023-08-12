import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Engine w=new Engine();
        Scanner s=new Scanner(System.in);
        System.out.println("enter pathname");
        String path=s.nextLine();
        try
        {
            Path dd = Paths.get(path);
            while(!Files.exists(dd))
            {
                System.out.println("you have entered a wrong file directory,please enter a valid one: ");
                path=s.nextLine();
                dd = Paths.get(path);
            }

            Uri uri=new Uri(path);
            File f=new File(String.valueOf(uri));
            w.ParseXmlAndLoadWorld(f);
            System.out.println("File Loaded successfully");

        } catch (Exception e)
        {
            System.out.println("dosh bag");
            throw new RuntimeException(e);

        }
    }
}
