package util;

import com.google.gson.Gson;

public class Constants
{

    // global constants
    public final static String JHON_DOE = "<Anonymous>";

    // fxml locations
    public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/components/mainApp/userMainApp.fxml";
    public final static String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/components/login/login.fxml";
    public final static String EXECUTION_FXML_RESOURCE_LOCATION = "/components/execution/execution.fxml";
    public final static String REQUESTS_FXML_RESOURCE_LOCATION = "/components/requests/requests.fxml";
    public final static String RESULTS_FXML_RESOURCE_LOCATION = "/components/results/results.fxml";
    public final static String SIMULATION_DETAILS_FXML_RESOURCE_LOCATION = "/components/simulationDetails/simulationDetails.fxml";
    public final static String SUBMIT_NEW_REQUEST_LOCATION = "/components/requests/submitNewRequest/submitnewRequest.fxml";
    public final static String SHOW_USER_REQUESTS = "/components/requests/showUserRequests/userRequessts.fxml";




    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/";
    public final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String NEW_REQUEST= "new_request";

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/login";
}
