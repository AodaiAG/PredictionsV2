package ExceptionHandler;

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

            if (!isIntRandom) {
                checkIfValueMatchesType(initValue, type);
            }
        } catch (Exception e) {
            throw new Exception("Problem occurred while Parsing xml file in property name " + name + " reasons: " + '\n' + e.getMessage());
        }
    }
}