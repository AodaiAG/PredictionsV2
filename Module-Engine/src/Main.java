import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import System.Engine;

public class Main
{
    public static void main(String[] args)
    {
        Calendar ccc=Calendar.getInstance();
        Date date=ccc.getTime();
        date.getDay();
        System.out.println(date.getDay());
    }
}
