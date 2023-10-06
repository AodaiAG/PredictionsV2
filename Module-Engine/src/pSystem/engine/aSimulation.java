package pSystem.engine;

public class aSimulation
{
    private String nameofSimulation;
    private boolean isExecuted=false;
    private World world;


    public String getNameofSimulation()
    {
        return nameofSimulation;
    }

    public aSimulation(String nameofSimulation, World world)
    {
        this.nameofSimulation = nameofSimulation;
        this.world = world;
    }
    public aSimulation()
    {

    }


    public boolean isExecuted()
    {
        return isExecuted;
    }

    public void setExecuted(boolean executed)
    {
        isExecuted = executed;
    }

    public aSimulation clone()
    {
        aSimulation res=new aSimulation();
        res.setWorld(this.world.clone());
        res.setNameofSimulation(new String(nameofSimulation));
        return res;
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
