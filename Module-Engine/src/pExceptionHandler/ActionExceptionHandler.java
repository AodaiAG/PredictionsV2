package pExceptionHandler;

import pEntity.Entity;
import pEntity.Property;
import pEnvironment.EnvironmentInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pSystem.World;

public class ActionExceptionHandler {
    public void checkProbabilityActivation(String prob) throws Exception {
        try {
            Float res = Float.parseFloat(prob);
            if (res < 0 || res > 1) {
                throw new Exception("Probability should be between 0-1 !");
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException("probability Activation for rule/action should be numeric!");
        }
    }

    public void checkTicksActivation(String ticks) throws Exception {
        try {
            Integer res = Integer.parseInt(ticks);
        } catch (Exception e) {
            throw new Exception("ticks should be Integer!");
        }
    }

    public void checkIfEntityExists(List<Entity> entities, String checked) throws Exception {

        for (Entity entity : entities) {
            if (entity.getNameOfEntity().equals(checked)) {
                return;
            }
        }
        throw new Exception("entity " + checked + " doesn't exist! ");
    }

    public void checkIfActionTypeValid(String type) throws Exception {
        String lowCase = type.toLowerCase();

        if (!lowCase.equals("condition") && !lowCase.equals("multiple") && !lowCase.equals("increase") && !lowCase.equals("decrease") && !lowCase.equals("calculation") && !lowCase.equals("set") && !lowCase.equals("kill") && !lowCase.equals("proximity") && !lowCase.equals("replace")) {
            throw new Exception("Action type " + type + " not Supported!");
        }
    }

    public void conditionCheckOperator(String operator) throws Exception {
        if (operator.equals("or") || operator.equals("and")) {
            return;
        } else {
            throw new Exception("Action condition: operator " + operator + " not supported!");
        }
    }

    public void conditionSingleCheckOperator(String operator) throws Exception {
        if (operator.equals("=") || operator.equals("!=") || operator.equals("bt") || operator.equals("lt")) {
            return;
        } else {
            throw new Exception("Action condition(single): operator " + operator + " not supported!");
        }
    }

    public void conditionCheckSingularity(String s) throws Exception {
        if (s.equals("single") || s.equals("multiple")) {
            return;
        } else {
            throw new Exception("Action condition: singularity type is not supported!");
        }

    }

    public void isEnvironmentExists(Map<String, EnvironmentInstance> environmentInstanceMap, String checked) throws Exception {
        EnvironmentInstance environmentInstance = environmentInstanceMap.get(checked);
        if (environmentInstance == null) {
            throw new Exception("Environment variable " + checked + " doesn't exist!");
        }
    }

    public boolean isAuxiliaryMethods(String checked) {
        int startIndex = checked.indexOf("(");
        if (startIndex > 0) {
            String firstWord = checked.substring(0, startIndex);
            return firstWord.equals("environment") || firstWord.equals("random") || firstWord.equals("evaluate") || firstWord.equals("percent") || firstWord.equals("ticks");
        }
        return false;
    }

    public boolean checkIfPropertyExists(Set<Property> properties, String checked) {
        for (Property property : properties) {
            if (property.getNameOfProperty().equals(checked))
                return true;
        }

        return false;
    }

    public void isExpressionValid(String exp, String entityWorksOn, World world, String typeofAction) throws Exception {
        Entity entityWanted = null;
        for (Entity entity : world.getEntities()) {
            if (entity.getNameOfEntity().equals(entityWorksOn)) {
                entityWanted = entity;
                break;
            }
        }
        if (isAuxiliaryMethods(exp)) {
            return;
        }

        if (checkIfPropertyExists(entityWanted.getPropertiesOfTheEntity(), exp)) {
            return;
        }

        switch (typeofAction) {
            case "increase": {
                try {
                    Float.parseFloat(exp);
                } catch (Exception e) {
                    throw new Exception("'" + exp + "'" + " is neither a predefined method, nor a property in '" + entityWorksOn + "' nor does it match the action type '" + typeofAction + "'!");
                }
                break;
            }
//Calculation
            case "decrease": {
                try {
                    Float.parseFloat(exp);
                } catch (Exception e) {
                    throw new Exception("'" + exp + "'" + " is neither a predefined method, nor a property in '" + entityWorksOn + "' nor does it match the action type '" + typeofAction + "'!");
                }
                break;
            }

            case "calculation": {
                try {
                    Float.parseFloat(exp);
                } catch (Exception e) {
                    throw new Exception("'" + exp + "'" + " is neither a predefined method, nor a property in '" + entityWorksOn + "' nor does it match the action type '" + typeofAction + "'!");
                }
                break;
            }
        }
    }

    public void throwableCheckIfPropertyExists(Boolean isExists, String entityName, String nameofProperty) throws Exception {
        if (!isExists) {
            throw new Exception("Property name " + nameofProperty + " doesn't exists in entity " + entityName);
        }
    }
}
