import java.util.Set;

public class RulesDTO
{
    private String nameOfRule;
    private int ticks;
    private double probability;
    private int numberOfActions;
    Set<String> namesOfActions;

    public RulesDTO(String nameOfRule, int ticks, double probability, int numberOfActions, Set<String> namesOfActions) {
        this.nameOfRule = nameOfRule;
        this.ticks = ticks;
        this.probability = probability;
        this.numberOfActions = numberOfActions;
        this.namesOfActions = namesOfActions;
    }
}
