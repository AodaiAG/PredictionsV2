package Rules;

import java.util.HashSet;
import java.util.Set;

public class Rules
{

    String nameOfRule;
    String whenActivated;
    int ticks=1;
    double probability;
    Set<Actions> actions;

    public Rules()
    {
        actions=new HashSet<Actions>();
    }
}
