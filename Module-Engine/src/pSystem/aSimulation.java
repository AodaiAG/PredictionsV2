package pSystem;

public class aSimulation
{
    String nameofSimulation;
    World world;

    public String getNameofSimulation()
    {
        return nameofSimulation;
    }

    public aSimulation(String nameofSimulation, World world)
    {
        this.nameofSimulation = nameofSimulation;
        this.world = world;
    }

    public void setNameofSimulation(String nameofSimulation)
    {
        this.nameofSimulation = nameofSimulation;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
