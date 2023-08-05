package Rules;

import Rules.ActionTypes.Action;

import java.util.HashSet;
import java.util.Set;

public class Rules
{

    String nameOfRule;
    String whenActivated;
    int ticks=1;
    double probability;
    Set<Action> actions;

    public Rules()
    {
        //actions=new HashSet<Actions>();
    }
}
