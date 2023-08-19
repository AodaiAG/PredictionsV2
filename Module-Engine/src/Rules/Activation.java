package Rules;

public class Activation
{
    private int ticks = 1;
    private double probability = 1;

    public int getTicks()
    {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
