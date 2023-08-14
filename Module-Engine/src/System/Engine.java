package System;
import DTOS.*;
import Environment.EnvironmentInstance;
import Expression.AuxiliaryMethods;
import Rules.ActionTypes.*;
import Rules.Rule;
import Rules.Activation;
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
import Entity.Data;
import Entity.DataType;
import Entity.Entity;

public class Engine implements IEngine
{
    public World world;

    public Engine() {
        this.world = new World();
    }

    public WorldDTO convertWorldToDTO()
    {
        World world = this.world;
        List<EntityDTO> entityDTOSet = new ArrayList<>();
        List<RulesDTO> rulesDTOSet = new ArrayList<>();
        List<EnvironmentDTO> envSet=new ArrayList<>();
        TerminationDTO terminationDTO=new TerminationDTO(world.getTerminationTicks(), world.getTerminationSeconds());
        for(Entity e: world.getEntities())
        {
            entityDTOSet.add(convertEntityToDTO(e));
        }
        for(Rule r: world.getRules())
        {
            rulesDTOSet.add(convertRuleToDTO(r));
        }
        for(EnvironmentInstance env:world.getName2Env().values())
        {
            PropertyDTO pDto=convertPropertyToDTO(env.getEnvironmentProperty());
            EnvironmentDTO dto=new EnvironmentDTO(pDto);
            envSet.add(dto);
        }
        return new WorldDTO(entityDTOSet, envSet, rulesDTOSet, terminationDTO);
    }

    @Override
    public void setDataToEnvironmentVar(EnvironmentDTO environmentDTO, String userValue) throws Exception {
       EnvironmentInstance environmentInstance = this.world.getName2Env().get(environmentDTO.getEnProperty().getNameOfProperty());
       try{
           environmentInstance.getEnvironmentProperty().getData().setNewValue(userValue);
           environmentInstance.getEnvironmentProperty().setRandomInitialize(false);
       }
       catch(Exception e)
        {
            throw  e;
        }
    }

    @Override
    public void startSimulation()
    {
        int tick=0;

        for (Rule rule : this.world.getRules())
        {
            rule.isActivated(world.getEntities(),tick,1);
            tick++;
        }
    }

    public RulesDTO convertRuleToDTO(Rule rule)
    {
        List<String> actionNames=new ArrayList<>();
        int numberofActions=rule.getActions().size();
        for(Action action:rule.getActions())
        {
            actionNames.add(action.getNameOfAction());
        }
        return new RulesDTO(rule.getNameOfRule(), rule.getActivation().getTicks(), rule.getActivation().getProbability(), numberofActions,actionNames);
    }

    public EntityDTO convertEntityToDTO(Entity entity)
    {
        List<PropertyDTO> propertyDTOs = new ArrayList<>();

        for (Property p: entity.getPropertiesOfTheEntity())
        {
            propertyDTOs.add(convertPropertyToDTO(p));
        }
        return new EntityDTO(entity.getNameOfEntity(), entity.getNumberOfInstances(), propertyDTOs);
    }

    public PropertyDTO convertPropertyToDTO(Property property)
    {
        return new PropertyDTO(property.getNameOfProperty(), property.isRandomInitialize(), property.getTypeString(), property.getData().from, property.getData().to,property.getData().getDataString(), property.getData().isRangeExist());
    }

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

            String ticks = doc.getElementsByTagName("PRD-by-ticks").item(0).getAttributes().getNamedItem("count").getTextContent();
            String seconds = doc.getElementsByTagName("PRD-by-second").item(0).getAttributes().getNamedItem("count").getTextContent();
            this.world.setTerminationTicks(Integer.parseInt(ticks));
            this.world.setTerminationSeconds(Integer.parseInt(seconds));
        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void initRulesFromFile(NodeList list, World world)
    {
        AuxiliaryMethods f = new AuxiliaryMethods(world);
        Rule justToCallFunction = new Rule();
        for(int i = 0; i < list.getLength(); i++)
        {
            Rule newRule = new Rule();
            newRule.setFunctions(f);
            Node item = list.item(i);
            Element el = (Element) item;
            String nameOfRule = ((Element) item).getAttribute("name");
            newRule.setNameOfRule(nameOfRule);
            Activation activation = new Activation();
            NodeList prdActivtionL = ((Element) item).getElementsByTagName("PRD-activation");
            if(prdActivtionL.getLength() != 0)//if not empty
            {
                Node ticksNode=((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("ticks");
                if(ticksNode != null)
                {
                    String tickString=ticksNode.getTextContent();
                    activation.setTicks(Integer.parseInt(tickString));
                }
                Node probNode=((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("probability");
                if(probNode != null)
                {
                    String probString=probNode.getTextContent();
                    activation.setProbability(Double.parseDouble(probString));
                }
            }
            newRule.setActivation(activation);
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
                Property eN1=  initProperty(type, prdName, true, from, to, true, "1");
                EnvironmentInstance environmentInstance=new EnvironmentInstance();
                environmentInstance.setEnvironmentProperty(eN1);
                world.getName2Env().put(prdName,environmentInstance);
            }
            else
            {
                Property eN= initProperty(type, prdName, false, from, to, true, "1");
                EnvironmentInstance environmentInstance = new EnvironmentInstance();
                environmentInstance.setEnvironmentProperty(eN);
                world.getName2Env().put(prdName,environmentInstance);
            }
        }
    }

    public void initEntitiesFromFile(NodeList list,World w)
    {
        for(int i=0;i<list.getLength();i++)
        {
            Entity newEntity = new Entity();
            Node item=list.item(i);
            Element el=(Element) item;
            String name=item.getAttributes().getNamedItem("name").getTextContent();
            newEntity.setNameOfEntity(name);
            String population= ((Element) item).getElementsByTagName("PRD-population").item(0).getTextContent();
            int popNumber=Integer.parseInt(population);
            newEntity.setNumberOfInstances(popNumber);

            NodeList entityProperty =((Element) item).getElementsByTagName("PRD-property");
            EntityInstance e1=new EntityInstance();
            e1.setNameOfEntity(name);

            for(int j = 0; j< entityProperty.getLength(); j++)
            {
                String from = "";
                String to = "";
                boolean isRange = false;
                Node item2 = entityProperty.item(j);
                Element el2 = (Element) item2;
                String type = ((Element) item2).getAttribute("type");
                String prdName = ((Element) item2).getElementsByTagName("PRD-name").item(0).getTextContent();

                if(((Element) item2).getElementsByTagName("PRD-range").item(0) != null)
                {
                    from=((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("from").getTextContent();
                    to=((Element) item2).getElementsByTagName("PRD-range").item(0).getAttributes().getNamedItem("to").getTextContent();
                    isRange = true;
                }

                String isRandom=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("random-initialize").getTextContent();
                String initValue = "1"; // random value


                if(isRandom.equals("false"))
                {
                    initValue=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("init").getTextContent();
                    Property property = initProperty(type, prdName, isRange, from, to, true, initValue);
                    e1.getPropertiesOfTheEntity().add(property);
                }
                else
                {
                    Property property = initProperty(type, prdName, isRange, from, to,true, initValue);
                    e1.getPropertiesOfTheEntity().add(property);
                }
            }

            // create collection of entities
            List<EntityInstance> first=new ArrayList<>();
            Set<Property> propOfEntity = e1.getPropertiesOfTheEntity();
            newEntity.setPropertiesOfTheEntity(propOfEntity);

            for(int m=0; m < popNumber; m++)
            {
                first.add(e1);
            }

            newEntity.setEntities(first);
            this.world.getEntities().add(newEntity);
        }
    }

    Property initProperty(String type, String name, boolean isRange, String from , String to, boolean bool, String init)
    {
        Property res = new Property();
        res.setNameOfProperty(name);
        res.setRandomInitialize(bool);
        Data eD = new Data();
        eD.setDataType(DataType.valueOf(type.toUpperCase()));
        eD.setRangeExist(isRange);
        if(isRange){
            eD.setFrom(from);
            eD.setTo(to);
        }
        eD.calculateNewVal(init, bool);
        res.setData(eD);
        return res;
    }

    private World getWorld()
    {
        return this.world;
    }
}