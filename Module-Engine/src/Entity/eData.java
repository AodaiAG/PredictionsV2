package Entity;

import java.util.Random;

public enum eData {

    DECIMAL {
        @Override
        public Integer getData() {
            return Integer.parseInt(dataString);
        }

        @Override
        public void calculateNewVal(String initValue, Boolean isRandomInitialize) {
            int val;
            if (isRandomInitialize) {
                val = r.nextInt(Integer.parseInt(getFrom()) - Integer.parseInt(getTo()) + 1) + Integer.parseInt(getFrom());
            } else {
                val = Integer.parseInt(initValue);
            }
            dataString = Integer.toString(val);
        }
    },

    FLOAT {
        @Override
        public Float getData() {
            return Float.parseFloat(dataString);
        }

        @Override
        public void calculateNewVal(String initValue, Boolean isRandomInitialize) {

            float val;
            if (isRandomInitialize) {
                val = Float.parseFloat(getFrom()) + r.nextFloat() * (Float.parseFloat(getTo()) - Float.parseFloat(getFrom()));
            } else {
                val = Float.parseFloat(initValue);
            }
            dataString = Float.toString(val);
        }
    },

    BOOLEAN {
        @Override
        public Boolean getData() {
            return (dataString.equals("true"));
        }

        @Override
        public void calculateNewVal(String initValue, Boolean isRandomInitialize) {
            {
                boolean val;
                if (isRandomInitialize) {
                    val = r.nextBoolean();
                } else {
                    val = Boolean.parseBoolean(initValue);
                }
                dataString = Boolean.toString(val);
            }
        }
    },

        STRING {
            @Override
            public String getData () {
                return dataString;
            }

            @Override
            public void calculateNewVal (String initValue, Boolean isRandomInitialize){
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
                dataString = val;
            }
        };

        final Random r = new Random();

        private String from;

        private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String dataString;

        public void setDataString(String dataString) {
            this.dataString = dataString;
        }

        public String getDataString() {
            return dataString;
        }

        public abstract void calculateNewVal(String initVal, Boolean isRandomInitialize);

        public abstract Object getData();
    }
}
