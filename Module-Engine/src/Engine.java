import Environment.EnvironmentInstance;
import Rules.ActionTypes.*;
import Rules.Rules;
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
import Entity.EntityCollection;


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




        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new RuntimeException(e);
        }


    }


    private void initRulesFromFile(NodeList list, World world)
    {
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

            NodeList actionsListOfaRule= ((Element) item).getElementsByTagName("PRD-actions");
            Element actionsListOfaRuleElement= (Element) actionsListOfaRule.item(0);
            NodeList ActionsList= actionsListOfaRuleElement.getElementsByTagName("PRD-action");


            for(int m=0;m<ActionsList.getLength();m++)
            {

                Action action=CreateAction(ActionsList.item(m));


                newRule.getActions().add(action);



            }







            this.world.getRules().add(newRule);

        }



    }


    public Action CreateAction(Node ActionNode)
    {


        String whichEntityActionWork=  ActionNode.getAttributes().getNamedItem("entity").getTextContent();
        String typeOfAction=  ActionNode.getAttributes().getNamedItem("type").getTextContent();

        switch (typeOfAction)
        {

            case "condition":
            {

                ConditionAction conditionA=new ConditionAction();

                conditionA.setNameOfEntity(whichEntityActionWork);

                Node c=((Element) ActionNode).getElementsByTagName("PRD-condition").item(0);
                String typeOfCondition=c.getAttributes().getNamedItem("singularity").getTextContent();
                if(typeOfCondition.equals("single"))
                {
                    SingleCondition single=new SingleCondition();
                    single.setNameofEntity(c.getAttributes().getNamedItem("entity").getTextContent());
                    single.setNameofProperty(c.getAttributes().getNamedItem("property").getTextContent());
                    single.setOperator(c.getAttributes().getNamedItem("operator").getTextContent());
                    single.setValue(c.getAttributes().getNamedItem("value").getTextContent());
                    conditionA.setCondition(single);
                }
                if(typeOfCondition.equals("multiple"))

                {

                    conditionA.setCondition(MultipleCondition.createMultipleCondition(c));

                }


                // then

                Element thenNodesF=(Element)(((Element) ActionNode).getElementsByTagName("PRD-then").item(0));
                if(thenNodesF!=null)
                {NodeList thenNodes=thenNodesF.getElementsByTagName("PRD-action");
                    for(int p=0;p<thenNodes.getLength();p++)
                    {
                        conditionA.getActionsToDoIfTrue().add(p,CreateAction(thenNodes.item(p)));

                    }

                }


                Element elseNodesF=(Element)(((Element) ActionNode).getElementsByTagName("PRD-else").item(0));
                if(elseNodesF!=null)
                {
                    NodeList elseNodes=elseNodesF.getElementsByTagName("PRD-action");

                    for(int p=0;p<elseNodes.getLength();p++)
                    {
                        conditionA.getActionsToDoIfFalse().add(p,CreateAction(elseNodes.item(p)));

                    }

                }
                return conditionA;




            }
            case "increase":
            {

                IncreaseAction action=new IncreaseAction();
                action.setEntityName(whichEntityActionWork);
                action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                action.setExpression(ActionNode.getAttributes().getNamedItem("by").getTextContent());

                return action;

            }
            case "decrease":
            {
                DecreaseAction action=new DecreaseAction();
                action.setEntityName(whichEntityActionWork);
                action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                action.setExpression(ActionNode.getAttributes().getNamedItem("by").getTextContent());
                return action;


            }
            case "calculation":
            {


                CalculationAction action=new CalculationAction();
                NodeList mul= ((Element) ActionNode).getElementsByTagName("PRD-multiply");
                NodeList div=((Element) ActionNode).getElementsByTagName("PRD-divide");
                if(mul.item(0)!=null)
                {

                    action.setExpression1(mul.item(0).getAttributes().getNamedItem("arg1").getTextContent());
                    action.setExpression2( mul.item(0).getAttributes().getNamedItem("arg2").getTextContent());
                    action.setCalType("multiply");
                    action.setResultProp(ActionNode.getAttributes().getNamedItem("result-prop").getTextContent());
                    return action;


                }
                if(div.item(0)!=null)
                {
                    action.setExpression1(div.item(0).getAttributes().getNamedItem("arg1").getTextContent()); ;
                    action.setExpression2(div.item(0).getAttributes().getNamedItem("arg2").getTextContent()); ;
                    action.setCalType("divide");
                    action.setResultProp(ActionNode.getAttributes().getNamedItem("result-prop").getTextContent());
                    return action;
                }

                break;

            }

            case "set":
            {
                SetAction action=new SetAction();
                action.setEntityName(whichEntityActionWork);
                action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                action.setExpression(ActionNode.getAttributes().getNamedItem("value").getTextContent());
                return action;


            }

            case "kill":
            {
                KillAction action=new KillAction();
                action.setEntityToKill(ActionNode.getAttributes().getNamedItem("entity").getTextContent());
                return action;

            }






        }

        throw new RuntimeException("emptyList");
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
                EnvironmentInstance e= (EnvironmentInstance) initProperty(type,prdName,from,to,true,"Null");

                world.getEnvironmentVariables().add(e);
            }
            else
            {
                EnvironmentInstance e= (EnvironmentInstance) initProperty(type,prdName,from,to,true,"Null");
                world.getEnvironmentVariables().add(e);
            }







        }

    }

    public void initEntitiesFromFile(NodeList list,World w)
    {
        for(int i=0;i<list.getLength();i++)
        {
            EntityCollection newEntityCollection=new EntityCollection();
            Node item=list.item(i);
            Element el=(Element) item;
            String name=item.getAttributes().getNamedItem("name").getTextContent();
            newEntityCollection.setNameOfEntity(name);
            String population= ((Element) item).getElementsByTagName("PRD-population").item(0).getTextContent();

            NodeList entityProberty=((Element) item).getElementsByTagName("PRD-property");
            Entity e1=new Entity();
            e1.setNameOfEntity(name);


            for(int j=0;j<entityProberty.getLength();j++)
            {
                String from=new String();
                String to=new String();
                Node item2=entityProberty.item(j);
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
                initValue="-1"; // random value


                if(isRandom.equals("false"))
                {
                    initValue=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("init").getTextContent();
                    Properties e= (Properties) initProperty(type,prdName,from,to,true,"Null");
                    e= e.setPropertiesAcorrdingToRandomInit(e,type,isRandom,initValue);
                    e.setRandomInitialize(Boolean.valueOf(isRandom));
                    e1.getPropertiesOfTheEnitiy().add(e);
                }
                else
                {
                    Properties e= (Properties) initProperty(type,prdName,from,to,true,"Null");
                    e= e.setPropertiesAcorrdingToRandomInit(e,type,isRandom,initValue);
                    e.setRandomInitialize(Boolean.valueOf(isRandom));
                    e1.getPropertiesOfTheEnitiy().add(e);

                }



            }
            // create collection of entites
            List<Entity> first=new ArrayList<>();
            int popNumber=Integer.parseInt(population);
            for(int m=0;m<popNumber;m++)
            {
                first.add(e1);
            }
            newEntityCollection.setEntities(first);
            this.world.getEntities().add(newEntityCollection);

            System.out.println("test");





        }

    }

    Object initProperty(String type,String name,String from , String to,boolean bool,String Stringdata)
    {

        switch(type)
        {
            case "decimal":
                EnvironmentInstance res=new EnvironmentInstance();
                res.setType(new Integer(0));
                res.setNameOfProperty(name);
                res.range[0]=Integer.parseInt(from);
                res.range[1]=Integer.parseInt(to);
                return res;

            case "float":
                EnvironmentInstance res2=new EnvironmentInstance();
                res2.setType(new Float(0));
                res2.setNameOfProperty(name);
                res2.range[0]=Float.parseFloat(from);
                res2.range[1]=Float.parseFloat(to);
                return res2;
            case "boolean":
                EnvironmentInstance res3=new EnvironmentInstance();
                res3.setType(new Boolean(false));
                res3.setNameOfProperty(name);
                return res3;
            case"string":
                EnvironmentInstance res4=new EnvironmentInstance();
                res4.setType(Stringdata);
                res4.setNameOfProperty(name);
                return res4;





        }
        return 5;
    }

}
