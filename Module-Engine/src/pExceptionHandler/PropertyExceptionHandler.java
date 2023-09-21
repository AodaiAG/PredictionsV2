package pExceptionHandler;

import pEntity.DataType;

public class PropertyExceptionHandler extends ExceptionHandler {

    public void Handle(String type, String name, boolean hasRange, String from, String to, boolean isIntRandom, String initValue) throws Exception {
        try {
            checkIfTypeNotSupported(type);
            if (hasRange) {
                checkFromToRange(from, to);
                if (!isIntRandom) {
                    checkIfValueMatchesType(initValue, type);
                    checkIfInRange(initValue, from, to);
                }
            }
            else{
                if(type.equals("decimal") || type.equals("float"))
                {
                    throw new Exception(type + " should have a range");
                }
            }
            if (!isIntRandom) {
                checkIfValueMatchesType(initValue, type);
            }

        } catch (Exception e) {
            throw new Exception("Problem occurred while Parsing xml file in property. Name: " + name + " Reasons: " + '\n' + e.getMessage());
        }
    }
}