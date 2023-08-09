package Rules.ActionTypes;



import Entity.Entity;
import Rules.Rules;
import com.sun.xml.internal.ws.api.pipe.Engine;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;


import static com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.getAttributes;

public class MultipleCondition extends ConditionAction
{
    private Boolean conditionResult;
    private String logical;
    private List<ConditionAction> listOfConditions;

    @Override
    public Boolean getConditionResult()
    {
        return conditionResult;
    }

    @Override
    public void setConditionResult(Boolean conditionResult) {
        this.conditionResult = conditionResult;
    }


    public  MultipleCondition createMultipleCondition(Node nodelist)
    {
        MultipleCondition res=new MultipleCondition();
        res.setLogical(nodelist.getAttributes().getNamedItem("logical").getTextContent());
        Element el=(Element) nodelist;
       //NodeList wanted= el.getElementsByTagName("PRD-condition");
       NodeList wanted= nodelist.getChildNodes();
       Rules justToCallFunction =new Rules();


       for(int i=0;i<wanted.getLength();i++)
       {


           if(wanted.item(i).getNodeType()==Node.ELEMENT_NODE)
           {
               Node node=wanted.item(i);
               String conditionType=node.getAttributes().getNamedItem("singularity").getTextContent();
               if(conditionType.equals("single"))
               {
                   SingleCondition single=new SingleCondition();
                   single.setNameofEntity(node.getAttributes().getNamedItem("entity").getTextContent());
                   single.setNameofProperty(node.getAttributes().getNamedItem("property").getTextContent());
                   single.setOperator(node.getAttributes().getNamedItem("operator").getTextContent());
                   single.setValue(node.getAttributes().getNamedItem("value").getTextContent());
                   res.getListOfConditions().add(single);
               }
               if(conditionType.equals("multiple"))
               {
                   res.getListOfConditions().add(createMultipleCondition(node));
               }


           }
       }

        // then

        Element thenNodesF=(Element)(((Element) nodelist).getElementsByTagName("PRD-then").item(0));
        if(thenNodesF!=null)
        {
            NodeList thenNodes=thenNodesF.getElementsByTagName("PRD-action");
            for(int p=0;p<thenNodes.getLength();p++)
            {
                res.getActionsToDoIfTrue().add(p,justToCallFunction.CreateAction(thenNodes.item(p)));

            }

        }


        Element elseNodesF=(Element)(((Element) nodelist).getElementsByTagName("PRD-else").item(0));
        if(elseNodesF!=null)
        {
            NodeList elseNodes=elseNodesF.getElementsByTagName("PRD-action");

            for(int p=0;p<elseNodes.getLength();p++)
            {
                res.getActionsToDoIfFalse().add(p,justToCallFunction.CreateAction(elseNodes.item(p)));

            }


        }





       return res;


    }

    public String getLogical() {
        return logical;
    }

    public void setLogical(String logical) {
        this.logical = logical;
    }

    public List<ConditionAction> getListOfConditions() {
        return listOfConditions;
    }

    public void setListOfConditions(List<ConditionAction> listOfConditions) {
        this.listOfConditions = listOfConditions;
    }


    @Override
    public void ActivateAction(Entity e) throws Exception
    {

            switch (logical)
            {
                    case "and":
                    {
                        for (ConditionAction c:listOfConditions)
                        {

                            c.ActivateAction(e);
                            Boolean result=c.getConditionResult();
                            if(result==false)
                            {
                                conditionResult=false;
                                break;
                            }

                        }

                        break;
                    }
                    case"or":
                    {
                        conditionResult=false;
                        for (ConditionAction c:listOfConditions)
                        {
                            c.ActivateAction(e);
                            Boolean result=c.getConditionResult();

                            if(result==true)
                            {
                                conditionResult=true;
                            }

                        }
                        break;

                    }

        }
    }

    public MultipleCondition()
    {
        logical=new String();
        listOfConditions=new ArrayList<>();
        conditionResult=true;
    }


}
