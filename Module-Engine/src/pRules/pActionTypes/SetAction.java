package pRules.pActionTypes;

import org.w3c.dom.Node;
import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.ReplaceActionDTO;
import pDTOS.ActionsDTO.SetActionDTO;
import pEntity.Property;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;
import pExpression.Expression;

public class SetAction extends Action
{
    private String entityName;
    private String propertyName;
    private String expression;

    public SetAction() {
        entityName = "";
        propertyName = "";
        expression = "";
    }

    public ActionDTO convertToDTO()
    {

        SetActionDTO setActionDTO=new SetActionDTO();
        setActionDTO.setMainEntityNameActionWorksOn(entityName);
        setActionDTO.setNameOfAction("set");
        setActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());
        setActionDTO.setArg1(expression);
        setActionDTO.setPropertyName(propertyName);

        return setActionDTO;
    }

    @Override
    public String getNameOfEntity() {
        return entityName;
    }

    @Override
    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getNameOfAction() {
        return "set";
    }

    @Override
    public void ActivateAction(EntityInstance ...args) throws Exception
    {
        EntityInstance e=args[0];
        Expression exp = new Expression(getFunctions(), e);

        String sValue = exp.evaluateExpression(expression);
        for (Property t : e.getPropertiesOfTheEntity()) {
            if (t.getNameOfProperty().equals(propertyName)) {
                try {
                    t.getData().setNewValue(sValue);
                } catch (Exception ex) {

                }
                break;
            }
        }
    }
}