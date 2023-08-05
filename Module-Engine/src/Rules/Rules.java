package Rules;

import Rules.ActionTypes.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rules
{

    public String nameOfRule;
    public String whenActivated;
    public int ticks=1;
    public double probability;
    public List<Action> actions;

    public Rules()
    {
        actions=new ArrayList<>();
    }
}
