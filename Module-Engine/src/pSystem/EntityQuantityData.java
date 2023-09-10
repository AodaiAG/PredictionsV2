package pSystem;

public class EntityQuantityData
{
    private int tick;
    private int quantity;

    public EntityQuantityData(int tick, int quantity)
    {
        this.tick = tick;
        this.quantity = quantity;
    }

    public int getTick() {
        return tick;
    }

    public int getQuantity() {
        return quantity;
    }
}
