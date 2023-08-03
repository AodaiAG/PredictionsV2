import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Engine w=new Engine();
        Scanner s=new Scanner(System.in);
        System.out.println("enter pathname");
        String path=s.nextLine();
        try {
            Uri uri=new Uri(path);
            File f=new File(String.valueOf(uri));
            w.ParseXmlAndLoadWorld(f);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }





    }
}
