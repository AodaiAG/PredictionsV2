package components.requests;

import components.mainApp.UserMainAppController;

public class RequestsController {
    UserMainAppController mainAppController;

    public void setAppMainController(UserMainAppController userMainAppController) {
        this.mainAppController = mainAppController;
    }
}
