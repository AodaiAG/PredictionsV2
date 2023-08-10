package Rules.ActionTypes;

import Entity.Entity;

public interface Action
{

     public void ActivateAction( Entity e) throws Exception;
     public String getNameOfAction();


}
