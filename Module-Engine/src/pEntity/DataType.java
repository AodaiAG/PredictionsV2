package pEntity;

import java.util.Random;

public enum DataType {
    DECIMAL {
        @Override
        public String getDataTypeString() {
            return "Decimal";
        }

        @Override
        public Boolean compareTo(String comparedTo, String Operator, String dataString) throws Exception {

            try {
                Float comparedToData = Float.parseFloat(comparedTo);
                Float myData = Float.parseFloat(dataString);
                switch (Operator) {
                    case "=": {
                        return myData.equals(comparedToData);

                    }
                    case "!=": {
                        return !myData.equals(comparedToData);

                    }
                    case "bt": {
                        return myData > comparedToData;

                    }
                    case "lt": {
                        return myData < comparedToData;

                    }
                    default:
                        throw new Exception("operator not legit");
                }
            } catch (Exception e) {
                throw new Exception("Comparison doesn't make sense");
            }
        }

        @Override
        public String multiply(String arg1, String arg2, String from, String to) throws Exception {
            try {
                Integer a1 = Integer.parseInt(arg1);
                Integer a2 = Integer.parseInt(arg2);
                Integer res = a1 * a2;
                if (isInRange(res, from, to)) {
                    return res.toString();
                } else {
                    throw new Exception("The result of this action is out of the predefined range");
                }

            } catch (Exception e) {

                throw e;
            }

        }

        @Override
        public String divide(String arg1, String arg2, String from, String to) throws Exception {
            try {
                Integer a1 = Integer.parseInt(arg1);
                Integer a2 = Integer.parseInt(arg2);
                if (a2 == 0) {
                    throw new Exception("You can't divide by 0 !");
                } else {
                    Integer res = a1 / a2;
                    if (isInRange(res, from, to)) {
                        return res.toString();
                    } else {
                        throw new Exception("The result of this action is out of the predefined range");
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }

        @Override
        public String setNewValue(String value, String from, String to) throws Exception {
            try {
                Integer res = Integer.parseInt(value);
                if (isInRange(res, from, to)) {
                    return res.toString();
                } else {
                    throw new Exception("value out of range");
                }
            } catch (NumberFormatException e) {
                throw new Exception("The data you entered is not of type Decimal ");
            }
        }

        @Override
        public String decrease(String value, String dataString, String from, String to) throws Exception {
            try {
                Integer fValue = Integer.parseInt(value);
                Integer res = Integer.parseInt(dataString) - fValue;
                Integer fromValue = Integer.parseInt(from);
                Integer toValue = Integer.parseInt(to);
                if (res >= fromValue && res <= toValue) {
                    return res.toString();
                } else {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            } catch (Exception e) {
                throw e;
            }
        }

        public Boolean isInRange(Integer value, String from, String to) {
            Integer fromValue = Integer.parseInt(from);
            Integer toValue = Integer.parseInt(to);
            if (value >= fromValue && value <= toValue) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String increase(String value, String dataString, String from, String to) throws Exception {
            try {
                Integer fValue = Integer.parseInt(value);
                Integer res = Integer.parseInt(dataString) + fValue;

                if (isInRange(res, from, to)) {
                    return res.toString();
                } else {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            } catch (Exception e) {
                throw e;
            }
        }


        @Override
        public String calculateNewVal(String initValue, Boolean isRandomInitialize, String from, String to) {

            int val;
            if (isRandomInitialize) {
                val = r.nextInt(Integer.parseInt(to) - Integer.parseInt(from) + 1) + Integer.parseInt(from);


            } else {
                val = Integer.parseInt(initValue);
            }
            return Integer.toString(val);
        }
    },

    FLOAT {
        @Override
        public String getDataTypeString() {
            return "Float";
        }

        @Override
        public Boolean compareTo(String comparedTo, String Operator, String dataString) throws Exception {
            try {
                Float ComparedToData = Float.parseFloat(comparedTo);
                Float myData = Float.parseFloat(dataString);
                switch (Operator) {
                    case "=": {
                        return myData.equals(ComparedToData);
                    }
                    case "!=": {
                        return !myData.equals(ComparedToData);
                    }
                    case "bt": {
                        return myData > ComparedToData;
                    }
                    case "lt": {
                        return myData < ComparedToData;
                    }
                    default:
                        throw new Exception("operator not legit");
                }

            } catch (Exception e) {
                throw new Exception("Comparison doesn't make sense");
            }
        }

        @Override
        public String multiply(String arg1, String arg2, String from, String to) throws Exception {
            try {
                Float a1 = Float.parseFloat(arg1);
                Float a2 = Float.parseFloat(arg2);
                Float res = a1 * a2;
                if (isInRange(res, from, to)) {
                    return res.toString();
                } else {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            } catch (Exception e) {
                throw e;
            }
        }

        @Override
        public String divide(String arg1, String arg2, String from, String to) throws Exception {
            try {
                Float a1 = Float.parseFloat(arg1);
                Float a2 = Float.parseFloat(arg2);
                if (a2 == 0) {
                    throw new Exception("You can't divide by 0 !");
                } else {
                    Float res = a1 / a2;
                    if (isInRange(res, from, to)) {
                        return res.toString();
                    } else {
                        throw new Exception("The result of this action is out of the predefined range");
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }

        @Override
        public String setNewValue(String value, String from, String to) throws Exception {
            try {
                Float res = Float.parseFloat(value);
                if (isInRange(res, from, to)) {
                    return res.toString();
                } else {
                    throw new Exception("Value out of range !");
                }
            } catch (NumberFormatException e) {
                throw new Exception("The data you entered is not of type Float ");
            }
        }

        @Override
        public String decrease(String value, String dataString, String from, String to) throws Exception {
            try {
                Float fValue = Float.parseFloat(value);
                Float res = Float.parseFloat(dataString) - fValue;
                Float fromValue = Float.parseFloat(from);
                Float toValue = Float.parseFloat(to);
                if (res >= fromValue && res <= toValue) {
                    return res.toString();
                } else {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            } catch (Exception e) {
                throw e;
            }
        }

        public Boolean isInRange(Float value, String from, String to) {
            Float fromValue = Float.parseFloat(from);
            Float toValue = Float.parseFloat(to);
            return value >= fromValue && value <= toValue;
        }

        @Override
        public String increase(String value, String dataString, String from, String to) throws Exception {
            try {
                Float fValue = Float.parseFloat(value);
                Float res = Float.parseFloat(dataString) + fValue;

                if (isInRange(res, from, to)) {
                    return res.toString();
                } else {
                    throw new Exception("The result of this action is out of the predefined range");
                }
            } catch (Exception e) {
                throw e;
            }
        }

        @Override
        public String calculateNewVal(String initValue, Boolean isRandomInitialize, String from, String to) {

            float val;
            if (isRandomInitialize) {
                val = Float.parseFloat(from) + r.nextFloat() * (Float.parseFloat(to) - Float.parseFloat(from));
            } else {
                val = Float.parseFloat(initValue);
            }
            return Float.toString(val);
        }
    },

    BOOLEAN {
        @Override
        public String getDataTypeString() {
            return "Boolean";
        }

        @Override
        public Boolean compareTo(String comparedTo, String Operator, String dataString) throws Exception {
            try {
                Boolean compTo = Boolean.parseBoolean(comparedTo);
                switch (Operator) {
                    case "=": {
                        return Boolean.parseBoolean(dataString) == Boolean.parseBoolean(comparedTo);

                    }
                    case "!=": {
                        return Boolean.parseBoolean(dataString) != Boolean.parseBoolean(comparedTo);

                    }
                    case "bt":
                    case "lt": {
                        throw new Exception("Comparator not legit in the context of Boolean");
                    }
                    default:
                        throw new Exception("operator not legit");
                }
            } catch (Exception e) {
                throw new Exception("comparison doesn't make sense");
            }
        }

        @Override
        public String multiply(String arg1, String arg2, String from, String to) throws Exception {
            throw new Exception("Can't apply this action to type boolean");
        }

        @Override
        public String divide(String arg1, String arg2, String from, String to) throws Exception {
            throw new Exception("Can't apply this action to type boolean");
        }

        @Override
        public String setNewValue(String value, String from, String to) throws Exception {
            if ((value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                Boolean res = Boolean.valueOf(value);
                return Boolean.toString(res);
            } else {
                throw new Exception("The data you entered is not of type Boolean ");
            }
        }

        @Override
        public String decrease(String value, String dataString, String from, String to) throws Exception {
            throw new Exception("Can't apply this action to type boolean");
        }

        @Override
        public String increase(String value, String dataString, String from, String to) throws Exception {
            throw new Exception("Can't apply this action to type boolean");

        }

        @Override
        public String calculateNewVal(String initValue, Boolean isRandomInitialize, String from, String to) {
            {
                boolean val;
                if (isRandomInitialize) {
                    val = r.nextBoolean();
                } else {
                    val = Boolean.parseBoolean(initValue);
                }
                return Boolean.toString(val);
            }
        }
    },

    STRING {
                @Override
                public String getDataTypeString() {
                    return "String";
                }

                @Override
                public Boolean compareTo(String comparedTo, String Operator, String dataString) throws Exception
                {

                    switch (Operator)
                    {
                        case "=": {
                            return dataString.equals(comparedTo);

                        }
                        case "!=": {
                            return !dataString.equals(comparedTo);

                        }
                        case "bt": {
                            int res = dataString.compareTo(comparedTo);
                            if (res == 0) {
                                return false;
                            }
                            if (res > 0) {
                                return true;
                            }
                            if (res < 0) {
                                return false;
                            }
                        }
                        case "lt": {
                            int res = dataString.compareTo(comparedTo);
                            if (res == 0) {
                                return false;
                            }
                            if (res > 0) {
                                return false;
                            }
                            if (res < 0) {
                                return true;
                            }

                        }

                    }
                    return false;
                }

                @Override
                public String multiply(String arg1, String arg2, String from, String to) throws Exception {
                    throw new Exception("Can't apply this action to type string");
                }

                @Override
                public String divide(String arg1, String arg2, String from, String to) throws Exception {
                    throw new Exception("Can't apply this action to type string");

                }

                @Override
                public String setNewValue(String value, String from, String to) throws Exception {
                    return value;
                }

                @Override
                public String decrease(String value, String dataString, String from, String to) throws Exception {
                    throw new Exception("Can't apply this action to type string");

                }

                @Override
                public String increase(String value, String dataString, String from, String to) throws Exception {
                    throw new Exception("Can't apply this action to type string");

                }

                @Override
                public String calculateNewVal(String initValue, Boolean isRandomInitialize, String from, String to) {
                    String val;
                    if (isRandomInitialize) {
                        int targetStringLength = r.nextInt(50) + 1;
                        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,_-.() ";
                        Random random = new Random();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < targetStringLength; i++) {
                            int randomIndex = random.nextInt(characters.length());
                            char randomChar = characters.charAt(randomIndex);
                            stringBuilder.append(randomChar);
                        }
                        val = stringBuilder.toString();
                    } else {
                        val = initValue;
                    }
                    return val;
                }
            };

    final Random r = new Random();

    public abstract String calculateNewVal(String initValue, Boolean isRandomInitialize, String from, String to);

    public abstract String decrease(String value, String dataString, String from, String to) throws Exception;

    public abstract String increase(String value, String dataString, String from, String to) throws Exception;

    public abstract String setNewValue(String value, String from, String to) throws Exception;

    public abstract String multiply(String arg1, String arg2, String from, String to) throws Exception;

    public abstract String divide(String arg1, String arg2, String from, String to) throws Exception;

    public abstract Boolean compareTo(String comparedTo, String Operator, String dataString) throws Exception;

    public abstract String getDataTypeString();
}