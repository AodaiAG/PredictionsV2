import Environment.EnvironmentInstance;
import Rules.ActionTypes.*;
import Rules.Actions;
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

            NodeList prdRules = doc.getElementsByTagName("PRD-rule");
            initRulesFromFile(prdRules,this.world);

            NodeList prdTermination = doc.getElementsByTagName("PRD-termination");




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
            NodeList prdActivtionL=((Element) item).getElementsByTagName("PRD-activation");
            if(prdActivtionL.getLength()!=0)//if not empty
            {
                Node ticksNode=((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("ticks");
                if(ticksNode!=null)
                {
                    String tickString=ticksNode.getTextContent();
                }
                Node probNode=((Element) item).getElementsByTagName("PRD-activation").item(0).getAttributes().getNamedItem("probability");
                if(probNode!=null)
                {
                    String probString=probNode.getTextContent();
                }
            }

            newRule.nameOfRule=nameOfRule;

           NodeList ActionsList= ((Element) item).getElementsByTagName("PRD-action");

            for(int m=0;m<ActionsList.getLength();m++)
            {

              String whichEntityActionWork=  ActionsList.item(m).getAttributes().getNamedItem("entity").getTextContent();
              String typeOfAction=  ActionsList.item(m).getAttributes().getNamedItem("type").getTextContent();

              switch (typeOfAction)
              {
                  case "increase":
                  {

                      IncreaseAction action=new IncreaseAction();
                      action.entityName=whichEntityActionWork;
                      action.propertyName=ActionsList.item(m).getAttributes().getNamedItem("property").getTextContent();
                      action.expression=ActionsList.item(m).getAttributes().getNamedItem("by").getTextContent();
                      newRule.actions.add(action);

                  }
                  case "decrease":
                  {
                      DecreaseAction action=new DecreaseAction();
                      action.entityName=whichEntityActionWork;
                      action.propertyName=ActionsList.item(m).getAttributes().getNamedItem("property").getTextContent();
                      action.expression=ActionsList.item(m).getAttributes().getNamedItem("by").getTextContent();
                      newRule.actions.add(action);

                  }
                  case "calculation":
                  {

                      CalculationAction action=new CalculationAction();
                      NodeList mul=((Element) item).getElementsByTagName("PRD-multiply");
                      NodeList div=((Element) item).getElementsByTagName("PRD-divide");
                      if(mul!=null)
                      {

                        action.expression1 = mul.item(0).getAttributes().getNamedItem("arg1").getTextContent();
                        action.expression2 = mul.item(0).getAttributes().getNamedItem("arg2").getTextContent();
                        action.calType="multiply";
                        action.resultProp=ActionsList.item(m).getAttributes().getNamedItem("result-prop").getTextContent();
                         newRule.actions.add(action);


                      }
                      if(div!=null)
                      {
                          action.expression1 = mul.item(0).getAttributes().getNamedItem("arg1").getTextContent();
                          action.expression2 = mul.item(0).getAttributes().getNamedItem("arg2").getTextContent();
                          action.calType="divide";
                          action.resultProp=ActionsList.item(m).getAttributes().getNamedItem("result-prop").getTextContent();
                          newRule.actions.add(action);
                      }


                  }

                  case "set":
                  {
                      SetAction action=new SetAction();
                      action.entityName=whichEntityActionWork;
                      action.propertyName=ActionsList.item(m).getAttributes().getNamedItem("property").getTextContent();
                      action.expression=ActionsList.item(m).getAttributes().getNamedItem("value").getTextContent();
                      newRule.actions.add(action);

                  }

                  case "kill":
                  {
                      KillAction action=new KillAction();
                      action.entityToKill=ActionsList.item(m).getAttributes().getNamedItem("entity").getTextContent();
                      newRule.actions.add(action);
                  }


              }

                //Actions action=SetActions();
            }







            this.world.rules.add(newRule);

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
            EnvironmentInstance e= (EnvironmentInstance) initProperty(type,prdName,Integer.parseInt(from),Integer.parseInt(to),true,"Null");
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

            for(int j=0;j<entityProberty.getLength();j++)
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


                if(isRandom.equals("false"))
                {
                    initValue=((Element) item2).getElementsByTagName("PRD-value").item(0).getAttributes().getNamedItem("init").getTextContent();
                    Properties e= (Properties) initProperty(type,prdName,Integer.parseInt(from),Integer.parseInt(to),true,"Null");
                   e= e.setPropertiesAcorrdingToRandomInit(e,type,isRandom,Integer.parseInt(initValue));
                    e1.propertiesOfTheEnitiy.add(e);
                }
                else
                {
                    Properties e= (Properties) initProperty(type,prdName,Integer.parseInt(from),Integer.parseInt(to),true,"Null");
                    e= e.setPropertiesAcorrdingToRandomInit(e,type,isRandom,Integer.parseInt(initValue));
                    e1.propertiesOfTheEnitiy.add(e);

                }



            }
            // create collection of entites
            List<Entity> first=new ArrayList<>();
            int popNumber=Integer.parseInt(population);
            for(int m=0;m<popNumber;m++)
            {
                first.add(e1);
            }
            // add the collection to entites
            //this.world.entities.add(first);





        }

    }

    Object initProperty(String type,String name,int from , int to,boolean bool,String Stringdata)
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
