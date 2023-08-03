import Environment.EnvironmentInstance;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import Entity.Properties;
import Entity.Entity;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Rules.FunctionHelper.RandomFun;


public class Engine
{

     public World world;


    public Engine()
    {
        world=new World();
    }
    public void ParseXmlAndLoadWorld( File file)
    {

        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder builder=factory.newDocumentBuilder();

            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList worldList = doc.getElementsByTagName("PRD-world");

            Node worldNode =worldList.item(0);
            NodeList Everything=worldNode.getChildNodes();
            System.out.println(Everything.getLength());

            NodeList prdEvironment = doc.getElementsByTagName("PRD-env-property");
            initEvironmentFromFile(prdEvironment,this.world);

            NodeList prdEntities = doc.getElementsByTagName("PRD-entity");
            initEntitiesFromFile(prdEntities,this.world);

            NodeList prdRules = doc.getElementsByTagName("PRD-rules");
            NodeList prdTermination = doc.getElementsByTagName("PRD-termination");




        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }


    }

   public void initEvironmentFromFile(NodeList list,World w)
    {
        for(int i=0;i<list.getLength();i++)
        {
            Node item=list.item(i);
            Element el=(Element) item;
            String type=((Element) item).getAttribute("type");
           String prdName=((Element) item).getElementsByTagName("PRD-name").item(0).getTextContent();
           String from=((Element) item).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("from").getTextContent();
           String to=((Element) item).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("to").getTextContent();
            EnvironmentInstance e= (EnvironmentInstance) initProperty(type,prdName,Integer.parseInt(from),Integer.parseInt(to),true,"Null",false,0);
            world.environmentVariables.add(e);



        }

    }

    public void initEntitiesFromFile(NodeList list,World w)
    {
        for(int i=0;i<list.getLength();i++)
        {
            Node item=list.item(i);
            Element el=(Element) item;
            String name=item.getAttributes().getNamedItem("name").getTextContent();
           String population= ((Element) item).getElementsByTagName("PRD-population").item(0).getTextContent();
           NodeList entityProberty=((Element) item).getElementsByTagName("PRD-property");
            Entity e1=new Entity();
            //List<Entity> entities = world.CreateEnityWithPopulation(name, Integer.parseInt(population));

            for(int j=0;j<list.getLength();j++)
            {
                Node item2=entityProberty.item(j);
                Element el2=(Element) item2;
                String type=((Element) item2).getAttribute("type");
                String prdName=((Element) item2).getElementsByTagName("PRD-name").item(0).getTextContent();
                String from=((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("from").getTextContent();
                String to=((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("to").getTextContent();
                String isRandom=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("random-initialize").getTextContent();
                String initValue=new String();
                initValue="-1"; // random value


                if(isRandom=="flase")
                {
                    initValue=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("init").getTextContent();
                    Properties e= (Properties) initProperty(type,prdName,Integer.parseInt(from),Integer.parseInt(to),true,"Null",Boolean.parseBoolean(isRandom),Integer.parseInt(initValue));
                    e1.propertiesOfTheEnitiy.add(e);
                }
                else
                {
                    Properties e= (Properties) initProperty(type,prdName,Integer.parseInt(from),Integer.parseInt(to),true,"Null",Boolean.parseBoolean(isRandom),Integer.parseInt(initValue));
                    e=(Properties)RandomFun(e,Integer.parseInt(initValue));
                    e1.propertiesOfTheEnitiy.add(e);

                }



            }

            for(int m=0;m<Integer.parseInt(population);m++)
            {
                this.world.entities.get(i).add(e1);

            }





        }

    }

    Object initProperty(String type,String name,int from , int to,boolean bool,String Stringdata,boolean isRandom,int initValue)
    {

        switch(type)
        {
            case "decimal":
                EnvironmentInstance res=new EnvironmentInstance();
                 res.Type=(Integer)res.Type;
                res.NameOfProperty=name;
                res.range[0]=from;
                res.range[1]=to;
                return res;

            case "float":
                Properties<Float> res2=new Properties();
                res2.Type=(Float)res2.Type;
                res2.NameOfProperty=name;
                res2.range[0]=from;
                res2.range[1]=to;
                return res2;
            case "boolean":
                Properties<Boolean> res3=new Properties();
                res3.Type=(Boolean) res3.Type;
                res3.NameOfProperty=name;
                res3.Type=bool;
                return res3;
            case"string":
                Properties<String> res4=new Properties();
                res4.Type=(String) res4.Type;
                res4.Type=Stringdata;
                res4.NameOfProperty=name;
                return res4;





        }
        return 5;
    }

}
