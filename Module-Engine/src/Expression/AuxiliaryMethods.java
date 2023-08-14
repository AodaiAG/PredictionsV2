package Expression;

import Environment.EnvironmentInstance;

import java.util.Map;
import java.util.Random;
import System.World;




 public class AuxiliaryMethods
{


        private World world;
        public AuxiliaryMethods(World world)
        {
            this.world = world;
        }
        public World getWorld()
        {
            return world;
        }

        public void setWorld(World world) {
            this.world = world;
        }


        public String environment(String nameOfEnv)
        {
            Map<String, EnvironmentInstance> name2Env = world.getName2Env();
            EnvironmentInstance en=name2Env.get(nameOfEnv);
            return en.getEnvironmentProperty().getData().getDataString();
        }
        public String random(String arg)
        {
            try {
                int maxRange = Integer.parseInt(arg);
                if (maxRange < 0) {
                    throw new IllegalArgumentException("Argument must be a non-negative integer");
                }

                Random random = new Random();
                int randomValue = random.nextInt(maxRange + 1);
                return String.valueOf(randomValue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Argument must be a numeric value");
            }
        }

}
