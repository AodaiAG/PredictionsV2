import Environment.EnvironmentInstance;
import Rules.ActionTypes.*;
import Rules.Rules;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import Entity.EntityInstance;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Entity.Property;
import java.util.Set;
import Entity.eData;
import Entity.Entity;



public class Engine implements IEngine
{

    public World world=new World();

    public void ParseXmlAndLoadWorld(File file)
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

            NodeList prdEvironment = doc.getElementsByTagName("PRD-env-property");
            initEvironmentFromFile(prdEvironment,this.world);

            NodeList prdEntities = doc.getElementsByTagName("PRD-entity");
            initEntitiesFromFile(prdEntities,this.world);

            NodeList prdRules = doc.getElementsByTagName("PRD-rule");
            initRulesFromFile(prdRules,this.world);
//
            String ticks = doc.getElementsByTagName("PRD-by-ticks").item(0).getAttributes().getNamedItem("count").getTextContent();
            String seconds = doc.getElementsByTagName("PRD-by-second").item(0).getAttributes().getNamedItem("count").getTextContent();
            this.world.setTerminationTicks(Integer.parseInt(ticks));
            this.world.setTerminationSeconds(Integer.parseInt(seconds));
            System.out.println("Testing");




        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void initRulesFromFile(NodeList list, World world)
    {
        Rules justToCallFunction=new Rules();
        for(int i=0;i<list.getLength();i++)
        {
            Rules newRule=new Rules();
            Node item=list.item(i);
            Element el=(Element) item;
            String nameOfRule=((Element) item).getAttribute("name");
            newRule.setNameOfRule(nameOfRule);
            NodeList prdActivtionL=((Element) item).getElementsByTagName("PRD-activation");
            if(prdActivtionL.getLength()!=0)//if not empty
            {
                Node ticksNode=((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("ticks");
                if(ticksNode!=null)
                {
                    String tickString=ticksNode.getTextContent();
                    newRule.setTicks(Integer.parseInt(tickString));
                }
                Node probNode=((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("probability");
                if(probNode!=null)
                {
                    String probString=probNode.getTextContent();
                    newRule.setProbability(Double.parseDouble(probString));
                }
            }

            NodeList actionsListOfaRule= ((Element) item).getElementsByTagName("PRD-actions").item(0).getChildNodes();


            for(int m=0;m<actionsListOfaRule.getLength();m++)
            {
                if(actionsListOfaRule.item(m).getNodeType()==Node.ELEMENT_NODE)
                {
                    Action action=justToCallFunction.CreateAction(actionsListOfaRule.item(m));

                    newRule.getActions().add(action);
                }
            }

            this.world.getRules().add(newRule);
        }
    }


    public void initEvironmentFromFile(NodeList list,World w)
    {
        for(int i=0;i<list.getLength();i++)
        {
            Node item=list.item(i);
            Element el=(Element) item;
            String from=new String();
            String to=new String();

            String type=((Element) item).getAttribute("type");
            String prdName=((Element) item).getElementsByTagName("PRD-name").item(0).getTextContent();
            if((((Element) item).getElementsByTagName("PRD-range").item(0))!=null)
            {
                from=((Element) item).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("from").getTextContent();
                to=((Element) item).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("to").getTextContent();

                Property eN=  initProperty(type,prdName,from,to,true,"1");
                EnvironmentInstance environmentInstance=new EnvironmentInstance();
                environmentInstance.setEnvironmentProperty(eN);

                world.getEnvironmentVariables().add( environmentInstance);
            }
            else
            {
               Property eN= initProperty(type,prdName,from,to,true,"1");
                EnvironmentInstance environmentInstance=new EnvironmentInstance();
                environmentInstance.setEnvironmentProperty(eN);
               world.getEnvironmentVariables().add(environmentInstance);
            }
        }
    }

    public void initEntitiesFromFile(NodeList list,World w)
    {
        for(int i=0;i<list.getLength();i++)
        {
            Entity newEntityCollection=new Entity();
            Node item=list.item(i);
            Element el=(Element) item;
            String name=item.getAttributes().getNamedItem("name").getTextContent();
            newEntityCollection.setNameOfEntity(name);
            String population= ((Element) item).getElementsByTagName("PRD-population").item(0).getTextContent();

            NodeList entityProperty =((Element) item).getElementsByTagName("PRD-property");
            EntityInstance e1=new EntityInstance();
            e1.setNameOfEntity(name);

            for(int j = 0; j< entityProperty.getLength(); j++)
            {
                String from=new String();
                String to=new String();
                Node item2= entityProperty.item(j);
                Element el2=(Element) item2;
                String type=((Element) item2).getAttribute("type");
                String prdName=((Element) item2).getElementsByTagName("PRD-name").item(0).getTextContent();
                if(((Element) item2).getElementsByTagName("PRD-range").item(0)!=null)
                {
                    from=((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("from").getTextContent();
                    to=((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("to").getTextContent();
                }

                String isRandom=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("random-initialize").getTextContent();
                String initValue=new String();
                initValue="1"; // random value


                if(isRandom.equals("false"))
                {
                    initValue=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("init").getTextContent();
                    Property property = initProperty(type,prdName,from,to,true, initValue);
                    e1.getPropertiesOfTheEnitiy().add(property);
                }
                else
                {
                    Property property = initProperty(type, prdName, from, to,true, initValue);
                    e1.getPropertiesOfTheEnitiy().add(property);
                }
            }

            // create collection of entites
            List<EntityInstance> first=new ArrayList<>();
            int popNumber=Integer.parseInt(population);
            Set<Property> propOfEntity = e1.getPropertiesOfTheEnitiy();
            newEntityCollection.setPropertiesOfTheEntity(propOfEntity);

            for(int m=0;m<popNumber;m++)
            {
                first.add(e1);
            }

            newEntityCollection.setEntities(first);
            this.world.getEntities().add(newEntityCollection);

            System.out.println("test");
        }

    }

    Property initProperty(String type, String name, String from , String to, boolean bool, String init)
    {
        Property res = new Property();
        res.setNameOfProperty(name);
        res.setRandomInitialize(bool);

        eData eD = eData.valueOf(type.toUpperCase());
        eD.setFrom(from);
        eD.setTo(to);
        eD.calculateNewVal(init, bool);
        res.setData(eD);
        res.setFrom(from);
        res.setTo(to);
        return res;
    }


    public World getWorld()
    {
        return this.world;

    }

}